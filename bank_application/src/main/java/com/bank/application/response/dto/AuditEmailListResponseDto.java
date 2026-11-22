package com.bank.application.response.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditEmailListResponseDto {

	private Long id;

	private String email;

	private String subject;

	private String body;

	private String emailType;

	private LocalDateTime createdDateTime;
}
