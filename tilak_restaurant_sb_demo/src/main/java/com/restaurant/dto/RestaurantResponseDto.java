package com.restaurant.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDto {

	private Long restaurantId;

	private String name;

	private String location;

	private Integer noOfTables;

	private String createdBy;

	private LocalDateTime createdDateTime;

	private String updatedBy;

	private LocalDateTime updatedDateTime;

}
