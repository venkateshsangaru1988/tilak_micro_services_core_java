package com.ecommerce.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAvailabilityResponseDto {

	private Long productId;
	private Long stockAvailable;
}
