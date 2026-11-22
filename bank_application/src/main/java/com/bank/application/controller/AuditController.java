package com.bank.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.request.dto.AuditDetailsRequestDto;
import com.bank.application.request.dto.AuditSmsListRequestDto;
import com.bank.application.response.dto.AuditInfoResponseDto;
import com.bank.application.response.dto.AuditSmsListResponseDto;
import com.bank.application.service.AccountInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/audit")
public class AuditController {

	private final AccountInfoService accountInfoService;

	AuditController(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	@Operation(summary = "Get Account details", description = "Get Account details by account id")
	@ApiResponse(responseCode = "200", description = "Account fetched successfully")
	@GetMapping("/get-mini-statement-details-by-account-id")
	public ResponseEntity<List<AuditInfoResponseDto>> getMiniStatementByAccountId(
			@Valid @RequestBody AuditDetailsRequestDto requestObj) {
		List<AuditInfoResponseDto> responseList = accountInfoService.getMiniStatementByAccountId(requestObj);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@Operation(summary = "Get SMS details", description = "Get SMS details by account id")
	@ApiResponse(responseCode = "200", description = "SMS fetched successfully")
	@GetMapping("/get-sms-statement-details-by-mobile-no")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<List<AuditSmsListResponseDto>> getSMSStatementByAccountId(
			@Valid @RequestBody AuditSmsListRequestDto requestObj) {
		List<AuditSmsListResponseDto> responseList = accountInfoService.auditSmsList(requestObj);
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

}
