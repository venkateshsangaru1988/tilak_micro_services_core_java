package com.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodMenuResponseDto {

	private RestaurantResponseDto restaurant;

	private Long foodMenuId;

	private String itemName;

	private Double price;
}
