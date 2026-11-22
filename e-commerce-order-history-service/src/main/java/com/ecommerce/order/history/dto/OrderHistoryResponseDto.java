package com.ecommerce.order.history.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryResponseDto {

	private Long orderHistoryId;

	private Long productId;

	private Long auditTxnId;

	private Long qty;

}
