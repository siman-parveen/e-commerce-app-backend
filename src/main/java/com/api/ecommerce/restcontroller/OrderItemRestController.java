package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.response.OrderItemResponseDTO;
import com.api.ecommerce.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Order Items API", description = "Operations related to Products that  has been ordered by the registered user")
public class OrderItemRestController {
	
	@Autowired
	private OrderItemService service;
	
	@Operation(summary = "Retrieve User's order items by Order ID", description = "API retrieves the order items by the Order ID by user")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Order Item retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = OrderItemResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/api/user/order/{orderExternalId}/items")
	public ResponseEntity<?> retrieveOrderItemForOrder(@PathVariable String orderExternalId) {
		return service.retrieveOrderItemForOrder(orderExternalId);
	}

}
