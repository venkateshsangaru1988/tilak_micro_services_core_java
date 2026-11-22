package com.ecommerce.product.customexceptions;

public class InsufficientStockException extends ProductApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public InsufficientStockException(String message, int statusCode) {
		super(message, statusCode);
	}

}
