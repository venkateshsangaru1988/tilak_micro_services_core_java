package com.ecommerce.product.customexceptions;

public class InsufficientBalanceException extends ProductApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public InsufficientBalanceException(String message, int statusCode) {
		super(message, statusCode);
	}

}
