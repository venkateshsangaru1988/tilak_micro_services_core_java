package com.ecommerce.product.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@Configuration
public class ResilienceConfig {

	@Bean
	CircuitBreakerRegistry circuitBreakerRegistry() {

		CircuitBreakerConfig stockCircuitBreakerConfig = CircuitBreakerConfig.custom()

				// Number of calls used to calculate failure rate
				.slidingWindowSize(10)

				// Minimum calls before failure rate is calculated
				.minimumNumberOfCalls(5)

				// Circuit opens when failure rate reaches 50%
				.failureRateThreshold(50)

				// Stay OPEN for 10 seconds
				.waitDurationInOpenState(Duration.ofSeconds(10))

				// Number of calls allowed during HALF_OPEN
				.permittedNumberOfCallsInHalfOpenState(3)

				// Automatically move OPEN -> HALF_OPEN
				.automaticTransitionFromOpenToHalfOpenEnabled(true)

				// Consider these as failures
				.recordExceptions(Exception.class)

				.build();

		return CircuitBreakerRegistry.of(stockCircuitBreakerConfig);
	}
}