package com.restaurant.mapper;

import com.restaurant.dto.country.CountryRequestDto;
import com.restaurant.dto.country.CountryResponseDto;
import com.restaurant.entity.Country;

public class CountryMapper {

	public static CountryResponseDto convertEntityToDto(Country requestObj) {

		CountryResponseDto countryResponse = new CountryResponseDto();
		countryResponse.setCountryId(requestObj.getCountryId());
		countryResponse.setCountryCode(requestObj.getCountryCode());
		countryResponse.setCountryName(requestObj.getCountryName());

		return countryResponse;

	}

	public static Country convertDtoToEntity(CountryRequestDto requestObj) {

		Country countryResponse = new Country();
		countryResponse.setCountryCode(requestObj.getCountryCode());
		countryResponse.setCountryName(requestObj.getCountryName());

		return countryResponse;

	}

	public static Country convertDtoToEntityInUpdate(CountryRequestDto requestObj, Long countryId) {

		Country countryResponse = new Country();
		countryResponse.setCountryId(countryId);
		countryResponse.setCountryCode(requestObj.getCountryCode());
		countryResponse.setCountryName(requestObj.getCountryName());

		return countryResponse;

	}
}
