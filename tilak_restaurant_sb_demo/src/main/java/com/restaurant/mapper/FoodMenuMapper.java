package com.restaurant.mapper;

import com.restaurant.dto.FoodMenuResponseDto;
import com.restaurant.dto.RestaurantResponseDto;
import com.restaurant.entity.FoodMenu;

public class FoodMenuMapper {

	public static FoodMenuResponseDto convertEntityToDto(FoodMenu requestObj) {

		FoodMenuResponseDto foodMenuResponse = new FoodMenuResponseDto();
		RestaurantResponseDto restaurantObj = RestaurantMapper.convertEntityToDto(requestObj.getRestaurant());
		foodMenuResponse.setRestaurant(restaurantObj);
		foodMenuResponse.setFoodMenuId(requestObj.getFoodMenuId());
		foodMenuResponse.setItemName(requestObj.getItemName());
		foodMenuResponse.setPrice(requestObj.getPrice());
		return foodMenuResponse;

	}

}
