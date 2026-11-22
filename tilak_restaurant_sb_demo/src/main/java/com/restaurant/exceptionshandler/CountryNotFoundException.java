package com.restaurant.exceptionshandler;

public class CountryNotFoundException extends RestaurantBaseApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public CountryNotFoundException(String message) {
		super(message, 404);
	}

}
