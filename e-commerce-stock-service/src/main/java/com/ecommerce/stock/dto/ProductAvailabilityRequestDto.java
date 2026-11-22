package com.ecommerce.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailabilityRequestDto {

	private Long productId;

	private String userEmail;

	private Long qty;

}
