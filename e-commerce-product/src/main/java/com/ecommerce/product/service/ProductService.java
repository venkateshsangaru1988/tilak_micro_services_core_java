package com.ecommerce.product.service;

import java.util.List;

import com.ecommerce.product.dto.CancelItemsListDto;
import com.ecommerce.product.dto.ProductAvailabilityRequestDto;
import com.ecommerce.product.dto.ProductOrderResponseDto;
import com.ecommerce.product.dto.ProductResponseDto;

public interface ProductService {

	List<ProductResponseDto> getAllProducts();

	ProductOrderResponseDto placeOrder(String emailId, List<ProductAvailabilityRequestDto> requestList);

	String cancelOrder(String emailId, String orderId);

	String partialCancelOrder(List<CancelItemsListDto> cancelList, String emailId, String orderId);

}
