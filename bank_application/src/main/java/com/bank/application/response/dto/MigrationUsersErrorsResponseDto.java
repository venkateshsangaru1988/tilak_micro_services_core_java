package com.bank.application.response.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MigrationUsersErrorsResponseDto {

	private String description;
	private String email;
	private String mobileNo;

}
