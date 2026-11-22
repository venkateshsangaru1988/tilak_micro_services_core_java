package com.ecommerce.order.history.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_history_id", nullable = false, unique = true)
	private Long orderHistoryId;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "customer_email_id")
	private String customerEmailId;

	@Column(name = "order_date_time")
	private LocalDateTime orderDateTime;

	@Column(name = "product_id")
	private Long productId;

	@Column(name = "audit_txn_id")
	private Long auditTxnId;

	@Column(name = "order_expected_date")
	private LocalDate orderExpectedDate;

	@Column(name = "order_status")
	private String orderStatus;

	@Column(name = "qty")
	private Long qty;

}
