package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Order API", description = "Operations related to Orders of the registered user")
public class OrderRestController {
	
	@Autowired
	private OrderService service;
	
	@Operation(summary = "Create order for user", description = "API creates a new order for the user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order created!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping("/api/user/{userExternalId}/order")
	public ResponseEntity<?> createOrderForUser(@PathVariable String userExternalId, @RequestBody @Valid OrderRequestDTO order) {
		return service.createOrderForUser(userExternalId, order);
	}
	
	@Operation(summary = "Retrieve User orders by User External ID", description = "API retrieves all the orders for a particular user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/api/user/{userExternalId}/order")
	public ResponseEntity<?> retrieveOrdersForUser(@PathVariable String userExternalId) {
		return service.retrieveOrdersForUser(userExternalId);
	}
	
	@Operation(summary = "Delete order for the user", description = "API deletes the order using the Order ID by user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order deleted successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/api/user/order/{orderExternalId}")
	public ResponseEntity<?> removeOrder(@PathVariable String orderExternalId) {
		return service.removeOrder(orderExternalId);
	}
	
	@Operation(summary = "Retrieve order by order ID", description = "API retrieve the order by order External ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = OrderResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/api/admin/order/{orderExternalId}")
	public ResponseEntity<?> retrieveOrder(@PathVariable String orderExternalId) {
		return service.retrieveOrder(orderExternalId);
	}
 
}
