package com.ecommerce.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(name = "e-commerce-payment-service", url = "${payment-base-url}")
@FeignClient(name = "e-commerce-payment-service")
public interface PaymentFeignClient {

	@GetMapping(value = "/e-commerce-payment/payment/get-balance-by-email-id/{emailId}")
	Double customerBalanceByEmailId(@PathVariable String emailId);

	@PostMapping(value = "/e-commerce-payment/payment/balance-update-by-email-id/{emailId}/{customerSelectedQtyPrice}/{operationType}")
	Long balanceUpdate(@PathVariable String emailId, @PathVariable Double customerSelectedQtyPrice,
			@PathVariable String operationType);

}