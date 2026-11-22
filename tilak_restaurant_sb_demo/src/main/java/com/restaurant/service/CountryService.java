package com.restaurant.service;

import java.util.List;

import com.restaurant.dto.country.CountryRequestDto;
import com.restaurant.dto.country.CountryResponseDto;

public interface CountryService {

	CountryResponseDto saveCountry(CountryRequestDto countryObj);

	List<CountryResponseDto> findAll();

	CountryResponseDto updateCountry(CountryRequestDto countryObj, Long countryId);

	CountryResponseDto findById(Long countryId);

	String deleteById(Long countryId);
}
