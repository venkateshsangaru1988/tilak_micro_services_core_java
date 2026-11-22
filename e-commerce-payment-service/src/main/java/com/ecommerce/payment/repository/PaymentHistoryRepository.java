package com.ecommerce.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.payment.entity.PaymentHistoryEntity;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {

}
