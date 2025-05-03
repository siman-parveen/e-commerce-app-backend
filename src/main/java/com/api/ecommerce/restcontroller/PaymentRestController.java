package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.service.PaymentService;

@RestController
public class PaymentRestController {
	
	@Autowired
	private PaymentService service;
	
	@PostMapping("/api/order/{orderId}/payment")
	public ResponseEntity<?> makePaymentForOrder(@PathVariable Long orderId){
		return service.makePaymentForOrder(orderId);
	}

}
