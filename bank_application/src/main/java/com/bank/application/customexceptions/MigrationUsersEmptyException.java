package com.bank.application.customexceptions;

public class MigrationUsersEmptyException extends BankApplicationUtilException {

	private static final long serialVersionUID = 1L;

	public MigrationUsersEmptyException(String message, int statusCode) {
		super(message, statusCode);
	}

}
