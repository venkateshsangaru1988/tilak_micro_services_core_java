package com.ecommerce.product.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.product.dto.ProductAvailabilityRequestDto;
import com.ecommerce.product.dto.ProductAvailabilityResponseDto;

//@FeignClient(name = "e-commerce-stock-service", url = "${stock-base-url}")
@FeignClient(name = "e-commerce-stock-service")
public interface StockFeignClient {

	@PostMapping(value = "/e-commerce-stock/stock/availability", consumes = MediaType.APPLICATION_JSON_VALUE)
	List<ProductAvailabilityResponseDto> stockAvailability(@RequestBody List<ProductAvailabilityRequestDto> requestObj);

	@PostMapping(value = "/e-commerce-stock/stock/update-by-product-id/{operationType}", consumes = MediaType.APPLICATION_JSON_VALUE)
	String stockUpdate(@RequestBody List<ProductAvailabilityRequestDto> requestObj, @PathVariable String operationType);

}