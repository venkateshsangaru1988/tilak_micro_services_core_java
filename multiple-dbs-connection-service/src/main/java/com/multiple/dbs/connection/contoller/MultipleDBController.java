package com.multiple.dbs.connection.contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.multiple.dbs.connection.h2.entity.H2ProductEntity;
import com.multiple.dbs.connection.mysql.entity.MysqlProductEntity;
import com.multiple.dbs.connection.service.MultipleDBService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/multiple-dbs")
@RequiredArgsConstructor
public class MultipleDBController {

	private final MultipleDBService multipleDBService;

	@GetMapping("/all-mysql-products")
	public ResponseEntity<List<MysqlProductEntity>> getAllProductsFromMysql() {
		List<MysqlProductEntity> responseList = multipleDBService.findAllRecords();
		return new ResponseEntity<>(responseList, HttpStatus.OK);

	}

	@PostMapping("/save-h2-products-and-fetch-records")
	public ResponseEntity<List<H2ProductEntity>> saveRecordsToH2AndFetchRecords() {
		List<H2ProductEntity> responseList = multipleDBService.saveRecordsAndFetchFromH2DB();
		return new ResponseEntity<>(responseList, HttpStatus.CREATED);
	}

	// Just for Interview purpose
	@RequestMapping(value = "/all-products", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
	public ResponseEntity<?> allProducts(HttpServletRequest request) {

		String methodType = null;
		if ("GET".equalsIgnoreCase(request.getMethod())) {
			return new ResponseEntity<>(getAllProductsFromMysql(), HttpStatus.OK);
		} else if ("POST".equalsIgnoreCase(request.getMethod())) {
			return new ResponseEntity<>(saveRecordsToH2AndFetchRecords(), HttpStatus.CREATED);
		} else {
			methodType = request.getMethod() + " accepted";
		}
		return new ResponseEntity<>(methodType, HttpStatus.OK);

	}

}
