package com.sblearning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel")
public class HotelController {


	@GetMapping("/one")
	public String hotelDetailsOne() {

		return "My Hotel name is : Hyderabad Spice";
	}

	@GetMapping("/two")
	public String hotelDetailsTwo() {

		return "My Second Hotel name is : Mehfil";
	}

	// Path Variable
	// End point url : http://localhost:8383/hotel/dynamic-one/Alpha
	@GetMapping("/dynamic-one/{name}")
	public String hotelDetailsOneDynamic(@PathVariable String name) {

		return "My Hotel name is : "+name;
	}

	// Path Variable with 2 values
	// End point url : http://localhost:8383/hotel/dynamic-one/Alpha/Hyd
	@GetMapping("/dynamic-one/{name}/{location}")
	public String hotelDetailsOneDynamic(@PathVariable String name,@PathVariable String location) {

		return "My Hotel name is : "+name + " and located in : "+location;
	}

	// Path Variable with variable different
	// End point url : http://localhost:8383/hotel/dynamic-one-with-diff/Alpha
	@GetMapping("/dynamic-one-with-diff/{name}")
	public String hotelDetailsOneDynamicValue(@PathVariable("name") String dynanicName) {

		return "My Hotel name is : "+dynanicName;
	}

	// Request Variable (Query param)
	// End point url : http://localhost:8383/hotel/dynamic-one-with-request-param
	@GetMapping("/dynamic-one-with-request-param")
	public String hotelDetailsOneDynamicRequestVariable(@RequestParam String name) {

		return "My Hotel name is : "+name;
	}

	// Request Variable with 2 values (Query param)
	// End point url : http://localhost:8383/hotel/dynamic-one-with-request-param
	@GetMapping("/dynamic-two-with-request-param")
	public String hotelDetailsTwoDynamicRequestVariable(@RequestParam(defaultValue = "Angara" ) String name,
			@RequestParam(defaultValue = "India") String location) {

		return "My Hotel name is : "+name+" located in : "+location;
	}




}
