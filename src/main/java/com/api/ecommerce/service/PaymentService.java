package com.api.ecommerce.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.Payment;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.response.Response;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepo repo;
	
	@Autowired
	private OrderRepo orderRepo;

	public ResponseEntity<?> makePaymentForOrder(Long orderId) {
		Response<Payment> response = new Response<>();
		
		try {
			Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("No order (with given id) found!!"));
			Payment payment = new Payment();
			payment.setTotalAmount(null);
			payment.setOrder(order);
			payment.setPaymentDate(LocalDateTime.now());
			payment.setPaymentMode(null);
			payment.setPaymentStatus(null);
			payment.setTransactionId("");
			repo.save(payment);
			response.setSuccess(true);
			response.setMessage(CommonConstant.PAYMENT_CREATED_SUCCESSFULLY);
			response.setData(payment);
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
