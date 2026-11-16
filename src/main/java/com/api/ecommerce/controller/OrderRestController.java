package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Order API", description = "Operations related to Orders of the registered user")
public class OrderRestController {
	
	@Autowired
	private OrderService service;
	
	@Operation(summary = "Create order for user", description = "API creates a new order for the user")
	@PostMapping("/api/user/order")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0.userExternalId)")
	public ResponseEntity<?> createOrderForUser(@Valid @RequestBody OrderRequestDTO order) {
		Response<?> response = service.createOrderForUser(order);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve User orders by User External ID", description = "API retrieves all the orders for a particular user")
	@GetMapping("/api/user/{userExternalId}/order")
	@PreAuthorize("@userAuthorization.isCurrentUser(#p0)")
	public ResponseEntity<?> retrieveOrdersForUser(@PathVariable String userExternalId) {
		Response<?> response = service.retrieveOrdersForUser(userExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Delete order for the user", description = "API deletes the order using the Order ID by user")
	@PatchMapping("/api/user/order/{orderExternalId}")
	@PreAuthorize("@userAuthorization.ownsOrder(#p0)")
	public ResponseEntity<?> cancelOrder(@PathVariable String orderExternalId) {
		Response<?> response = service.cancelOrder(orderExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve order by order ID", description = "API retrieve the order by order External ID")
	@GetMapping("/api/admin/order/{orderExternalId}")
	public ResponseEntity<?> retrieveOrderForAdmin(@PathVariable String orderExternalId) {
		Response<?> response = service.retrieveOrder(orderExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve order by order ID", description = "API retrieve the order by order External ID")
	@GetMapping("/api/user/order/{orderExternalId}")
	@PreAuthorize("@userAuthorization.ownsOrder(#p0)")
	public ResponseEntity<?> retrieveOrderForUser(@PathVariable String orderExternalId) {
		Response<?> response = service.retrieveOrder(orderExternalId);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Retrieve all orders", description = "API retrieve all the orders")
	@GetMapping("/api/admin/orders")
	public ResponseEntity<?> retrieveAllOrders() {
		Response<?> response = service.retrieveAllOrders();
		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "Update the order status", description = "API updates the order status")
	@PatchMapping("/api/admin/order/{orderExternalId}/update/status")
	public ResponseEntity<?> markOrder(@PathVariable String orderExternalId,  @RequestParam String status) {
		Response<?> response = service.updateOrderStatus(orderExternalId, status);
		return ResponseEntity.ok().body(response);
	}
 
}
