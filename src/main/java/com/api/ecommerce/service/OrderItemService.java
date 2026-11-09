package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface OrderItemService {
	
	public ResponseEntity<?> retrieveOrderItemForOrder(String orderExternalId);

}
