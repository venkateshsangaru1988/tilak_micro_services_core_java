package com.restaurant.exceptionshandler;

public class RestaurantBaseApplicationUtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public RestaurantBaseApplicationUtilException(String message, int statusCode) {

		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
