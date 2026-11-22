package com.restaurant.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuRequestDto {

	@NotNull(message = "Restaurant name is required")
	private Long restaurantId;

	@NotNull(message = "Item name name is required")
	private String itemName;

	@NotNull(message = "Price is required")
	private Double price;
}
