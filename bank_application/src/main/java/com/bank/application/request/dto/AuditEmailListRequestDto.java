package com.bank.application.request.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditEmailListRequestDto {

	@NotNull(message = "Email is required")
	private String email;

	@NotNull(message = "SMS Type is required")
	private String emailType;

	private LocalDateTime fromCreatedDateTime;

	private LocalDateTime toCreatedDateTime;

}
