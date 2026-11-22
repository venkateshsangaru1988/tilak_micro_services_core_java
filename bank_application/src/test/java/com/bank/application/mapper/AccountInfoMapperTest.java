package com.bank.application.mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.application.entity.master.AccountTypeEntity;
import com.bank.application.entity.transactions.AccountInfoEntity;
import com.bank.application.mapper.accountinfo.AccountFailTransactionMapper;
import com.bank.application.mapper.accountinfo.AccountInfoMapper;
import com.bank.application.mapper.common.CommonMapper;
import com.bank.application.notifications.EmailNotifications;
import com.bank.application.notifications.SmsNotifications;
import com.bank.application.repository.AccountInfoAuditRepository;
import com.bank.application.repository.AccountInfoRepository;
import com.bank.application.repository.AccountTypeRepository;
import com.bank.application.request.dto.AccountInfoRequestDto;
import com.bank.application.response.dto.AccountInfoResponseDto;

@ExtendWith(MockitoExtension.class)
public class AccountInfoMapperTest {

	@InjectMocks
	private AccountInfoMapper accountInfoMapperMock;

	@Mock
	private AccountInfoRepository accountInfoRepositoryMock;
	@Mock
	private AccountTypeRepository accountTypeRepositoryMock;
	@Mock
	private AccountInfoAuditRepository accountInfoAuditRepositoryMock;
	@Mock
	private AccountFailTransactionMapper accountFailTransactionMapperMock;
	@Mock
	private CommonMapper commonMapperMock;
	@Mock
	private SmsNotifications smsNotificationsMock;
	@Mock
	private EmailNotifications emailNotificationsMock;

	@Test
	void saveAccountInfoTest() {

		// Arrange: Request
		AccountInfoRequestDto requestObj = new AccountInfoRequestDto();
		requestObj.setAccountType("SAVINGS");
		requestObj.setBalance(100d);

		// Arrange: Account type
		AccountTypeEntity accountType = new AccountTypeEntity();
		accountType.setId(1L);

		when(accountTypeRepositoryMock.findByAccountType("SAVINGS")).thenReturn(accountType);

		// Arrange: Mock repository save
		when(accountInfoRepositoryMock.save(any(AccountInfoEntity.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		// Act: Execute actual mapper method
		AccountInfoResponseDto response = accountInfoMapperMock.saveAccountInfo(requestObj);

		// Assert
		assertNotNull(response);

	}

}
