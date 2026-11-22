package com.ecommerce.product.service.impl;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ecommerce.product.client.service.OrderHistoryClientService;
import com.ecommerce.product.client.service.PaymentClientService;
import com.ecommerce.product.client.service.StockClientService;
import com.ecommerce.product.constants.UtilConstants;
import com.ecommerce.product.customexceptions.InsufficientBalanceException;
import com.ecommerce.product.customexceptions.InsufficientStockException;
import com.ecommerce.product.customexceptions.OrderNotFoundException;
import com.ecommerce.product.customexceptions.ProductApplicationUtilException;
import com.ecommerce.product.dto.CancelItemsListDto;
import com.ecommerce.product.dto.OrderHistoryRequestDto;
import com.ecommerce.product.dto.OrderHistoryResponseDto;
import com.ecommerce.product.dto.ProductAvailabilityRequestDto;
import com.ecommerce.product.dto.ProductAvailabilityResponseDto;
import com.ecommerce.product.dto.ProductOrderItemResponseDto;
import com.ecommerce.product.dto.ProductOrderResponseDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.entity.ProductEntity;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	private final StockClientService stockClientService;

	private final PaymentClientService paymentClientService;

	private final OrderHistoryClientService orderHistoryClientService;

	@Override
	@Cacheable(value = "products", key = "'all'")
	public List<ProductResponseDto> getAllProducts() {

		List<ProductResponseDto> responseList = new ArrayList<>();

		List<ProductEntity> productList = productRepository.findAll();

		if (!productList.isEmpty()) {

			productList.forEach(product -> {

				ProductResponseDto dto = new ProductResponseDto();
				BeanUtils.copyProperties(product, dto);
				responseList.add(dto);
			});
		}

		return responseList;
	}

	@Override
	public ProductOrderResponseDto placeOrder(String emailId, List<ProductAvailabilityRequestDto> requestList) {

		ProductOrderResponseDto responseObj = new ProductOrderResponseDto();

		boolean paymentUpdateFlag = false;
		boolean stockUpdateFlag = false;
		boolean orderHistorySaveFlag = false;
		String orderId = null;
		Double customerSelectedQtyPrice = 0d;

		List<ProductOrderItemResponseDto> productList = new ArrayList<>();
		try {
			List<ProductAvailabilityResponseDto> responseList = stockClientService.stockAvailability(requestList);
			Map<Long, ProductAvailabilityRequestDto> requestMap = requestList.stream().collect(Collectors.toMap(
					ProductAvailabilityRequestDto::getProductId, Function.identity(), (first, second) -> second));
			for (ProductAvailabilityResponseDto response : responseList) {

				ProductAvailabilityRequestDto request = requestMap.get(response.getProductId());

				if (request == null) {
					continue;
				}

				Long requestedQty = request.getQty();
				Long availableStock = response.getStockAvailable();

				if (availableStock < requestedQty) {
					// Notification Call
					throw new InsufficientStockException("Insufficient stock for product ID: " + request.getProductId()
							+ ". Requested quantity: " + requestedQty + ", Available stock: "
							+ (availableStock == null ? 0 : availableStock), 409);

				}
			}

			Double bankBalance = paymentClientService.customerBalanceByEmailId(emailId);

			for (ProductAvailabilityRequestDto reqObj : requestList) {

				Optional<ProductEntity> prodObj = productRepository.findById(reqObj.getProductId());
				if (prodObj.isPresent()) {
					Double productPrice = prodObj.get().getProductPrice();
					Double selectedQtyProdPrice = productPrice * reqObj.getQty();
					customerSelectedQtyPrice += selectedQtyProdPrice;

					ProductOrderItemResponseDto item = new ProductOrderItemResponseDto();

					item.setProductName(prodObj.get().getProductName());
					item.setQty(reqObj.getQty());
					item.setProductPrice(productPrice);
					item.setTotalPrice(selectedQtyProdPrice);

					productList.add(item);

				}

			}

			if (bankBalance < customerSelectedQtyPrice) {
				// Notification Call
				throw new InsufficientBalanceException("Insufficient Balance for products & bank balance is : "
						+ bankBalance + ". Requested product amount : " + customerSelectedQtyPrice, 409);
			}

			// Rest Calls to Stock,Payment & insert records for order history

			SecureRandom secureRandom = new SecureRandom();
			int otp = 100000 + secureRandom.nextInt(900000);
			orderId = "ORD-" + LocalDate.now() + "-" + String.valueOf(otp);

			// Balance Update
			Long auditTxnId = paymentClientService.balanceUpdate(emailId, customerSelectedQtyPrice,
					UtilConstants.ORDER_PLACED_PAID);
			// This line will says balance updated
			paymentUpdateFlag = true;
			// Stock Update
			stockClientService.stockUpdate(requestList, UtilConstants.STOCK_MINUS);
			// This line will says stock updated
			stockUpdateFlag = true;
			List<OrderHistoryRequestDto> orderHistoryList = new ArrayList<>();

			for (ProductAvailabilityRequestDto reqObj : requestList) {

				OrderHistoryRequestDto history = new OrderHistoryRequestDto();
				history.setCustomerEmailId(emailId);
				history.setOrderDateTime(LocalDateTime.now());
				history.setOrderId(orderId);
				history.setProductId(reqObj.getProductId());
				history.setAuditTxnId(auditTxnId);
				history.setOrderExpectedDate(LocalDate.now().plusDays(2));
				history.setQty(reqObj.getQty());

				orderHistoryList.add(history);
			}
			// Order History Save
			orderHistoryClientService.saveOrderHistory(orderHistoryList);
			// This line will says order history saved
			orderHistorySaveFlag = true;

		} catch (Exception ex) {

			// Order history delete
			if (orderHistorySaveFlag) {
				// Delete
				orderHistoryClientService.deleteOrderHistoryByOrderId(orderId);

			}

			// Stock roll-back
			if (stockUpdateFlag) {
				stockClientService.stockUpdate(requestList, UtilConstants.STOCK_PLUS);
			}
			// Payment refund
			if (paymentUpdateFlag) {
				paymentClientService.balanceUpdate(emailId, customerSelectedQtyPrice, UtilConstants.ORDER_REFUND);
			}

			if (orderHistorySaveFlag || stockUpdateFlag || paymentUpdateFlag) {
				// Notification Call
				throw new ProductApplicationUtilException("Order is not booked due to technical error", 500);
			}
			throw ex;

		}

		responseObj.setProducts(productList);
		responseObj.setOrderId(orderId);
		responseObj.setGrandTotal(customerSelectedQtyPrice);
		// Notification Call
		return responseObj;
	}

	@Override
	public String cancelOrder(String emailId, String orderId) {
		Double sumOfPurchasedProductPrice = 0d;
		boolean paymentUpdateFlag = false;
		boolean stockUpdateFlag = false;
		boolean orderHistorySaveFlag = false;
		List<ProductAvailabilityRequestDto> productAvailableList = new ArrayList<>();
		try {
			List<OrderHistoryResponseDto> orderHistoryList = orderHistoryClientService
					.getHistoryByEmailAndOrderId(emailId, orderId);
			if (orderHistoryList.isEmpty()) {
				throw new OrderNotFoundException("Order is not available with given order id : " + orderId, 404);
			}
			for (OrderHistoryResponseDto orderHistory : orderHistoryList) {
				ProductAvailabilityRequestDto productAvailable = new ProductAvailabilityRequestDto();
				Optional<ProductEntity> prodObj = productRepository.findById(orderHistory.getProductId());
				if (prodObj.isPresent()) {
					sumOfPurchasedProductPrice += prodObj.get().getProductPrice() * orderHistory.getQty();
				}
				productAvailable.setProductId(orderHistory.getProductId());
				productAvailable.setQty(orderHistory.getQty());
				productAvailableList.add(productAvailable);
			}
			// Update Payment
			paymentClientService.balanceUpdate(emailId, sumOfPurchasedProductPrice, UtilConstants.ORDER_REFUND);
			paymentUpdateFlag = true;
			// Stock Update
			stockClientService.stockUpdate(productAvailableList, UtilConstants.STOCK_PLUS);
			stockUpdateFlag = true;
			// Update Order History Status By Order ID
			orderHistoryClientService.orderStatusUpdate(orderId, UtilConstants.ORDER_CANCELLED);
			orderHistorySaveFlag = true;

		} catch (Exception ex) {
			// Roll-back scenario while exception
			// Update Order History Status By Order ID
			if (orderHistorySaveFlag) {
				orderHistoryClientService.orderStatusUpdate(orderId, UtilConstants.ORDER_CANCELLED);
			}
			// Stock Update
			if (stockUpdateFlag) {
				stockClientService.stockUpdate(productAvailableList, UtilConstants.STOCK_PLUS);
			}
			// Update Payment
			if (paymentUpdateFlag) {
				paymentClientService.balanceUpdate(emailId, sumOfPurchasedProductPrice, UtilConstants.ORDER_REFUND);
			}

		}
		// Notification Call
		return "Your Order got cancelled with order id : " + orderId;
	}

	@Override
	public String partialCancelOrder(List<CancelItemsListDto> cancelList, String emailId, String orderId) {
		Double sumOfPurchasedProductPrice = 0d;
		boolean paymentUpdateFlag = false;
		boolean stockUpdateFlag = false;
		String orderHistorySaveFlag = "";
		List<ProductAvailabilityRequestDto> productAvalibalityList = new ArrayList<>();
		try {
			for (CancelItemsListDto cancelItem : cancelList) {
				ProductAvailabilityRequestDto prodAvailable = new ProductAvailabilityRequestDto();
				Optional<ProductEntity> prodObj = productRepository.findById(cancelItem.getProductId());
				if (prodObj.isPresent()) {
					List<OrderHistoryResponseDto> orderHistoryList = orderHistoryClientService
							.getHistoryByEmailAndOrderIdAndProductId(emailId, orderId, cancelItem.getProductId());
					if (!orderHistoryList.isEmpty()) {
						sumOfPurchasedProductPrice += prodObj.get().getProductPrice()
								* orderHistoryList.get(0).getQty();
						prodAvailable.setProductId(cancelItem.getProductId());
						prodAvailable.setQty(orderHistoryList.get(0).getQty());
						productAvalibalityList.add(prodAvailable);
					}

				}

			}
			// Update to Payment
			paymentClientService.balanceUpdate(emailId, sumOfPurchasedProductPrice, UtilConstants.ORDER_REFUND);
			paymentUpdateFlag = true;
			// Stock Update
			stockClientService.stockUpdate(productAvalibalityList, UtilConstants.STOCK_PLUS);
			stockUpdateFlag = true;
			// Order History Update
			for (CancelItemsListDto cancelItem : cancelList) {
				orderHistoryClientService.orderStatusUpdateByOrderIdAndProductId(orderId, UtilConstants.ORDER_CANCELLED,
						cancelItem.getProductId());
				orderHistorySaveFlag = orderHistorySaveFlag + "," + cancelItem.getProductId();
			}

		} catch (Exception ex) {

			// Rollbak-scenario

			if (null != orderHistorySaveFlag) {

				String[] orderHistory = orderHistorySaveFlag.substring(1).split(",");

				for (String productId : orderHistory) {

					orderHistoryClientService.orderStatusUpdateByOrderIdAndProductId(orderId,
							UtilConstants.ORDER_CANCELLED, Long.parseLong(productId));

				}

			}
			if (stockUpdateFlag) {
				stockClientService.stockUpdate(productAvalibalityList, UtilConstants.STOCK_PLUS);
			}
			if (paymentUpdateFlag) {
				paymentClientService.balanceUpdate(emailId, sumOfPurchasedProductPrice, UtilConstants.ORDER_REFUND);
			}
		}
		// Notification Call
		return "Your partial Order got cancelled with order id : " + orderId
				+ " and remaiming items will be delivered in soon";
	}

}
