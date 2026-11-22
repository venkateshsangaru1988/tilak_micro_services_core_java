package com.restaurant.exceptionshandler;

public class RestaurantCustomException extends RestaurantBaseApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public RestaurantCustomException(String message) {
		super(message, 404);
	}

}
