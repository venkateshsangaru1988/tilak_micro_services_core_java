package com.ecommerce.stock.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.stock.dto.ProductAvailabilityRequestDto;
import com.ecommerce.stock.dto.ProductAvailabilityResponseDto;
import com.ecommerce.stock.service.StockService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
@Slf4j
public class StockController {

	private final StockService stockService;

	@PostMapping("/availability")
	public ResponseEntity<List<ProductAvailabilityResponseDto>> stockAvailability(
			@Valid @RequestBody List<ProductAvailabilityRequestDto> requestList) {
		log.info("in side stockAvailability() method");
		List<ProductAvailabilityResponseDto> responseList = stockService.stockAvailability(requestList);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PostMapping("/update-by-product-id/{operationType}")
	public ResponseEntity<String> stockUpdate(@Valid @RequestBody List<ProductAvailabilityRequestDto> requestList,
			@PathVariable String operationType) {
		log.info("in side stockUpdate() method");
		String response = stockService.stockUpdate(requestList, operationType);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
