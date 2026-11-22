package com.sblearning.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sblearning.dto.CookingMaster;
import com.sblearning.dto.Hotel;
import com.sblearning.dto.TeaMaster;

@RestController
@RequestMapping("/hotel-dynamic")
public class HotelRequestController {


	@PostMapping("/required-details")
	public String getHotelDetails(@RequestBody Hotel hotelObj) {

		return "Hotel Id : "+hotelObj.getHotelId()+" location : "+hotelObj.getLocation()+" and daily price is : "+hotelObj.getPrice();

	}

	@PostMapping("/required-details-with-object")
	public Hotel getHotelDetailsWithObject(@RequestBody Hotel hotelObj) {

		return hotelObj;

	}
	
	
	@GetMapping("/required-details-with-list")
	public List<Hotel> getHotelDetailsList() {

		
		List<Hotel> hotelsList = new ArrayList<>();
		
		List<TeaMaster> teaMasterList = new ArrayList<>();
		
		TeaMaster gingerMaster = new TeaMaster();
		gingerMaster.setMasterName("Venky");
		gingerMaster.setSalary(8000d);
		
		
		TeaMaster greenTeaMaster = new TeaMaster();
		greenTeaMaster.setMasterName("Tilak");
		greenTeaMaster.setSalary(9000d);
		
		teaMasterList.add(greenTeaMaster);
		teaMasterList.add(gingerMaster);
		
		Hotel hotelObjOne = new Hotel();
		hotelObjOne.setHotelId("0001");
		hotelObjOne.setLocation("Hyd");
		hotelObjOne.setPrice(7000d);
		
		hotelObjOne.setTeaMasterList(teaMasterList);
		
		
		CookingMaster master = new CookingMaster();
		master.setMasterName("Sai");
		master.setSalary(20000d);
		
		
		Hotel hotelObjTwo = new Hotel();
		hotelObjTwo.setHotelId("0002");
		hotelObjTwo.setLocation("Banglore");
		hotelObjTwo.setPrice(9000d);
		hotelObjTwo.setCookingMaster(master);
		
		Hotel hotelObjThree = new Hotel();
		hotelObjThree.setHotelId("0003");
		hotelObjThree.setLocation("Kolkata");
		hotelObjThree.setPrice(10000d);
		
		hotelsList.add(hotelObjOne);
		hotelsList.add(hotelObjTwo);
		hotelsList.add(hotelObjThree);
		
		return hotelsList;

	}

}
