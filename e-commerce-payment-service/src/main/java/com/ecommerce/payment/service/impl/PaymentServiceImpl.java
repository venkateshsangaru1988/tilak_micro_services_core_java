package com.ecommerce.payment.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.ecommerce.payment.constants.UtilConstants;
import com.ecommerce.payment.entity.PaymentEntity;
import com.ecommerce.payment.entity.PaymentHistoryEntity;
import com.ecommerce.payment.repository.PaymentHistoryRepository;
import com.ecommerce.payment.repository.PaymentRepository;
import com.ecommerce.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final PaymentRepository paymentRepository;

	private final PaymentHistoryRepository paymentHistoryRepository;

	@Override
	public Double customerBalanceByEmailId(String emailId) {
		PaymentEntity paymentObj = paymentRepository.findByEmailId(emailId);
		if (null != paymentObj) {
			return paymentObj.getBalance();
		}
		return 0d;
	}

	@Override
	public Long balanceUpdate(String emailId, Double customerSelectedQtyPrice, String operationType) {
		PaymentEntity paymentObj = paymentRepository.findByEmailId(emailId);
		PaymentHistoryEntity history = new PaymentHistoryEntity();
		if (null != paymentObj) {
			if (UtilConstants.ORDER_PLACED_PAID.equalsIgnoreCase(operationType)) {
				paymentObj.setBalance(paymentObj.getBalance() - customerSelectedQtyPrice);
			} else if (UtilConstants.ORDER_REFUND.equalsIgnoreCase(operationType)) {
				paymentObj.setBalance(paymentObj.getBalance() + customerSelectedQtyPrice);
			}
			paymentRepository.save(paymentObj);
			// History logic
			history.setPayment(paymentObj);
			history.setPurchasedAmount(customerSelectedQtyPrice);
			history.setTxnDateTime(LocalDateTime.now());
			history.setOperationType(operationType);
			history = paymentHistoryRepository.save(history);

		}
		if (null != history) {
			return history.getPaymentHistoryId();
		}
		return 0l;

	}

}
