package com.restaurant.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "restaurant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCustomProperties {
	private String location;
	private String owner;
}
