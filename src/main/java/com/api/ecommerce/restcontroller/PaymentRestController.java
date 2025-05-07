package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Payment API", description = "Operations related to Payment made by the User when placing the order")
public class PaymentRestController {
	
	@Autowired
	private PaymentService service;
	
	@Operation(summary = "Retrieve Payment for a specific order", description = "API returns the payment made by the user to a specific order")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Payment retrieved successfully for the order!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = PaymentResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PostMapping("/api/user/order/{orderExternalId}/payment")
	public ResponseEntity<?> makePaymentForOrder(@PathVariable String orderExternalId, @Valid @RequestBody PaymentRequestDTO payment) {
		return service.makePaymentForOrder(orderExternalId, payment);
	}
	
	@Operation(summary = "Retrieve All Payments", description = "API returns the all the payments that are made by the users")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Payments retrieved successfully!!", content = @Content(mediaType = "/application/json", array = @ArraySchema(schema = @Schema(implementation = PaymentResponseDTO.class)))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@GetMapping("/api/admin/payments")
	public ResponseEntity<?> retrieveAllPayments() {
		return service.retrieveAllPayments();
	}

}
