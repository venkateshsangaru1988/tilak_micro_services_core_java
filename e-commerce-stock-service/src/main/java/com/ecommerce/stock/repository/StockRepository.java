package com.ecommerce.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.stock.entity.StockEntity;

public interface StockRepository extends JpaRepository<StockEntity, Long> {

	@Query("SELECT COALESCE(SUM(s.stockAvaliable), 0) FROM StockEntity s WHERE s.productId = :productId")
	Long findAvailableStockByProductId(@Param("productId") Long productId);

	StockEntity findByProductId(Long productId);

}
