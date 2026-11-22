package com.ecommerce.product.client.service;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import com.ecommerce.product.client.PaymentFeignClient;
import com.ecommerce.product.customexceptions.StockServiceUnavailableException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentClientService {

	private final PaymentFeignClient paymentFeignClient;

	private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	public Double customerBalanceByEmailId(String emailId) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("paymentServiceCircuitBreaker");

		return circuitBreaker.run(() -> paymentFeignClient.customerBalanceByEmailId(emailId),
				throwable -> customerBalanceByEmailIdFallback(emailId, throwable));
	}

	private Double customerBalanceByEmailIdFallback(String emailId, Throwable throwable) {

		log.error("Payment service unavailable", throwable);

		throw new StockServiceUnavailableException("Payment service is currently unavailable ....", 503);
	}

	public Long balanceUpdate(String emailId, Double customerSelectedQtyPrice, String operationType) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("paymentServiceCircuitBreaker");

		return circuitBreaker.run(
				() -> paymentFeignClient.balanceUpdate(emailId, customerSelectedQtyPrice, operationType),
				throwable -> balanceUpdateFallback(emailId, customerSelectedQtyPrice, operationType, throwable));
	}

	private Long balanceUpdateFallback(String emailId, Double customerSelectedQtyPrice, String operationType,
			Throwable throwable) {

		log.error("Payment service unavailable", throwable);

		throw new StockServiceUnavailableException("Payment service is currently unavailable ....", 503);
	}

}
