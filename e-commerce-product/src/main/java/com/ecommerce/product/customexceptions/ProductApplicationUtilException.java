package com.ecommerce.product.customexceptions;

public class ProductApplicationUtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final int statusCode;

	public ProductApplicationUtilException(String message, int statusCode) {

		super(message);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
