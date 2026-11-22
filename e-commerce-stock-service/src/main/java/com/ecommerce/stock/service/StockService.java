package com.ecommerce.stock.service;

import java.util.List;

import com.ecommerce.stock.dto.ProductAvailabilityRequestDto;
import com.ecommerce.stock.dto.ProductAvailabilityResponseDto;

public interface StockService {

	List<ProductAvailabilityResponseDto> stockAvailability(List<ProductAvailabilityRequestDto> requestList);

	String stockUpdate(List<ProductAvailabilityRequestDto> reqObjList, String operationType);
}
