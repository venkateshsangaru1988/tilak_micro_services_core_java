package com.multiple.dbs.connection.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multiple.dbs.connection.h2.entity.H2ProductEntity;
import com.multiple.dbs.connection.h2.repository.H2ProductRepository;
import com.multiple.dbs.connection.mysql.entity.MysqlProductEntity;
import com.multiple.dbs.connection.mysql.repository.MySqlProductRepository;
import com.multiple.dbs.connection.service.MultipleDBService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MultipleDBServiceImpl implements MultipleDBService {

	private final MySqlProductRepository mySqlProductRepository;

	private final H2ProductRepository h2ProductRepository;

	@Override
	public List<MysqlProductEntity> findAllRecords() {
		List<MysqlProductEntity> productList = mySqlProductRepository.findAll();
		return productList;
	}

	@Override
	public List<H2ProductEntity> saveRecordsAndFetchFromH2DB() {

		List<MysqlProductEntity> mysqlProductList = findAllRecords();
		if (!mysqlProductList.isEmpty()) {

			mysqlProductList.forEach(mysql -> {

				H2ProductEntity h2Product = new H2ProductEntity();
				h2Product.setProductCode(mysql.getProductCode());
				h2Product.setProductName(mysql.getProductName());
				h2Product.setProductPrice(mysql.getProductPrice());
				h2ProductRepository.save(h2Product);
			});

		}
		return h2ProductRepository.findAll();
	}

}
