package com.restaurant.dto.country;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryResponseDto {

	private Long countryId;

	private String countryCode;

	private String countryName;

}
