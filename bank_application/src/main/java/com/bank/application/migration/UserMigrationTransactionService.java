package com.bank.application.migration;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.entity.master.Users;
import com.bank.application.mapper.users.UserAuthMapper;
import com.bank.application.repository.UsersRepository;
import com.bank.application.request.dto.UserAuthRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMigrationTransactionService {

	private final UsersRepository usersRepository;
	private final UserAuthMapper userAuthMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Users saveSingleUser(UserAuthRequestDto requestDto) {
		Users user = userAuthMapper.convertDtoToEntity(requestDto);
		user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
		user.setMigratedUser(true);
		// Flush inside this transaction to detect many DB errors now.
		return usersRepository.saveAndFlush(user);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Users saveSingleUserWithEncodedPassword(UserAuthRequestDto requestDto) {

		Users user = userAuthMapper.convertDtoToEntity(requestDto);

		// The staging payload contains an already-encoded password.
		user.setPassword(requestDto.getPassword());
		user.setMigratedUser(true);

		return usersRepository.saveAndFlush(user);
	}
}
