package com.ecommerce.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

	private final PaymentService paymentService;

	@GetMapping("/get-balance-by-email-id/{emailId}")
	public ResponseEntity<Double> customerBalanceByEmailId(@PathVariable String emailId) {
		log.info("in side customerBalanceByEmailId() method");
		Double response = paymentService.customerBalanceByEmailId(emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/balance-update-by-email-id/{emailId}/{customerSelectedQtyPrice}/{operationType}")
	public ResponseEntity<Long> balanceUpdate(@PathVariable String emailId,
			@PathVariable Double customerSelectedQtyPrice, @PathVariable String operationType) {
		log.info("in side balanceUpdate() method");
		Long response = paymentService.balanceUpdate(emailId, customerSelectedQtyPrice, operationType);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
