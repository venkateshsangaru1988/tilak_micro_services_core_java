package com.restaurant.mapper;

import java.time.LocalDateTime;

import com.restaurant.dto.RestaurantRequestDto;
import com.restaurant.dto.RestaurantResponseDto;
import com.restaurant.entity.Restaurant;

public class RestaurantMapper {

	public static Restaurant convertDtoToEntity(RestaurantRequestDto requestObj) {

		Restaurant rest = new Restaurant();
		rest.setName(requestObj.getName());
		rest.setLocation(requestObj.getLocation());
		rest.setNoOfTables(requestObj.getNoOfTables());
		rest.setCreatedBy("Tilak");
		rest.setCreatedDateTime(LocalDateTime.now());
		return rest;

	}

	public static Restaurant convertDtoToEntityUpdateCase(RestaurantRequestDto requestObj, Long restaurantId,
			Restaurant rest) {

		rest.setRestaurantId(restaurantId);
		rest.setName(requestObj.getName());
		rest.setLocation(requestObj.getLocation());
		rest.setNoOfTables(requestObj.getNoOfTables());
		rest.setUpdatedBy("Venky");
		rest.setUpdatedDateTime(LocalDateTime.now());
		return rest;

	}

	public static RestaurantResponseDto convertEntityToDto(Restaurant requestObj) {

		RestaurantResponseDto rest = new RestaurantResponseDto();
		rest.setRestaurantId(requestObj.getRestaurantId());
		rest.setName(requestObj.getName());
		rest.setLocation(requestObj.getLocation());
		rest.setNoOfTables(requestObj.getNoOfTables());
		rest.setCreatedBy(requestObj.getCreatedBy());
		rest.setCreatedDateTime(requestObj.getCreatedDateTime());
		rest.setUpdatedBy(requestObj.getUpdatedBy());
		rest.setUpdatedDateTime(requestObj.getUpdatedDateTime());
		return rest;

	}

}
