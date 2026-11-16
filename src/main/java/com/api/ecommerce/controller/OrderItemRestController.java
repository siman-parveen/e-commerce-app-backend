package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.api.ecommerce.dto.response.OrderItemResponseDTO;
import com.api.ecommerce.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Order Items API", description = "Operations related to Products that  has been ordered by the registered user")
public class OrderItemRestController {
	
	@Autowired
	private OrderItemService service;
	
	@Operation(summary = "Retrieve User's order items by Order ID", description = "API retrieves the order items by the Order ID by user")
	@GetMapping("/api/user/order/{orderExternalId}/items")
	@PreAuthorize("@userAuthorization.ownsOrder(#p0)")
	public ResponseEntity<?> retrieveOrderItemForOrder(@PathVariable String orderExternalId) {
		Response<?> response = service.retrieveOrderItemForOrder(orderExternalId);
		return ResponseEntity.ok().body(response);
	}

}
