package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.constants.ApplicationUtilsConstants;
import com.restaurant.dto.RestaurantRequestDto;
import com.restaurant.dto.RestaurantResponseDto;
import com.restaurant.entity.Restaurant;
import com.restaurant.mapper.RestaurantMapper;
import com.restaurant.repository.RestaurantRepository;
import com.restaurant.service.RestaurantService;
import com.restaurant.utility.ApplicationUtility;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantServiceImpl implements RestaurantService {

	// Constructor injection
	private final RestaurantRepository restaurantRepository;
	private final ApplicationUtility applicationUtility;

	RestaurantServiceImpl(RestaurantRepository restaurantRepository, ApplicationUtility applicationUtility) {
		this.restaurantRepository = restaurantRepository;
		this.applicationUtility = applicationUtility;
	}

	@Override
	@Transactional
	public RestaurantResponseDto saveRestaurantDetails(RestaurantRequestDto requestObj) {

		log.info(" * In side RestaurantServiceImpl class saveRestaurantDetails() method ");
		Restaurant restEntity = RestaurantMapper.convertDtoToEntity(requestObj);

		restEntity = restaurantRepository.save(restEntity);

		log.info(" * In side RestaurantServiceImpl class saveRestaurantDetails() method after persist");

		return RestaurantMapper.convertEntityToDto(restEntity);
	}

	@Override
	public RestaurantResponseDto getRestaurantDetailsById(Long restaurantId) {

		Restaurant restaurantObj = applicationUtility.findById(restaurantId);

		return RestaurantMapper.convertEntityToDto(restaurantObj);

	}

	@Override
	public List<RestaurantResponseDto> getRestaurantDetails(String orderType) {
		List<RestaurantResponseDto> responseList = new ArrayList<>();
		List<Restaurant> restaurantList = new ArrayList<>();

		if (ApplicationUtilsConstants.ORDER_TYPE_ASC.equalsIgnoreCase(orderType)) {

			restaurantList = restaurantRepository.findAll();
		} else {
			restaurantList = restaurantRepository.findAllByOrderByRestaurantIdDesc();
		}

		restaurantList.forEach(rest -> {

			responseList.add(RestaurantMapper.convertEntityToDto(rest));
		});
		return responseList;
	}

	@Override
	@Transactional
	public RestaurantResponseDto updateRestaurantDetails(RestaurantRequestDto requestObj, Long restaurantId) {

		System.out
				.println("---requestObj location --" + requestObj.getLocation() + "---Name ---" + requestObj.getName());
		Restaurant restaurantObj = applicationUtility.findById(restaurantId);

		System.out.println("---OLD DB restaurantObj location --" + restaurantObj.getLocation() + "---Name ---"
				+ restaurantObj.getName() + "--id--" + restaurantObj.getRestaurantId());

		restaurantObj = RestaurantMapper.convertDtoToEntityUpdateCase(requestObj, restaurantId, restaurantObj);

		System.out.println("---NEW VALUES AFTER Conversion restaurantObj location --" + restaurantObj.getLocation()
				+ "---Name ---" + restaurantObj.getName() + "--id--" + restaurantObj.getRestaurantId());

		restaurantObj = restaurantRepository.save(restaurantObj);

		return RestaurantMapper.convertEntityToDto(restaurantObj);
	}

	@Override
	public String updateSingleValue(Long restaurantId, String inputValue) {
		restaurantRepository.updateSingleValue(restaurantId, inputValue);
		return "Record is updated with id : " + restaurantId;
	}

	@Override
	public String updateSingleValueWithNative(Long restaurantId, Integer noOfTables) {
		restaurantRepository.updateSingleValueWithNative(restaurantId, noOfTables);
		return "Record is updated with native query & id : " + restaurantId;
	}

	@Override
	public String updateMultiValuesWithNative(Long restaurantId, Integer noOfTables, String name) {
		restaurantRepository.updateMultiValuesWithNative(restaurantId, noOfTables, name);
		return "Record is updated with multiple values native query & id : " + restaurantId;
	}

	@Override
	public void deleteById(Long restaurantId) {

		Restaurant restaurantObj = applicationUtility.findById(restaurantId);
		restaurantRepository.delete(restaurantObj);

	}

	@Override
	public void deleteByIdWithCustomJpaQuery(Long restaurantId) {

		restaurantRepository.deleteWithJpaQuery(restaurantId);
	}

	@Override
	public void deleteByIdWithCustomNativeQuery(Long restaurantId) {
		restaurantRepository.deleteWithNativeQuery(restaurantId);

	}

	@Override
	public List<RestaurantResponseDto> getRestaurantDetailsByLocation(String location) {
		List<RestaurantResponseDto> responseList = new ArrayList<>();
		List<Restaurant> restaurantList = restaurantRepository.findAllByLocationOrderByRestaurantIdDesc(location);

		restaurantList.forEach(rest -> {

			responseList.add(RestaurantMapper.convertEntityToDto(rest));
		});
		return responseList;
	}

	@Override
	public List<RestaurantResponseDto> getRestaurantDetailsByLocationWithLikeMatch(String location) {
		List<RestaurantResponseDto> responseList = new ArrayList<>();
		List<Restaurant> restaurantList = restaurantRepository
				.findAllByLocationContainingIgnoreCaseOrderByRestaurantIdDesc(location);

		restaurantList.forEach(rest -> {

			responseList.add(RestaurantMapper.convertEntityToDto(rest));
		});
		return responseList;
	}

	@Override
	public List<RestaurantResponseDto> getRestaurantDetailsByLocationAndNameWithLikeMatch(String location,
			String name) {

		List<RestaurantResponseDto> responseList = new ArrayList<>();
		List<Restaurant> restaurantList = restaurantRepository
				.findAllByLocationContainingIgnoreCaseAndNameContainingIgnoreCaseOrderByRestaurantIdDesc(location,
						name);

		restaurantList.forEach(rest -> {

			responseList.add(RestaurantMapper.convertEntityToDto(rest));
		});
		return responseList;

	}

}
