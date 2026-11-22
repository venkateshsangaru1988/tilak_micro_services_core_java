package com.bank.application.migration;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bank.application.entity.master.Users;
import com.bank.application.repository.UsersRepository;
import com.bank.application.request.dto.UserAuthRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserMigrationItemWriter implements ItemWriter<Users> {

	private final ObjectMapper objectMapper;
	private final UserMigrationTransactionService userTransactionService;
	private final UsersRepository stagingRepository;

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void write(Chunk<? extends Users> chunk) throws Exception {

		for (Users record : chunk.getItems()) {

			try {
				UserAuthRequestDto dto = objectMapper.readValue(record.getPayload(), UserAuthRequestDto.class);

				userTransactionService.saveSingleUserWithEncodedPassword(dto);
				stagingRepository.saveAndFlush(record);

			} catch (Exception exception) {

				stagingRepository.saveAndFlush(record);
			}
		}
	}

}