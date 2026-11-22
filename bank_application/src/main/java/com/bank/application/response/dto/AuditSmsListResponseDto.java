package com.bank.application.response.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditSmsListResponseDto {

	private Long id;

	private String mobileNo;

	private String subject;

	private String body;

	private String smsType;

	private LocalDateTime createdDateTime;
}
