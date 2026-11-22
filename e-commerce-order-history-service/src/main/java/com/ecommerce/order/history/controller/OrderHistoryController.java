package com.ecommerce.order.history.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.order.history.dto.OrderHistoryRequestDto;
import com.ecommerce.order.history.dto.OrderHistoryResponseDto;
import com.ecommerce.order.history.service.OrderHistoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Slf4j
public class OrderHistoryController {

	private final OrderHistoryService orderHistoryService;

	@PostMapping("/save")
	public ResponseEntity<String> saveOrderHistory(@Valid @RequestBody List<OrderHistoryRequestDto> requestList) {
		log.info("in side saveOrderHistory() method");
		String response = orderHistoryService.saveOrderHistory(requestList);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@PostMapping("/order-status-update/{orderId}/{orderStatus}")
	public ResponseEntity<String> orderStatusUpdate(@PathVariable String orderId, @PathVariable String orderStatus) {
		log.info("in side orderStatusUpdate() method");
		String response = orderHistoryService.orderStatusUpdate(orderId, orderStatus);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping("/order-list/{customerEmailId}/{orderId}")
	public ResponseEntity<List<OrderHistoryResponseDto>> getHistoryByEmailAndOrderId(
			@PathVariable String customerEmailId, @PathVariable String orderId) {
		log.info("in side getHistoryByEmailAndOrderId() method");
		List<OrderHistoryResponseDto> responseList = orderHistoryService.getHistoryByEmailAndOrderId(customerEmailId,
				orderId);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PostMapping("/order-status-update-by-order-id-product-id/{orderId}/{orderStatus}/{productId}")
	public ResponseEntity<String> orderStatusUpdateByOrderIdAndProductId(@PathVariable String orderId,
			@PathVariable String orderStatus, @PathVariable Long productId) {
		log.info("in side orderStatusUpdateByOrderIdAndProductId() method");
		String response = orderHistoryService.updateOrderHistoryByOrderIdAndProductId(orderId, orderStatus, productId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	@GetMapping("/order-list-with-product-id/{customerEmailId}/{orderId}/{productId}")
	public ResponseEntity<List<OrderHistoryResponseDto>> getHistoryByEmailAndOrderIdAndProductId(
			@PathVariable String customerEmailId, @PathVariable String orderId, @PathVariable Long productId) {
		log.info("in side getHistoryByEmailAndOrderIdAndProductId() method");
		List<OrderHistoryResponseDto> responseList = orderHistoryService
				.getHistoryByEmailAndOrderIdAndProductId(customerEmailId, orderId, productId);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@GetMapping("/history-order-list/{orderStatus}")
	public ResponseEntity<List<OrderHistoryResponseDto>> getHistoryRecordsByOrderStatus(
			@PathVariable String orderStatus) {
		log.info("in side getHistoryRecordsByOrderStatus() method");
		List<OrderHistoryResponseDto> responseList = orderHistoryService.getHistoryRecordsByOrderStatus(orderStatus);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@DeleteMapping("/order-history-delete/{orderId}")
	public ResponseEntity<String> deleteOrderHistoryByOrderId(@PathVariable String orderId) {
		log.info("in side deleteOrderHistoryByOrderId() method");
		String response = orderHistoryService.deleteOrderHistoryByOrderId(orderId);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

}
