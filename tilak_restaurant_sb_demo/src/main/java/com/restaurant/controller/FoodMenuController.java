package com.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.dto.FoodMenuRequestDto;
import com.restaurant.dto.FoodMenuResponseDto;
import com.restaurant.service.FoodMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/food-menu")
@Slf4j
@Tag(name = "Food Menu APIs", description = "Operations related to restaurants food menu")
public class FoodMenuController {

	private final FoodMenuService foodMenuService;

	FoodMenuController(FoodMenuService foodMenuService) {
		this.foodMenuService = foodMenuService;
	}

	@Operation(summary = "Create food menu", description = "Creates a new food item in restaurant")

	@ApiResponse(responseCode = "201", description = "Food menu created successfully")

	@PostMapping("/save")
	public ResponseEntity<FoodMenuResponseDto> saveFoodMenu(@Valid @RequestBody FoodMenuRequestDto requestObj) {

		log.info("* In side FoodMenuController class  saveFoodMenu() method * ");

		FoodMenuResponseDto responseObj = foodMenuService.saveFoodMenuDetails(requestObj);

		log.info("* In side FoodMenuController class  saveFoodMenu() method after service called * ");

		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@Operation(summary = "Get Food menus by ID")

	@GetMapping("/food-menus/{restaurantId}")
	public ResponseEntity<List<FoodMenuResponseDto>> getFoodMenuDetailsById(

			@Parameter(description = "Unique ID of the restaurant", example = "101", required = true)

			@PathVariable Long restaurantId) {

		log.info("* In side FoodMenuController class  getFoodMenuDetailsById() method restaurantId is : {} * ",
				restaurantId);

		List<FoodMenuResponseDto> responseList = foodMenuService.getFoodMenuByRestaurantId(restaurantId);

		log.info(
				"* In side FoodMenuController class  getFoodMenuDetailsById() method after service called restaurantId is : {} * ",
				restaurantId);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@Operation(summary = "Get Food menus by ID")

	@GetMapping("/restaurants/{itemName}")
	public ResponseEntity<List<FoodMenuResponseDto>> getRestaurantDetailsByItemName(

			@Parameter(description = "Item name", example = "Biryani", required = true)

			@PathVariable String itemName) {

		log.info("* In side FoodMenuController class  getRestaurantDetailsByItemName() method item name is : {} * ",
				itemName);

		List<FoodMenuResponseDto> responseList = foodMenuService.getRestaurantsByItemName(itemName);

		log.info(
				"* In side FoodMenuController class  getRestaurantDetailsByItemName() method after service called item name is : {} * ",
				itemName);

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

}
