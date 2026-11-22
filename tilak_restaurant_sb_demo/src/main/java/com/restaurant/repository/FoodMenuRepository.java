package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.FoodMenu;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long> {

	List<FoodMenu> findAllByRestaurantRestaurantId(Long restaurantId);

	List<FoodMenu> findAllByItemNameContainingIgnoreCaseOrderByFoodMenuIdDesc(String itemName);

}
