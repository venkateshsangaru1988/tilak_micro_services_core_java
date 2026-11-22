package com.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.RestaurantRequestDto;
import com.restaurant.dto.RestaurantResponseDto;
import com.restaurant.service.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/restaurant-details")
@Slf4j

@Tag(name = "Restaurant APIs", description = "Operations related to restaurants")

public class RestaurantController {

	private final RestaurantService restaurantService;

	RestaurantController(RestaurantService restaurantService) {
		this.restaurantService = restaurantService;
	}

	@Operation(summary = "Create restaurant", description = "Creates a new restaurant in the system")

	@ApiResponse(responseCode = "201", description = "Restaurant created successfully")

	@PostMapping("/save")
	public ResponseEntity<RestaurantResponseDto> saveRestaurant(@Valid @RequestBody RestaurantRequestDto requestObj) {

		log.info("* In side RestaurantController class  saveRestaurant() method * ");

		RestaurantResponseDto responseObj = restaurantService.saveRestaurantDetails(requestObj);

		log.info("* In side RestaurantController class  saveRestaurant() method after service called * ");

		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@Operation(summary = "Get restaurant by ID")

	@GetMapping("/by-id/{restaurantId}")
	public ResponseEntity<RestaurantResponseDto> getRestaurantDetailsById(

			@Parameter(description = "Unique ID of the restaurant", example = "101", required = true)

			@PathVariable Long restaurantId) {

		log.info("* In side RestaurantController class  getRestaurantDetailsById() method restaurantId is : {} * ",
				restaurantId);

		RestaurantResponseDto responseObj = restaurantService.getRestaurantDetailsById(restaurantId);

		log.info(
				"* In side RestaurantController class  getRestaurantDetailsById() method after service called restaurantId is : {} * ",
				restaurantId);

		return new ResponseEntity<>(responseObj, HttpStatus.OK);

	}

	@GetMapping("/list/{orderType}")
	public ResponseEntity<List<RestaurantResponseDto>> getRestaurantDetails(@PathVariable String orderType) {

		long startTime = System.currentTimeMillis();

		List<RestaurantResponseDto> responseList = restaurantService.getRestaurantDetails(orderType);

		long endTime = System.currentTimeMillis();

		long totalTime = endTime - startTime;

		log.info("===========Total Response time is {} ", totalTime);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PutMapping("/update/{restaurantId}")
	public ResponseEntity<RestaurantResponseDto> updateRestaurant(@Valid @RequestBody RestaurantRequestDto requestObj,
			@PathVariable Long restaurantId) {

		RestaurantResponseDto responseObj = restaurantService.updateRestaurantDetails(requestObj, restaurantId);

		return new ResponseEntity<>(responseObj, HttpStatus.OK);

	}

	@PutMapping("/update-single-value/{restaurantId}/{location}")
	public ResponseEntity<String> updateSingleValue(@PathVariable Long restaurantId, @PathVariable String location) {

		String response = restaurantService.updateSingleValue(restaurantId, location);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/update-single-value-with-native/{restaurantId}/{noOfTables}")
	public ResponseEntity<String> updateSingleValueWithNative(@PathVariable Long restaurantId,
			@PathVariable Integer noOfTables) {

		String response = restaurantService.updateSingleValueWithNative(restaurantId, noOfTables);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping("/update-multi-values-with-native/{restaurantId}/{noOfTables}/{name}")
	public ResponseEntity<String> updateMultiValuesWithNative(@PathVariable Long restaurantId,
			@PathVariable Integer noOfTables, @PathVariable String name) {

		String response = restaurantService.updateMultiValuesWithNative(restaurantId, noOfTables, name);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{restaurantId}")
	public ResponseEntity<String> deleteRecord(@PathVariable Long restaurantId) {

		restaurantService.deleteById(restaurantId);

		return new ResponseEntity<>("Record got deleted with id : " + restaurantId, HttpStatus.OK);

	}

	@DeleteMapping("/delete-with-custom-jpa-query/{restaurantId}")
	public ResponseEntity<String> deleteByIdWithCustomJpaQuery(@PathVariable Long restaurantId) {

		restaurantService.deleteByIdWithCustomJpaQuery(restaurantId);

		return new ResponseEntity<>("Record got deleted with custom jpa query & id : " + restaurantId, HttpStatus.OK);

	}

	@DeleteMapping("/delete-with-custom-native-query/{restaurantId}")
	public ResponseEntity<String> deleteWithNativeQuery(@PathVariable Long restaurantId) {

		restaurantService.deleteByIdWithCustomNativeQuery(restaurantId);

		return new ResponseEntity<>("Record got deleted with custom native query & id : " + restaurantId,
				HttpStatus.OK);

	}

	@GetMapping("/list-with-location/{location}")
	public ResponseEntity<List<RestaurantResponseDto>> getRestaurantDetailsByLocation(@PathVariable String location) {

		List<RestaurantResponseDto> responseList = restaurantService.getRestaurantDetailsByLocation(location);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@GetMapping("/list-with-location-with-like-match/{location}")
	public ResponseEntity<List<RestaurantResponseDto>> getRestaurantDetailsByLocationWithLikeMatch(
			@PathVariable String location) {

		List<RestaurantResponseDto> responseList = restaurantService
				.getRestaurantDetailsByLocationWithLikeMatch(location);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@GetMapping("/list-with-location-and-name-with-like-match/{location}/{name}")
	public ResponseEntity<List<RestaurantResponseDto>> getRestaurantDetailsByLocationAndNameWithLikeMatch(
			@PathVariable String location, @PathVariable String name) {

		List<RestaurantResponseDto> responseList = restaurantService
				.getRestaurantDetailsByLocationAndNameWithLikeMatch(location, name);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

}
