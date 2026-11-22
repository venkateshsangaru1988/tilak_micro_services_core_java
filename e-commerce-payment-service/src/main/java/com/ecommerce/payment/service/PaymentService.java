package com.ecommerce.payment.service;

public interface PaymentService {

	Double customerBalanceByEmailId(String emailId);

	Long balanceUpdate(String emailId, Double customerSelectedQtyPrice, String operationType);
}
