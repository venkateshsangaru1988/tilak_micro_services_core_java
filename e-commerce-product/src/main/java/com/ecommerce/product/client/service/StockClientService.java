package com.ecommerce.product.client.service;

import java.util.List;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.stereotype.Service;

import com.ecommerce.product.client.StockFeignClient;
import com.ecommerce.product.customexceptions.StockServiceUnavailableException;
import com.ecommerce.product.dto.ProductAvailabilityRequestDto;
import com.ecommerce.product.dto.ProductAvailabilityResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockClientService {

	private final StockFeignClient stockFeignClient;

	private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	public List<ProductAvailabilityResponseDto> stockAvailability(List<ProductAvailabilityRequestDto> request) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("stockServiceCircuitBreaker");

		return circuitBreaker.run(() -> stockFeignClient.stockAvailability(request),
				throwable -> stockFallback(request, throwable));
	}

	private List<ProductAvailabilityResponseDto> stockFallback(List<ProductAvailabilityRequestDto> request,
			Throwable throwable) {

		log.error("Stock service unavailable", throwable);

		throw new StockServiceUnavailableException("Stock service is currently unavailable", 503);
	}

	public String stockUpdate(List<ProductAvailabilityRequestDto> requestObj, String operationType) {

		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("stockServiceCircuitBreaker");

		return circuitBreaker.run(() -> stockFeignClient.stockUpdate(requestObj, operationType),
				throwable -> stockUpdateFallback(requestObj, operationType, throwable));
	}

	private String stockUpdateFallback(List<ProductAvailabilityRequestDto> request, String operationType,
			Throwable throwable) {

		log.error("Stock service unavailable", throwable);

		throw new StockServiceUnavailableException("Stock service is currently unavailable...", 503);
	}
}