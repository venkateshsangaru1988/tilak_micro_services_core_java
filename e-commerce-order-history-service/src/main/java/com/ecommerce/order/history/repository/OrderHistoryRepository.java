package com.ecommerce.order.history.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.order.history.entity.OrderHistory;

import jakarta.transaction.Transactional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

	@Transactional
	@Modifying
	@Query("update OrderHistory ord set ord.orderStatus=:orderStatus where ord.orderId=:orderId")
	int updateOrderHistoryByOrderId(String orderStatus, String orderId);

	@Transactional
	@Modifying
	@Query("update OrderHistory ord set ord.orderStatus=:orderStatus where ord.orderId=:orderId and ord.productId=:productId")
	int updateOrderHistoryByOrderIdAndProductId(String orderStatus, String orderId, Long productId);

	List<OrderHistory> findByCustomerEmailIdAndOrderId(String customerEmailId, String orderId);

	@Transactional
	@Modifying
	@Query("update OrderHistory ord set ord.orderStatus=:orderStatus where ord.orderId=:orderId and ord.orderHistoryId=:orderHistoryId")
	int updateOrderHistoryByOrderIAndHistoryId(String orderStatus, String orderId, Long orderHistoryId);

	List<OrderHistory> findByCustomerEmailIdAndOrderIdAndProductId(String customerEmailId, String orderId,
			Long productId);

	List<OrderHistory> findByOrderStatus(String orderStatus);

	@Transactional
	@Modifying
	@Query("delete from OrderHistory ord  where ord.orderId=:orderId")
	int deleteOrderHistoryByOrderId(String orderId);

}
