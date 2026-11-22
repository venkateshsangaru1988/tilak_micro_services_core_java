package com.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Restaurant Management API", version = "1.0", description = "REST APIs for managing restaurants"))

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:restaurant.properties"), @PropertySource("classpath:shop.properties") })
@ConfigurationPropertiesScan
@EnableCaching
@EnableScheduling
public class TilakRestaurantSbDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TilakRestaurantSbDemoApplication.class, args);
	}

}
