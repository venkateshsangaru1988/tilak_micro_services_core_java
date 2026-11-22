package com.ecommerce.product.customexceptions;

public class OrderNotFoundException extends ProductApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String message, int statusCode) {
		super(message, statusCode);
	}

}
