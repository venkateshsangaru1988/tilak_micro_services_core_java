package com.ecommerce.product.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.product.dto.OrderHistoryRequestDto;
import com.ecommerce.product.dto.OrderHistoryResponseDto;

//@FeignClient(name = "e-commerce-order-history-service", url = "${order-history-base-url}")
@FeignClient(name = "e-commerce-order-history-service")
public interface OrderHistoryFeignClient {

	@PostMapping(value = "/e-commerce-order-history/history/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	String saveOrderHistory(@RequestBody List<OrderHistoryRequestDto> requestObj);

	@GetMapping(value = "/e-commerce-order-history/history/order-list/{customerEmailId}/{orderId}")
	List<OrderHistoryResponseDto> getHistoryByEmailAndOrderId(@PathVariable String customerEmailId,
			@PathVariable String orderId);

	@PostMapping(value = "/e-commerce-order-history/history/order-status-update/{orderId}/{orderStatus}")
	String orderStatusUpdate(@PathVariable String orderId, @PathVariable String orderStatus);

	@PostMapping(value = "/e-commerce-order-history/history/order-status-update-by-order-id-product-id/{orderId}/{orderStatus}/{productId}")
	String orderStatusUpdateByOrderIdAndProductId(@PathVariable String orderId, @PathVariable String orderStatus,
			@PathVariable Long productId);

	@GetMapping(value = "/e-commerce-order-history/history/order-list-with-product-id/{customerEmailId}/{orderId}/{productId}")
	List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdAndProductId(@PathVariable String customerEmailId,
			@PathVariable String orderId, @PathVariable Long productId);

	@DeleteMapping(value = "/e-commerce-order-history/history/order-history-delete/{orderId}")
	String deleteOrderHistoryByOrderId(@PathVariable String orderId);

}