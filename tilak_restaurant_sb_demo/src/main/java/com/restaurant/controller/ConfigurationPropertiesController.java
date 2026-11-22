package com.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.properties.RestaurantCustomProperties;
import com.restaurant.properties.ShopCustomProperties;

@RestController
@RequestMapping("/configuration")
public class ConfigurationPropertiesController {

	private final RestaurantCustomProperties restaurantCustomProperties;

	private final ShopCustomProperties shopCustomProperties;

	ConfigurationPropertiesController(RestaurantCustomProperties restaurantCustomProperties,
			ShopCustomProperties shopCustomProperties) {
		this.restaurantCustomProperties = restaurantCustomProperties;
		this.shopCustomProperties = shopCustomProperties;
	}

	@GetMapping("/details")
	public ResponseEntity<String> getConfigurationDetails() {

		String message = "Latest Updated Restuarant location : " + restaurantCustomProperties.getLocation()
				+ " , owner : " + restaurantCustomProperties.getOwner() + " , Shop Details : location : "
				+ shopCustomProperties.getLocation() + " , owner : " + shopCustomProperties.getOwner() + " and type : "
				+ shopCustomProperties.getType();

		return new ResponseEntity<>(message, HttpStatus.OK);

	}

}
