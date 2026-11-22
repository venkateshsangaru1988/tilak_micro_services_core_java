package com.ecommerce.product.customexceptions;

public class StockServiceUnavailableException extends ProductApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public StockServiceUnavailableException(String message, int statusCode) {
		super(message, statusCode);
	}

}
