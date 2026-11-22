package com.bank.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.config.AuthService;
import com.bank.application.request.dto.LoginRequestDto;
import com.bank.application.request.dto.UserAuthRequestDto;
import com.bank.application.response.dto.LoginResponseDto;
import com.bank.application.response.dto.MigrationUsersResponseDto;
import com.bank.application.response.dto.UserResponseDto;
import com.bank.application.service.UsersService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "User Auth APIs", description = "Operations related to Signup and login")
public class UsersAuthController {

	private final UsersService usersService;
	private final AuthService authService;

	UsersAuthController(UsersService usersService, AuthService authService) {
		this.usersService = usersService;
		this.authService = authService;
	}

	@Operation(summary = "Signup/Register", description = "Creates a new user in the system")

	@ApiResponse(responseCode = "201", description = "User created successfully")

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> saveUser(@Valid @RequestBody UserAuthRequestDto requestObj) {
		UserResponseDto responseObj = usersService.saveUser(requestObj);
		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
		LoginResponseDto response = authService.login(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/migration-users")
	public ResponseEntity<MigrationUsersResponseDto> saveMigrationUsers(
			@Valid @RequestBody List<UserAuthRequestDto> requestObjList) {
		MigrationUsersResponseDto responseObj = usersService.saveMigrationUsers(requestObjList);
		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

	@PostMapping("/migration-batch-users")
	public ResponseEntity<MigrationUsersResponseDto> saveMigrationUsersWithBatch(
			@Valid @RequestBody List<UserAuthRequestDto> requestObjList) {
		MigrationUsersResponseDto responseObj = usersService.saveMigrationUsersWithBatch(requestObjList);
		return new ResponseEntity<>(responseObj, HttpStatus.CREATED);

	}

}
