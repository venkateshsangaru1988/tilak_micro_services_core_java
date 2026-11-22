package com.bank.application.request.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditSmsListRequestDto {

	@NotNull(message = "Mobile no is required")
	private String mobileNo;

	@NotNull(message = "SMS Type is required")
	private String smsType;

	private LocalDate fromDate;

	private LocalDate toDate;

}
