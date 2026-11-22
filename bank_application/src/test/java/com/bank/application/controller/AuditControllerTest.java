package com.bank.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bank.application.request.dto.AuditDetailsRequestDto;
import com.bank.application.request.dto.AuditSmsListRequestDto;
import com.bank.application.response.dto.AuditInfoResponseDto;
import com.bank.application.response.dto.AuditSmsListResponseDto;
import com.bank.application.service.AccountInfoService;

@ExtendWith(MockitoExtension.class)
public class AuditControllerTest {

	// Which class we need to perform test
	@InjectMocks
	private AuditController auditControllerMock;

	@Mock
	private AccountInfoService accountInfoServiceMock;

	@Test
	// @Disabled("not required")
	@DisplayName("Should return HTTP 200 when mini statement is fetched by account number")
	void getMiniStatementByAccountIdTest() {
		AuditDetailsRequestDto requestObj = new AuditDetailsRequestDto();
		requestObj.setAccountNo("AC0001");
		List<AuditInfoResponseDto> responseList = new ArrayList<>();
		AuditInfoResponseDto res = new AuditInfoResponseDto();
		res.setAccountNo("AC0001");
		res.setBalance(4000d);
		responseList.add(res);
		when(accountInfoServiceMock.getMiniStatementByAccountId(requestObj)).thenReturn(responseList);
		ResponseEntity<List<AuditInfoResponseDto>> response = auditControllerMock
				.getMiniStatementByAccountId(requestObj);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Should return HTTP 200 when SMS list is fetched by mobile number")
	void getSMSStatementByAccountIdTest() {

		// Arrange: Prepare request
		AuditSmsListRequestDto requestObj = new AuditSmsListRequestDto();

		// Arrange: Prepare expected response
		List<AuditSmsListResponseDto> responseList = new ArrayList<>();

		AuditSmsListResponseDto res = new AuditSmsListResponseDto();

		// Set response fields here according to your DTO
		// Example: res.setMobileNo("9876543210");

		responseList.add(res);

		// Mock service response
		when(accountInfoServiceMock.auditSmsList(requestObj)).thenReturn(responseList);

		// Act: Call controller method
		ResponseEntity<List<AuditSmsListResponseDto>> response = auditControllerMock
				.getSMSStatementByAccountId(requestObj);

		// Assert: Verify HTTP status
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

}
