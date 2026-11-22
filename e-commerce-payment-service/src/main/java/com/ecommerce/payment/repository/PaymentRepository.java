package com.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.payment.entity.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

	PaymentEntity findByEmailId(String emailId);

}
