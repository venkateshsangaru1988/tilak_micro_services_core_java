package com.restaurant.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.exceptionshandler.PersonNotAvailableException;

@RestController
@RequestMapping("/properties")
public class PropertiesController {

	@Value("${person.email}")
	private String personEmailId;

	@Value("${dog.email}")
	private String dogEmailId;

	@Value("${restaurant.location}")
	private String restaurantLocation;

	@Value("${restaurant.owner}")
	private String restaurantOwner;

	@Value("${shop.location}")
	private String shopLocation;

	@Value("${shop.owner}")
	private String shopOwner;

	@Value("${shop.type}")
	private String shopType;

	@GetMapping("/details/{personId}")
	public ResponseEntity<String> getRestaurantDetails(@PathVariable String personId) {

		String message = "";

		if (personId.equalsIgnoreCase("Tilak")) {
			message = " Person Details Id : " + personId + " , mail id : " + personEmailId + " , dog email : "
					+ dogEmailId + " , location is : " + restaurantLocation + " , owner name is : " + restaurantOwner
					+ " , Shop location : " + shopLocation + " , Owner : " + shopOwner + " and Type : " + shopType;
		} else {

			throw new PersonNotAvailableException("Person is not avialble");
		}

		return new ResponseEntity<>(message, HttpStatus.OK);

	}

}
