package com.restaurant.exceptionshandler;

public class ItemNotAvailableException extends RestaurantBaseApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public ItemNotAvailableException(String message) {
		super(message, 404);
	}

}
