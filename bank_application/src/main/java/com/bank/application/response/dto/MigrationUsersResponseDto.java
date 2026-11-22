package com.bank.application.response.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MigrationUsersResponseDto {

	private String response;
	private List<MigrationUsersErrorsResponseDto> failedRecords;

}
