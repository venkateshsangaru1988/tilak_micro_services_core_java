package com.restaurant.utility;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.restaurant.entity.Restaurant;
import com.restaurant.exceptionshandler.RestaurantCustomException;
import com.restaurant.repository.RestaurantRepository;

@Component
public class ApplicationUtility {

	// Constructor injection
	private final RestaurantRepository restaurantRepository;

	ApplicationUtility(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}

	public Restaurant findById(Long restaurantId) {

		Optional<Restaurant> restaurantObj = restaurantRepository.findById(restaurantId);

		if (restaurantObj.isPresent()) {

			return restaurantObj.get();
		}

		throw new RestaurantCustomException("Restaurant is not exist with given id : " + restaurantId);
	}

}
