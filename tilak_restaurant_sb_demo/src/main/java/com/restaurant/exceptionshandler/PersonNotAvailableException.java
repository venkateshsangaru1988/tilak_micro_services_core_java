package com.restaurant.exceptionshandler;

public class PersonNotAvailableException extends RestaurantBaseApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public PersonNotAvailableException(String message) {
		super(message, 404);
	}

}
