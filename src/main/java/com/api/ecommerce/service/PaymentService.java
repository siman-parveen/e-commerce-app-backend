package com.api.ecommerce.service;

import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.response.Response;

import com.api.ecommerce.dto.request.PaymentRequestDTO;

public interface PaymentService {
	
	public Response<?> makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO);
	public Response<?> retrieveAllPayments();
	public Response<?> retrievePaymentForOrder(String orderExternalId);
	public Response<PaymentResponseDTO> refundPayment(String orderExternalId);
	public Response<PaymentResponseDTO> refundPaymentByTransactionId(String transactionId);

}
