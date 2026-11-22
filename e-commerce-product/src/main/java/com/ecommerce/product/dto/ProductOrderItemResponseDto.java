package com.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderItemResponseDto {

	private String productName;
	private Long qty;
	private Double productPrice;
	private Double totalPrice;
}