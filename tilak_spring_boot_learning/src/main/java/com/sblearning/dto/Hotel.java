package com.sblearning.dto;

import java.util.List;

public class Hotel {

	private String hotelId;
	private String location;
	private Double price;

	private CookingMaster cookingMaster;
	private List<TeaMaster> teaMasterList;




	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public CookingMaster getCookingMaster() {
		return cookingMaster;
	}

	public void setCookingMaster(CookingMaster cookingMaster) {
		this.cookingMaster = cookingMaster;
	}

	public List<TeaMaster> getTeaMasterList() {
		return teaMasterList;
	}

	public void setTeaMasterList(List<TeaMaster> teaMasterList) {
		this.teaMasterList = teaMasterList;
	}



}
