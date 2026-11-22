package com.inmemory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/restauarant-in-memory")
@Slf4j
public class RestaurantInMemoryController {
	
	private final RestaurantInMemoryRepository restaurantInMemoryRepository;


	RestaurantInMemoryController(RestaurantInMemoryRepository restaurantInMemoryRepository) {
		this.restaurantInMemoryRepository = restaurantInMemoryRepository;
	}
	
	
	@PostMapping("/save")
	public ResponseEntity<RestaurantInMemory> saveRestaurant(@Valid @RequestBody RestaurantInMemory requestObj){

		log.info("* In side RestaurantController class  saveRestaurant() method * ");

		RestaurantInMemory responseObj = restaurantInMemoryRepository.save(requestObj);

		log.info("* In side RestaurantController class  saveRestaurant() method after service called * ");

		return new ResponseEntity<>(responseObj,HttpStatus.CREATED);

	}

}
