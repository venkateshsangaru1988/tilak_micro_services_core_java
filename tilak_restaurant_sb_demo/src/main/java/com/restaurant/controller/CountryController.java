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

import com.restaurant.dto.country.CountryRequestDto;
import com.restaurant.dto.country.CountryResponseDto;
import com.restaurant.service.CountryService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/country")
@Slf4j
public class CountryController {

	private final CountryService countryService;

	CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@PostMapping("/save")
	public ResponseEntity<CountryResponseDto> saveCountry(@Valid @RequestBody CountryRequestDto requestObj) {

		log.info("* In side CountryController class  saveCountry() method * ");

		CountryResponseDto responseObj = countryService.saveCountry(requestObj);

		log.info("* In side CountryController class  saveCountry() method after service called * ");

		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public ResponseEntity<List<CountryResponseDto>> getCountries() {

		log.info("* In side CountryController class  getCountries() method  * ");

		List<CountryResponseDto> responseList = countryService.findAll();

		log.info("* In side CountryController class  getCountries() method after service called ");

		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PutMapping("/update/{countryId}")
	public ResponseEntity<CountryResponseDto> updateCountry(@Valid @RequestBody CountryRequestDto requestObj,
			@PathVariable Long countryId) {

		log.info("* In side CountryController class  updateCountry() method * ");

		CountryResponseDto responseObj = countryService.updateCountry(requestObj, countryId);

		log.info("* In side CountryController class  updateCountry() method after service called * ");

		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@GetMapping("/single-record/{countryId}")
	public ResponseEntity<CountryResponseDto> getCountryById(@PathVariable Long countryId) {

		log.info("* In side CountryController class  getCountryById() method  * ");

		CountryResponseDto responseObj = countryService.findById(countryId);

		log.info("* In side CountryController class  getCountryById() method after service called ");

		return new ResponseEntity<>(responseObj, HttpStatus.OK);

	}

	@DeleteMapping("/delete-record/{countryId}")
	public ResponseEntity<String> deleteById(@PathVariable Long countryId) {

		log.info("* In side CountryController class  deleteById() method  * ");

		String response = countryService.deleteById(countryId);

		log.info("* In side CountryController class  deleteById() method after service called ");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
