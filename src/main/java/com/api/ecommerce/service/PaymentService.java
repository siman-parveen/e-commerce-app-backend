package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

import com.api.ecommerce.dto.request.PaymentRequestDTO;

public interface PaymentService {
	
	public ResponseEntity<?> makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO);
	public ResponseEntity<?> retrieveAllPayments();
	public ResponseEntity<?> retrievePaymentForOrder(String orderExternalId);
	public ResponseEntity<?> refundPayment(String orderExternalId);
	public ResponseEntity<?> refundPaymentByTransactionIdd(String transactionId);

}
