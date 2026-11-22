package com.multiple.dbs.connection.h2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiple.dbs.connection.h2.entity.H2ProductEntity;

public interface H2ProductRepository extends JpaRepository<H2ProductEntity, Long> {

}
