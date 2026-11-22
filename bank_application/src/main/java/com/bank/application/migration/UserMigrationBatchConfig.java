package com.bank.application.migration;

import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.data.RepositoryItemReader;
import org.springframework.batch.infrastructure.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.bank.application.entity.master.Users;
import com.bank.application.repository.UsersRepository;

@Configuration
public class UserMigrationBatchConfig {

	@Bean
	@StepScope
	RepositoryItemReader<Users> migrationReader(UsersRepository repository,
			@Value("#{jobParameters['batchId']}") String batchId) {

		return new RepositoryItemReaderBuilder<Users>().name("migrationReader").repository(repository)
				.methodName("findByBatchIdOrderByIdAsc").arguments(java.util.List.of(batchId)).pageSize(100)
				.sorts(Map.of("id", Sort.Direction.ASC)).build();
	}

	@Bean
	Step migrationStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			RepositoryItemReader<Users> migrationReader, UserMigrationItemProcessor processor,
			UserMigrationItemWriter writer) {

		return new StepBuilder("migrationStep", jobRepository).<Users, Users>chunk(5, transactionManager)
				.reader(migrationReader).processor(processor).writer(writer).build();
	}

	@Bean
	Job userMigrationJob(JobRepository jobRepository, Step migrationStep) {

		return new JobBuilder("userMigrationJob", jobRepository).start(migrationStep).build();
	}
}