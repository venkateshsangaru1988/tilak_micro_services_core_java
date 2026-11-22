package com.bank.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.application.mapper.accountinfo.AccountInfoMapper;
import com.bank.application.request.dto.AccountInfoRequestDto;
import com.bank.application.response.dto.AccountInfoResponseDto;
import com.bank.application.service.impl.AccountInfoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AccountInfoServiceImplTest {

	// Which class we need to perform test
	@InjectMocks
	private AccountInfoServiceImpl accountInfoServiceImplMock;

	@Mock
	private AccountInfoMapper accountInfoMapperMock;

	@Test
	void saveAccountInfoTest() {

		AccountInfoRequestDto requestObj = new AccountInfoRequestDto();
		requestObj.setAccountType("SAVINGS");
		requestObj.setBalance(0d);

		AccountInfoResponseDto response = new AccountInfoResponseDto();
		response.setAccountNo("SAVINGS");
		when(accountInfoServiceImplMock.saveAccountInfo(requestObj)).thenReturn(response);

		// Act: Call the actual service method
		AccountInfoResponseDto actualResponse = accountInfoServiceImplMock.saveAccountInfo(requestObj);

		assertEquals(actualResponse.getAccountNo(), requestObj.getAccountType());
	}

}
