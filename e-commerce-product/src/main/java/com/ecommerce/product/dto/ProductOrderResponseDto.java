package com.ecommerce.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderResponseDto {

	private List<ProductOrderItemResponseDto> products;
	private Double grandTotal;
	private String orderId;
}