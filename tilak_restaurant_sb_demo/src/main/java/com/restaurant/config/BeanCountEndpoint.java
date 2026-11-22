package com.restaurant.config;

import java.util.Map;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "bean-count")
public class BeanCountEndpoint {

	private final ApplicationContext applicationContext;

	public BeanCountEndpoint(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@ReadOperation
	public Map<String, Object> getBeanCount() {

		String[] beanNames = applicationContext.getBeanDefinitionNames();

		return Map.of("beanCount", beanNames.length);
	}
}
