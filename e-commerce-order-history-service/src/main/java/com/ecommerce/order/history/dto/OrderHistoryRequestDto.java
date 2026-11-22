package com.ecommerce.order.history.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryRequestDto {

	private String orderId;

	private String customerEmailId;

	private LocalDateTime orderDateTime;

	private Long productId;

	private Long auditTxnId;

	private LocalDate orderExpectedDate;

	private Long qty;
}
