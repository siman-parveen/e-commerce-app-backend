package com.api.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Payment API", description = "Operations related to Payment made by the User when placing the order")
public class PaymentRestController {
	
	@Autowired
	private PaymentService service;
	
	@Operation(summary = "Make a payment for an order", description = "Processes the supplied payment for the order identified by its external ID.")
	@PostMapping("/api/user/order/{orderExternalId}/payment")
	public ResponseEntity<?> makePaymentForOrder(@PathVariable String orderExternalId, @Valid @RequestBody PaymentRequestDTO payment) {
		return service.makePaymentForOrder(orderExternalId, payment);
	}
	
	@Operation(summary = "Retrieve payment for an order", description = "Returns the payment record associated with the specified order.")
	@GetMapping("/api/user/order/{orderExternalId}/payment")
	public ResponseEntity<?> retrievePaymentForOrderForUser(@PathVariable String orderExternalId) {
		return service.retrievePaymentForOrder(orderExternalId);
	}
	
	@Operation(summary = "Retrieve all payments", description = "Returns payment records for all orders.")
	@GetMapping("/api/admin/payments")
	public ResponseEntity<?> retrieveAllPayments() {
		return service.retrieveAllPayments();
	}

	@Operation(summary = "Refund Payment By Order Id", description = "API refunds the payment made to the order.")
	@PatchMapping("/api/user/order/{orderExternalId}/refund/payment")
	public ResponseEntity<?> refundPaymentByOrderExternalId(@PathVariable String orderExternalId) {
		return service.refundPayment(orderExternalId);
	}

	@Operation(summary = "Refund Payment By Transaction Id", description = "API refunds the payment transaction.")
	@PatchMapping("/api/user/order/payment/{transactionId}")
	public ResponseEntity<?> refundPaymentByTransactionId(@PathVariable String transactionId) {
		return service.refundPaymentByTransactionIdd(transactionId);
	}

}
