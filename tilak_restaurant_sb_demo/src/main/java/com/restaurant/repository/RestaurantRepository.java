package com.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.entity.Restaurant;

import jakarta.transaction.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	List<Restaurant> findAllByOrderByRestaurantIdDesc();

	@Transactional
	@Modifying
	@Query("update Restaurant res set res.location=:location where res.restaurantId=:restaurantId")
	int updateSingleValue(Long restaurantId, String location);

	@Transactional
	@Modifying
	@Query(value = "update restaurant res set res.no_of_tables=:noOfTables where res.restaurant_id=:restaurantId", nativeQuery = true)
	int updateSingleValueWithNative(Long restaurantId, Integer noOfTables);

	@Transactional
	@Modifying
	@Query(value = "update restaurant  set no_of_tables=:noOfTables,restaurant_name=:name where restaurant_id=:restaurantId", nativeQuery = true)
	int updateMultiValuesWithNative(Long restaurantId, Integer noOfTables, String name);

	@Transactional
	@Modifying
	@Query("delete from Restaurant res  where res.restaurantId=:restaurantId")
	int deleteWithJpaQuery(Long restaurantId);

	@Transactional
	@Modifying
	@Query(value = "delete  from restaurant where restaurant_id=:restaurantId", nativeQuery = true)
	int deleteWithNativeQuery(@Param("restaurantId") Long restaurantId);

	List<Restaurant> findAllByLocationOrderByRestaurantIdDesc(String location);

	List<Restaurant> findAllByLocationContainingIgnoreCaseOrderByRestaurantIdDesc(String location);

	List<Restaurant> findAllByLocationContainingIgnoreCaseAndNameContainingIgnoreCaseOrderByRestaurantIdDesc(
			String location, String name);

}
