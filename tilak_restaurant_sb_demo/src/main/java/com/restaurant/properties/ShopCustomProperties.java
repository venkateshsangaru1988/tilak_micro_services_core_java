package com.restaurant.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ConfigurationProperties(prefix = "shop")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCustomProperties {

	private String location;
	private String owner;
	private String type;
}
