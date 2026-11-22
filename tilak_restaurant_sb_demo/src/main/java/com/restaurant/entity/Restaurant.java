package com.restaurant.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

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

	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@Column(name = "created_date_time", updatable = false)
	private LocalDateTime createdDateTime;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date_time")
	private LocalDateTime updatedDateTime;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FoodMenu> foodMenus = new ArrayList<>();

}
