package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.dto.FoodMenuRequestDto;
import com.restaurant.dto.FoodMenuResponseDto;
import com.restaurant.entity.FoodMenu;
import com.restaurant.entity.Restaurant;
import com.restaurant.exceptionshandler.ItemNotAvailableException;
import com.restaurant.mapper.FoodMenuMapper;
import com.restaurant.repository.FoodMenuRepository;
import com.restaurant.service.FoodMenuService;
import com.restaurant.utility.ApplicationUtility;

@Service
public class FoodMenuServiceImpl implements FoodMenuService {

	private final FoodMenuRepository foodMenuRepository;

	private final ApplicationUtility applicationUtility;

	FoodMenuServiceImpl(FoodMenuRepository foodMenuRepository, ApplicationUtility applicationUtility) {
		this.foodMenuRepository = foodMenuRepository;
		this.applicationUtility = applicationUtility;
	}

	@Override
	public FoodMenuResponseDto saveFoodMenuDetails(FoodMenuRequestDto requestObj) {

		Restaurant restaurantObj = applicationUtility.findById(requestObj.getRestaurantId());
		FoodMenu foodMenu = new FoodMenu();
		// Relation setting with Parent
		foodMenu.setRestaurant(restaurantObj);
		foodMenu.setItemName(requestObj.getItemName());
		foodMenu.setPrice(requestObj.getPrice());
		foodMenu = foodMenuRepository.save(foodMenu);
		return FoodMenuMapper.convertEntityToDto(foodMenu);
	}

	@Override
	public List<FoodMenuResponseDto> getFoodMenuByRestaurantId(Long restaurantId) {
		Restaurant restaurantObj = applicationUtility.findById(restaurantId);
		List<FoodMenu> foodMenuResponseList = foodMenuRepository
				.findAllByRestaurantRestaurantId(restaurantObj.getRestaurantId());

		List<FoodMenuResponseDto> responseList = restaurantListDisplay(foodMenuResponseList);

		return responseList;
	}

	private List<FoodMenuResponseDto> restaurantListDisplay(List<FoodMenu> foodMenuResponseList) {
		List<FoodMenuResponseDto> responseList = new ArrayList<>();
		if (!foodMenuResponseList.isEmpty()) {

			foodMenuResponseList.forEach(food -> {

				responseList.add(FoodMenuMapper.convertEntityToDto(food));
			});
		} else {
			throw new ItemNotAvailableException("Sorry Item is not avilable");
		}
		return responseList;
	}

	@Override
	public List<FoodMenuResponseDto> getRestaurantsByItemName(String itemName) {
		List<FoodMenu> foodMenuResponseList = foodMenuRepository
				.findAllByItemNameContainingIgnoreCaseOrderByFoodMenuIdDesc(itemName);

		List<FoodMenuResponseDto> responseList = restaurantListDisplay(foodMenuResponseList);

		return responseList;
	}

}
