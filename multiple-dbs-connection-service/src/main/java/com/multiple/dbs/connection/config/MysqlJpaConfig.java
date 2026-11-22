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
@EnableJpaRepositories(basePackages = "com.multiple.dbs.connection.mysql.repository", entityManagerFactoryRef = "mysqlEntityManagerFactory", transactionManagerRef = "mysqlTransactionManager")
public class MysqlJpaConfig {

	@Bean
	@ConfigurationProperties("spring.datasource.mysql")
	DataSourceProperties mysqlDataSourceProperties() {

		return new DataSourceProperties();
	}

	@Bean
	DataSource mysqlDataSource() {

		return mysqlDataSourceProperties().initializeDataSourceBuilder().build();
	}

	@Bean
	LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
			@Qualifier("mysqlDataSource") DataSource dataSource) {

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();

		emf.setDataSource(dataSource);

		emf.setPackagesToScan("com.multiple.dbs.connection.mysql.entity");

		emf.setPersistenceUnitName("mysql");

		emf.setPersistenceProviderClass(HibernatePersistenceProvider.class);

		Map<String, Object> properties = new HashMap<>();

		// Do not modify/create your production MySQL schema
		properties.put("hibernate.hbm2ddl.auto", "none");

		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

		properties.put("hibernate.show_sql", true);

		properties.put("hibernate.format_sql", true);

		emf.setJpaPropertyMap(properties);

		return emf;
	}

	@Bean
	public PlatformTransactionManager mysqlTransactionManager(
			@Qualifier("mysqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {

		return new JpaTransactionManager(entityManagerFactory);
	}
}