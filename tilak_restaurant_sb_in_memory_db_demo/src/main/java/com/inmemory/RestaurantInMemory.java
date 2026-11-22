package com.inmemory;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_in_memory")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInMemory {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "restaurant_id", nullable = false)
	private Long restaurantId;

	@Column(name = "restaurant_name")
	private String name;

	@Column(name = "location")
	private String location;

	@Column(name = "no_of_tables")
	private Integer noOfTables;

	@Column(name = "created_by",updatable = false)
	private String createdBy;

	@Column(name = "created_date_time",updatable = false)
	private LocalDateTime createdDateTime;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date_time")
	private LocalDateTime updatedDateTime;


}
