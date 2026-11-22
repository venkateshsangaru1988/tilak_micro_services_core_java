package com.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {

	@NotNull(message = "Name is required")
	private String name;

	@NotBlank(message = "Location is required")
	private String location;

	@NotNull(message = "No of tables are required")
	private Integer noOfTables;

}
