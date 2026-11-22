package com.multiple.dbs.connection.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.multiple.dbs.connection.h2.repository", entityManagerFactoryRef = "h2EntityManagerFactory", transactionManagerRef = "h2TransactionManager")
public class H2JpaConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.h2")
	DataSourceProperties h2DataSourceProperties() {

		return new DataSourceProperties();
	}

	@Bean
	DataSource h2DataSource() {

		return h2DataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean h2EntityManagerFactory(@Qualifier("h2DataSource") DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

		emf.setDataSource(dataSource);

		emf.setPackagesToScan("com.multiple.dbs.connection.h2.entity");

		emf.setPersistenceUnitName("h2");

		emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		Map<String, Object> properties = new HashMap<>();

		properties.put("hibernate.hbm2ddl.auto", "create");

		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

		properties.put("hibernate.show_sql", true);

		properties.put("hibernate.format_sql", true);

		emf.setJpaPropertyMap(properties);

		return emf;
	}

	@Bean
	PlatformTransactionManager h2TransactionManager(
			@Qualifier("h2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {

		return new JpaTransactionManager(entityManagerFactory);
	}
}