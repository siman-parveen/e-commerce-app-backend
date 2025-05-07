package com.api.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.Payment;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.response.Response;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		try {
			
			repo.findByTransactionId(paymentDTO.getTransactionId()).orElseThrow(() -> new RuntimeException("Transaction ID already exists"));
			
			Order order = commonService.findOrderByExternalId(orderExternalId);
			Payment payment = commonService.createNewPaymentForOrder(order, paymentDTO);
			
			PaymentResponseDTO dto = commonService.convertPaymentToPaymentResponseDTO(payment);
			
			repo.save(payment);
			response.setSuccess(true);
			response.setMessage(CommonConstant.PAYMENT_CREATED_SUCCESSFULLY);
			response.setData(dto);
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveAllPayments() {
		Response<List<PaymentResponseDTO>> response = new Response<>();
		
		try {
			List<Payment> payments = repo.findAll();
			List<PaymentResponseDTO> paymentList = new ArrayList<>();
			
			payments.stream().forEach(payment -> {
				paymentList.add(commonService.convertPaymentToPaymentResponseDTO(payment));
			});
			
			response.setSuccess(true);
			response.setMessage("Payments retrieved successfully!!");
			response.setData(paymentList);
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}

}
