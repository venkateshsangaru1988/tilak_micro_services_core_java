package com.multiple.dbs.connection.service;

import java.util.List;

import com.multiple.dbs.connection.h2.entity.H2ProductEntity;
import com.multiple.dbs.connection.mysql.entity.MysqlProductEntity;

public interface MultipleDBService {

	List<MysqlProductEntity> findAllRecords();

	List<H2ProductEntity> saveRecordsAndFetchFromH2DB();

}
