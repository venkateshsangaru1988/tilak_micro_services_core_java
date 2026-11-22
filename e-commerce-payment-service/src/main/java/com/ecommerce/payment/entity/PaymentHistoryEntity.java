package com.ecommerce.payment.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_history_id", nullable = false, unique = true)
	private Long paymentHistoryId;

	@Column(name = "purchased_amount")
	private Double purchasedAmount;

	@Column(name = "txn_date_time")
	private LocalDateTime txnDateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payment_id", nullable = false)
	private PaymentEntity payment;

	@Column(name = "operation_type")
	private String operationType;

}
