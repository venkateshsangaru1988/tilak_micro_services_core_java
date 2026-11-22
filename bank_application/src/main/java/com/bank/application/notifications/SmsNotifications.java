package com.bank.application.notifications;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.bank.application.constants.ApplicationConstants;
import com.bank.application.entity.notifications.SmsEntity;
import com.bank.application.repository.SmsRepository;
import com.bank.application.request.dto.AuditSmsListRequestDto;
import com.bank.application.response.dto.AuditSmsListResponseDto;

@Component
public class SmsNotifications {

	private final SmsRepository smsRepository;

	SmsNotifications(SmsRepository smsRepository) {
		this.smsRepository = smsRepository;
	}

	public String sendSms(SmsRequestDto requestObj) {
		String smsContent = "Subject :" + requestObj.getSubject() + " Body : " + requestObj.getBody()
				+ " and mobile no : " + requestObj.getMobileNo();
		// External API If Status is SUCCESS / FAIL
		requestObj.setSmsStatus(ApplicationConstants.SUCCESS);
		saveSms(requestObj);
		return smsContent;
	}

	public void saveSms(SmsRequestDto requestObj) {

		SmsEntity smsObj = new SmsEntity();
		BeanUtils.copyProperties(requestObj, smsObj);
		smsRepository.save(smsObj);

	}

	public List<AuditSmsListResponseDto> auditSmsList(AuditSmsListRequestDto requestObj) {
		List<AuditSmsListResponseDto> responseList = new ArrayList<>();
		List<SmsEntity> smsList = null;
		if (null != requestObj.getFromDate() && null != requestObj.getToDate()) {
			LocalDateTime fromDateTime = requestObj.getFromDate().atStartOfDay();
			LocalDateTime toDateTime = requestObj.getToDate().atTime(LocalTime.MAX);
			smsList = smsRepository.findByMobileNoAndSmsTypeAndCreatedDateTimeBetweenOrderByIdDesc(
					requestObj.getMobileNo(), requestObj.getSmsType(), fromDateTime, toDateTime);
		} else {

			smsList = smsRepository.findByMobileNoAndSmsTypeOrderByIdDesc(requestObj.getMobileNo(),
					requestObj.getSmsType());
		}
		if (!smsList.isEmpty()) {
			smsList.forEach(sms -> {
				AuditSmsListResponseDto dto = new AuditSmsListResponseDto();
				BeanUtils.copyProperties(sms, dto);
				responseList.add(dto);
			});

		}

		return responseList;
	}

}
