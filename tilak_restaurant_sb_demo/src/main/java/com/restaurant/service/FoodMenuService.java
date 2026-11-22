package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.FoodMenuRequestDto;
import com.restaurant.dto.FoodMenuResponseDto;

public interface FoodMenuService {

	FoodMenuResponseDto saveFoodMenuDetails(FoodMenuRequestDto requestObj);

	List<FoodMenuResponseDto> getFoodMenuByRestaurantId(Long restaurantId);

	List<FoodMenuResponseDto> getRestaurantsByItemName(String itemName);
}
