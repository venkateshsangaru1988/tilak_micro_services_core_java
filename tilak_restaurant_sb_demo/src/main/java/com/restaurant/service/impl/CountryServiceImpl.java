package com.restaurant.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.restaurant.dto.country.CountryRequestDto;
import com.restaurant.dto.country.CountryResponseDto;
import com.restaurant.entity.Country;
import com.restaurant.exceptionshandler.CountryNotFoundException;
import com.restaurant.mapper.CountryMapper;
import com.restaurant.repository.CountryRepository;
import com.restaurant.service.CountryService;

import jakarta.transaction.Transactional;

@Service
public class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	@Cacheable(value = "countries_cache", key = "#countryId")
	@Transactional
	public CountryResponseDto saveCountry(CountryRequestDto countryObj) {

		Country countryEntity = CountryMapper.convertDtoToEntity(countryObj);
		countryEntity = countryRepository.save(countryEntity);
		return CountryMapper.convertEntityToDto(countryEntity);
	}

	@Override
	@Cacheable(value = "countries_cache", key = "'all'")
	public List<CountryResponseDto> findAll() {
		List<CountryResponseDto> responseList = new ArrayList<>();
		List<Country> countryList = countryRepository.findAll();
		countryList.forEach(country -> {

			responseList.add(CountryMapper.convertEntityToDto(country));
		});

		return responseList;
	}

	@Override
	@CachePut(value = "countries_cache", key = "#countryId")
	@Transactional
	public CountryResponseDto updateCountry(CountryRequestDto countryObj, Long countryId) {
		Country countryEntity = CountryMapper.convertDtoToEntityInUpdate(countryObj, countryId);
		countryEntity = countryRepository.save(countryEntity);
		return CountryMapper.convertEntityToDto(countryEntity);
	}

	@Override
	@Cacheable(value = "countries_cache", key = "#countryId")

	public CountryResponseDto findById(Long countryId) {

		Optional<Country> countryObj = countryRepository.findById(countryId);
		if (countryObj.isEmpty()) {
			throw new CountryNotFoundException("Country is not available with given id : " + countryId);
		}
		return CountryMapper.convertEntityToDto(countryObj.get());
	}

	@Override
	@Caching(evict = { @CacheEvict(value = "countries_cache", key = "'all'"),
			@CacheEvict(value = "countries_cache", key = "#countryId") })

	@Transactional
	public String deleteById(Long countryId) {
		Optional<Country> countryObj = countryRepository.findById(countryId);
		if (countryObj.isEmpty()) {
			throw new CountryNotFoundException("Country is not available with given id : " + countryId);
		}
		countryRepository.delete(countryObj.get());
		return "Country Deleted with id " + countryId;
	}

}
