package com.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {

}
