package com.multiple.dbs.connection.mysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.multiple.dbs.connection.mysql.entity.MysqlProductEntity;

public interface MySqlProductRepository extends JpaRepository<MysqlProductEntity, Long> {

}
