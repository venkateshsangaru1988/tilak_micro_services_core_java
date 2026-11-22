package com.ecommerce.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.CancelItemsListDto;
import com.ecommerce.product.dto.ProductAvailabilityRequestDto;
import com.ecommerce.product.dto.ProductOrderResponseDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

	private final ProductService productService;

	@GetMapping("/all")
	public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
		log.info("in side getAllProducts() method");
		List<ProductResponseDto> responseList = productService.getAllProducts();
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PostMapping("/place-order/{emailId}")
	public ResponseEntity<ProductOrderResponseDto> placeOrder(@PathVariable String emailId,
			@Valid @RequestBody List<ProductAvailabilityRequestDto> requestList) {
		log.info("in side placeOrder() method");
		ProductOrderResponseDto response = productService.placeOrder(emailId, requestList);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/cancel-order/{emailId}/{orderId}")
	public ResponseEntity<String> cancelOrder(@PathVariable String emailId, @PathVariable String orderId) {
		log.info("in side cancelOrder() method");
		String response = productService.cancelOrder(emailId, orderId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/partial-cancel-order/{emailId}/{orderId}")
	public ResponseEntity<String> partialCancelOrder(@Valid @RequestBody List<CancelItemsListDto> cancelList,
			@PathVariable String emailId, @PathVariable String orderId) {
		log.info("in side partialCancelOrder() method");
		String response = productService.partialCancelOrder(cancelList, emailId, orderId);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
