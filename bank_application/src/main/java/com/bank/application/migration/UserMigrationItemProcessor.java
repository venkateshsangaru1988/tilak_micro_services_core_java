package com.bank.application.migration;

import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.bank.application.entity.master.Users;
import com.bank.application.repository.UsersRepository;
import com.bank.application.request.dto.UserAuthRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMigrationItemProcessor implements ItemProcessor<Users, Users> {

	private final ObjectMapper objectMapper;
	private final UsersRepository usersRepository;

	@Override
	public Users process(Users record) throws Exception {

		UserAuthRequestDto dto = objectMapper.readValue(record.getPayload(), UserAuthRequestDto.class);

		if (usersRepository.existsByEmail(dto.getEmail())) {

			return record;
		}

		if (usersRepository.existsByMobileNo(dto.getMobileNo())) {

			return record;
		}

		return record;
	}
}