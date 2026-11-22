package com.ecommerce.product.client.service;

import java.util.List;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import com.ecommerce.product.client.OrderHistoryFeignClient;
import com.ecommerce.product.customexceptions.StockServiceUnavailableException;
import com.ecommerce.product.dto.OrderHistoryRequestDto;
import com.ecommerce.product.dto.OrderHistoryResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryClientService {

	private final OrderHistoryFeignClient orderHistoryFeignClient;

	private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	public String saveOrderHistory(List<OrderHistoryRequestDto> requestObj) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(() -> orderHistoryFeignClient.saveOrderHistory(requestObj),
				throwable -> saveOrderHistoryFallback(requestObj, throwable));
	}

	private String saveOrderHistoryFallback(List<OrderHistoryRequestDto> requestObj, Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}

	public List<OrderHistoryResponseDto> getHistoryByEmailAndOrderId(String customerEmailId, String orderId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(() -> orderHistoryFeignClient.getHistoryByEmailAndOrderId(customerEmailId, orderId),
				throwable -> getHistoryByEmailAndOrderIdFallback(customerEmailId, orderId, throwable));
	}

	private List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdFallback(String customerEmailId, String orderId,
			Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}

	public String orderStatusUpdate(String orderId, String orderStatus) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(() -> orderHistoryFeignClient.orderStatusUpdate(orderId, orderStatus),
				throwable -> orderStatusUpdateFallback(orderId, orderStatus, throwable));
	}

	private String orderStatusUpdateFallback(String orderId, String orderStatus, Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}

	public String orderStatusUpdateByOrderIdAndProductId(String orderId, String orderStatus, Long productId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(
				() -> orderHistoryFeignClient.orderStatusUpdateByOrderIdAndProductId(orderId, orderStatus, productId),
				throwable -> orderStatusUpdateByOrderIdAndProductIdFallback(orderId, orderStatus, productId,
						throwable));
	}

	private String orderStatusUpdateByOrderIdAndProductIdFallback(String orderId, String orderStatus, Long productId,
			Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}

	public List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdAndProductId(String customerEmailId, String orderId,
			Long productId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(
				() -> orderHistoryFeignClient.getHistoryByEmailAndOrderIdAndProductId(customerEmailId, orderId,
						productId),
				throwable -> getHistoryByEmailAndOrderIdAndProductIdFallback(customerEmailId, orderId, productId,
						throwable));
	}

	private List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdAndProductIdFallback(String customerEmailId,
			String orderId, Long productId, Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}

	public String deleteOrderHistoryByOrderId(String orderId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderHistoryServiceCircuitBreaker");

		return circuitBreaker.run(() -> orderHistoryFeignClient.deleteOrderHistoryByOrderId(orderId),
				throwable -> deleteOrderHistoryByOrderIdFallback(orderId, throwable));
	}

	private String deleteOrderHistoryByOrderIdFallback(String orderId, Throwable throwable) {

		log.error("Order History service unavailable", throwable);

		throw new StockServiceUnavailableException("Order History service is currently unavailable ....", 503);
	}
}
