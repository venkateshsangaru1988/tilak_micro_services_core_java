package com.bank.application.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.application.entity.notifications.SmsEntity;

public interface SmsRepository extends JpaRepository<SmsEntity, Long> {

	List<SmsEntity> findByMobileNoAndSmsTypeOrderByIdDesc(String mobileNo, String smsType);

	List<SmsEntity> findByMobileNoAndSmsTypeAndCreatedDateTimeBetweenOrderByIdDesc(String mobileNo, String smsType,
			LocalDateTime fromCreatedDateTime, LocalDateTime toCreatedDateTime);
}
