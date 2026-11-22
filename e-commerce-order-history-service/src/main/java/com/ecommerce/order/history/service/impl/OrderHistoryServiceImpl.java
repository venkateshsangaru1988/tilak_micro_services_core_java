package com.ecommerce.order.history.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.order.history.constants.UtilConstants;
import com.ecommerce.order.history.dto.OrderHistoryRequestDto;
import com.ecommerce.order.history.dto.OrderHistoryResponseDto;
import com.ecommerce.order.history.entity.OrderHistory;
import com.ecommerce.order.history.repository.OrderHistoryRepository;
import com.ecommerce.order.history.service.OrderHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderHistoryServiceImpl implements OrderHistoryService {

	private final OrderHistoryRepository orderHistoryRepository;

	@Override
	public String saveOrderHistory(List<OrderHistoryRequestDto> requestList) {

		requestList.forEach(req -> {

			OrderHistory history = new OrderHistory();
			history.setOrderId(req.getOrderId());
			history.setCustomerEmailId(req.getCustomerEmailId());
			history.setOrderDateTime(LocalDateTime.now());
			history.setAuditTxnId(req.getAuditTxnId());
			history.setOrderExpectedDate(req.getOrderExpectedDate());
			history.setProductId(req.getProductId());
			history.setOrderStatus(UtilConstants.ORDERED);
			history.setQty(req.getQty());
			orderHistoryRepository.save(history);

		});
		return "Order History Saved";
	}

	@Override
	public String orderStatusUpdate(String orderId, String orderStatus) {

		orderHistoryRepository.updateOrderHistoryByOrderId(orderStatus, orderId);
		return "Order Status Updated";
	}

	@Override
	public List<OrderHistoryResponseDto> getHistoryByEmailAndOrderId(String customerEmailId, String orderId) {
		List<OrderHistoryResponseDto> responseList = new ArrayList<>();
		List<OrderHistory> orderHistoryList = orderHistoryRepository.findByCustomerEmailIdAndOrderId(customerEmailId,
				orderId);

		if (!orderHistoryList.isEmpty()) {

			orderHistoryList.forEach(ord -> {
				OrderHistoryResponseDto response = new OrderHistoryResponseDto();
				response.setOrderHistoryId(ord.getOrderHistoryId());
				response.setProductId(ord.getProductId());
				response.setAuditTxnId(ord.getAuditTxnId());
				response.setQty(ord.getQty());
				responseList.add(response);
			});

		}
		return responseList;
	}

	@Override
	public String updateOrderHistoryByOrderIdAndProductId(String orderId, String orderStatus, Long productId) {
		orderHistoryRepository.updateOrderHistoryByOrderIdAndProductId(orderStatus, orderId, productId);
		return "Order Status Updated";
	}

	@Override
	public List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdAndProductId(String customerEmailId, String orderId,
			Long productId) {
		List<OrderHistoryResponseDto> responseList = new ArrayList<>();
		List<OrderHistory> orderHistoryList = orderHistoryRepository
				.findByCustomerEmailIdAndOrderIdAndProductId(customerEmailId, orderId, productId);

		if (!orderHistoryList.isEmpty()) {

			orderHistoryList.forEach(ord -> {
				OrderHistoryResponseDto response = new OrderHistoryResponseDto();
				response.setOrderHistoryId(ord.getOrderHistoryId());
				response.setProductId(ord.getProductId());
				response.setAuditTxnId(ord.getAuditTxnId());
				response.setQty(ord.getQty());
				responseList.add(response);
			});

		}
		return responseList;
	}

	@Override
	public List<OrderHistoryResponseDto> getHistoryRecordsByOrderStatus(String orderStatus) {
		List<OrderHistoryResponseDto> responseList = new ArrayList<>();
		List<OrderHistory> orderHistoryList = orderHistoryRepository.findByOrderStatus(orderStatus);
		if (!orderHistoryList.isEmpty()) {

			orderHistoryList.forEach(ord -> {
				OrderHistoryResponseDto response = new OrderHistoryResponseDto();
				response.setOrderHistoryId(ord.getOrderHistoryId());
				response.setProductId(ord.getProductId());
				response.setAuditTxnId(ord.getAuditTxnId());
				response.setQty(ord.getQty());
				responseList.add(response);
			});

		}
		return responseList;
	}

	@Override
	public String deleteOrderHistoryByOrderId(String orderId) {
		orderHistoryRepository.deleteOrderHistoryByOrderId(orderId);
		return "Order history deleted with order id : " + orderId;
	}

}
