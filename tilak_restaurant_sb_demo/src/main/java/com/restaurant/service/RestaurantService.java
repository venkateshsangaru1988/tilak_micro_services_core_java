package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.RestaurantRequestDto;
import com.restaurant.dto.RestaurantResponseDto;

public interface RestaurantService {

	RestaurantResponseDto saveRestaurantDetails(RestaurantRequestDto requestObj);

	RestaurantResponseDto getRestaurantDetailsById(Long restaurantId);

	List<RestaurantResponseDto> getRestaurantDetails(String orderType);

	RestaurantResponseDto updateRestaurantDetails(RestaurantRequestDto requestObj, Long restaurantId);

	String updateSingleValue(Long restaurantId, String inputValue);

	String updateSingleValueWithNative(Long restaurantId, Integer noOfTables);

	String updateMultiValuesWithNative(Long restaurantId, Integer noOfTables, String name);

	void deleteById(Long restaurantId);

	void deleteByIdWithCustomJpaQuery(Long restaurantId);

	void deleteByIdWithCustomNativeQuery(Long restaurantId);

	List<RestaurantResponseDto> getRestaurantDetailsByLocation(String location);

	List<RestaurantResponseDto> getRestaurantDetailsByLocationWithLikeMatch(String location);

	List<RestaurantResponseDto> getRestaurantDetailsByLocationAndNameWithLikeMatch(String location, String name);

}
