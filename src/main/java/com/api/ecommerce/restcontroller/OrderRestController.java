package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.entity.Order;
import com.api.ecommerce.service.OrderService;

@RestController
public class OrderRestController {
	
	@Autowired
	private OrderService service;
	
	@PostMapping("/api/user/{userId}/order")
	public ResponseEntity<?> createOrderForUser(@PathVariable Long userId, @RequestBody Order order) {
		return service.createOrderForUser(userId, order);
	}
	
	@GetMapping("/api/user/{userId}/order")
	public ResponseEntity<?> retrieveOrdersForUser(@PathVariable Long userId) {
		return service.retrieveOrdersForUser(userId);
	}
	
	@GetMapping("/api/order/{orderId}")
	public ResponseEntity<?> retrieveOrder(@PathVariable Long orderId) {
		return service.retrieveOrder(orderId);
	}
	
	@DeleteMapping("/api/order/{orderId}")
	public ResponseEntity<?> removeOrder(@PathVariable Long orderId) {
		return service.removeOrder(orderId);
	}
 
}
