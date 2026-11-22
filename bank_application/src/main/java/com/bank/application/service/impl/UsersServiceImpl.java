package com.bank.application.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.bank.application.constants.ApplicationConstants;
import com.bank.application.customexceptions.MigrationUsersEmptyException;
import com.bank.application.entity.master.Users;
import com.bank.application.enums.ApplicationUtilEnum;
import com.bank.application.mapper.users.UserAuthMapper;
import com.bank.application.notifications.EmailNotifications;
import com.bank.application.notifications.EmailRequestDto;
import com.bank.application.notifications.SmsNotifications;
import com.bank.application.notifications.SmsRequestDto;
import com.bank.application.repository.UsersRepository;
import com.bank.application.request.dto.UserAuthRequestDto;
import com.bank.application.request.dto.UserRequestDto;
import com.bank.application.response.dto.MigrationUsersErrorsResponseDto;
import com.bank.application.response.dto.MigrationUsersResponseDto;
import com.bank.application.response.dto.UserResponseDto;
import com.bank.application.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

	private final UsersRepository usersRepository;

	private final UserAuthMapper userAuthMapper;

	private final PasswordEncoder passwordEncoder;

	private final SmsNotifications smsNotifications;

	private final EmailNotifications emailNotifications;

	private final ObjectMapper objectMapper;

	private final JobLauncher jobLauncher;

	private final Job userMigrationJob;

	@Override
	@Transactional
	public UserResponseDto saveUser(UserAuthRequestDto requestDto) {

		Users userObj = userAuthMapper.convertDtoToEntity(requestDto);
		// Encode password before saving
		String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

		userObj.setPassword(encodedPassword);

		userObj = usersRepository.save(userObj);

		UserResponseDto response = userAuthMapper.convertEntityToDto(userObj);

		sendingNotifications(requestDto, userObj);

		return response;
	}

	private void sendingNotifications(UserAuthRequestDto requestDto, Users userObj) {
		// Sending notifications
		String subject = "Welcome User : " + requestDto.getUserName();
		String body = "Congratulations you are part of the HDFC bank with customer id : " + userObj.getUserCustomerId();
		String createdBy = userObj.getCreatedBy();
		LocalDateTime createdDateTime = LocalDateTime.now();

		// SMS
		SmsRequestDto smsRequest = new SmsRequestDto();
		smsRequest.setMobileNo(requestDto.getMobileNo());
		smsRequest.setSubject(subject);
		smsRequest.setBody(body);
		smsRequest.setCreatedBy(createdBy);
		smsRequest.setCreatedDateTime(createdDateTime);
		smsRequest.setSmsType(ApplicationConstants.SIGNUP_SCENARIO);
		smsNotifications.sendSms(smsRequest);

		// Email
		EmailRequestDto emailRequest = new EmailRequestDto();
		emailRequest.setEmail(requestDto.getEmail());
		emailRequest.setSubject(subject);
		emailRequest.setBody(body);
		emailRequest.setCreatedBy(createdBy);
		emailRequest.setCreatedDateTime(createdDateTime);
		emailRequest.setEmailType(ApplicationConstants.SIGNUP_SCENARIO);
		emailNotifications.sendEmail(emailRequest);
	}

	@Override
	@Transactional
	public UserResponseDto updateUser(UserRequestDto requestDto, Long id) {
		Users userObj = userAuthMapper.getUserById(id);

		userObj.setDateOfBirth(requestDto.getDateOfBirth());
		userObj.setGender(requestDto.getGender());
		userObj.setAddress(requestDto.getAddress());

		// This should be login user,as of now i have kept DB user only
		userObj.setLastModifiedBy(userObj.getCreatedBy());
		userObj.setLastModifiedDateTime(LocalDateTime.now());

		userObj = usersRepository.save(userObj);

		return userAuthMapper.convertEntityToDto(userObj);
	}

	@Override
	public UserResponseDto getUserDetailsById(Long id) {
		Users userObj = userAuthMapper.getUserById(id);
		return userAuthMapper.convertEntityToDto(userObj);
	}

	@Override
	public List<UserResponseDto> getAllUsers() {

		List<UserResponseDto> responseList = new ArrayList<>();
		List<Users> userList = usersRepository.findAllByOrderByIdDesc();

		if (!userList.isEmpty()) {

			userList.forEach(user -> {
				responseList.add(userAuthMapper.convertEntityToDto(user));
			});

		}

		return responseList;
	}

	@Override
	public String userInActive(Long id) {
		Users userObj = userAuthMapper.getUserById(id);
		userObj.setUserIsActive(Boolean.FALSE);
		usersRepository.save(userObj);
		return "User is in active";
	}

	@Override
	@org.springframework.transaction.annotation.Transactional(propagation = Propagation.REQUIRES_NEW)
	public MigrationUsersResponseDto saveMigrationUsers(List<UserAuthRequestDto> requestDtoList) {

		if (requestDtoList.isEmpty()) {
			throw new MigrationUsersEmptyException("Request Body should be required", 404);
		}
		int successCount = 0;
		int failedCount = 0;

		List<MigrationUsersErrorsResponseDto> errorsList = new ArrayList<>();

		for (UserAuthRequestDto requestDto : requestDtoList) {
			try {
				Users userObj = userAuthMapper.convertDtoToEntity(requestDto);
				// Encode password before saving
				String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
				userObj.setPassword(encodedPassword);
				userObj.setMigratedUser(true);
				userObj = usersRepository.save(userObj);
				sendingNotifications(requestDto, userObj);
				successCount++;
			} catch (Exception exception) {
				failedCount++;
				MigrationUsersErrorsResponseDto error = new MigrationUsersErrorsResponseDto();
				error.setMobileNo(requestDto.getMobileNo());
				error.setEmail(requestDto.getEmail());
				error.setDescription("Either Mobile no / email already exist in the system");
				errorsList.add(error);
			}
		}

		MigrationUsersResponseDto response = new MigrationUsersResponseDto();
		response.setResponse(
				"Migrated users success count is " + successCount + " and failed count is : " + failedCount);
		response.setFailedRecords(errorsList);
		return response;
	}

	@Override
	
	public MigrationUsersResponseDto saveMigrationUsersWithBatch(List<UserAuthRequestDto> requestDtoList) {

		if (requestDtoList == null || requestDtoList.isEmpty()) {
			throw new MigrationUsersEmptyException("Request body should not be empty", 400);
		}

		String batchId = UUID.randomUUID().toString();

		try {
			List<Users> stagingRecords = new ArrayList<>();

			for (UserAuthRequestDto dto : requestDtoList) {

				// Encode the password before persisting the staging payload.
				dto.setPassword(passwordEncoder.encode(dto.getPassword()));

				Users record = new Users();
				
				BeanUtils.copyProperties(dto, record);

				if (ApplicationConstants.CUSTOMER.equalsIgnoreCase(dto.getRole())) {
					record.setRole(ApplicationUtilEnum.CUSTOMER.name());
				} else if (ApplicationConstants.MANAGER.equalsIgnoreCase(dto.getRole())) {
					record.setRole(ApplicationUtilEnum.MANAGER.name());
				} else if (ApplicationConstants.ADMIN.equalsIgnoreCase(dto.getRole())) {
					record.setRole(ApplicationUtilEnum.ADMIN.name());
				}

				// User is Active
				record.setUserIsActive(Boolean.TRUE);
				// Generating Bank Account number
				record.setUserCustomerId(userAuthMapper.generateBankAccountNo());
				record.setCreatedBy(dto.getUserName());
				record.setCreatedDateTime(LocalDateTime.now());
				record.setBatchId(batchId);
				record.setPayload(objectMapper.writeValueAsString(dto));
				
				
				stagingRecords.add(record);
			}

			
			
			List<Users> usersSaveList = usersRepository.saveAllAndFlush(stagingRecords);
			
			if(!usersSaveList.isEmpty()) {
				
				usersSaveList.forEach(userObj ->{
					userObj.setUserCustomerId(userAuthMapper.generateBankAccountNo());
					usersRepository.save(userObj);
				});
			}
			
			

		} catch (Exception exception) {
			exception.printStackTrace();
			
			throw new IllegalStateException("Unable to prepare migration batch", exception);
		}

		try {
			JobParameters parameters = new JobParametersBuilder().addString("batchId", batchId)
					.addLong("requestedAt", System.currentTimeMillis()).toJobParameters();

			jobLauncher.run(userMigrationJob, parameters);

		} catch (Exception exception) {
			throw new IllegalStateException("Unable to launch migration batch " + batchId, exception);
		}

		// The default JobLauncher is normally synchronous unless configured
		// with an asynchronous TaskExecutor.
		List<Users> records = usersRepository.findByBatchId(batchId);

		long successCount = 0;
		long failedCount = 0;
		
		if(!records.isEmpty()) {
			successCount=records.size();
		}
				

		failedCount=requestDtoList.size()-successCount;
		
		MigrationUsersResponseDto response = new MigrationUsersResponseDto();
		response.setResponse("Migration batch " + batchId + ": success count = " + successCount + ", failed count = "
				+ failedCount);
		List<MigrationUsersErrorsResponseDto> errors = new ArrayList<>();
		

		response.setFailedRecords(errors);
		return response;
	}

}
