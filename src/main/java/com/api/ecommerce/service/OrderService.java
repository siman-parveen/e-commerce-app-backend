package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.OrderRequestDTO;
import jakarta.validation.Valid;

public interface OrderService {
	
	public ResponseEntity<?> retrieveOrdersForUser(String userExternalId);
	public ResponseEntity<?> retrieveOrder(String orderExternalId);
	public ResponseEntity<?> createOrderForUser(String userExternalId, @Valid OrderRequestDTO orderRqst);
	public ResponseEntity<?> cancelOrder(String orderExternalId);
	public ResponseEntity<?> retrieveAllOrders();
	public ResponseEntity<?> updateOrderStatus(String orderExternalId, String status);

}
