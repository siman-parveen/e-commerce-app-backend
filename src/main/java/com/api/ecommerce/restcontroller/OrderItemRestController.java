package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.service.OrderItemService;

@RestController
public class OrderItemRestController {
	
	@Autowired
	private OrderItemService service;
	
	@GetMapping("/api/order/{orderId}/items")
	public ResponseEntity<?> retrieveOrderItemForOrder(@PathVariable Long orderId) {
		return service.retrieveOrderItemForOrder(orderId);
	}

}
