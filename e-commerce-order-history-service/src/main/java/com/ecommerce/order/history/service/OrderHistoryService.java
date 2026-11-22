package com.ecommerce.order.history.service;

import java.util.List;

import com.ecommerce.order.history.dto.OrderHistoryRequestDto;
import com.ecommerce.order.history.dto.OrderHistoryResponseDto;

public interface OrderHistoryService {

	String saveOrderHistory(List<OrderHistoryRequestDto> requestList);

	String orderStatusUpdate(String orderId, String orderStatus);

	List<OrderHistoryResponseDto> getHistoryByEmailAndOrderId(String customerEmailId, String orderId);

	String updateOrderHistoryByOrderIdAndProductId(String orderId, String orderStatus, Long productId);

	List<OrderHistoryResponseDto> getHistoryByEmailAndOrderIdAndProductId(String customerEmailId, String orderId,
			Long productId);

	List<OrderHistoryResponseDto> getHistoryRecordsByOrderStatus(String orderStatus);

	String deleteOrderHistoryByOrderId(String orderId);

}
