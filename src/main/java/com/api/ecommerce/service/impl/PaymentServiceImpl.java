package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderItem;
import com.api.ecommerce.entity.OrderStatus;
import com.api.ecommerce.entity.Payment;
import com.api.ecommerce.entity.PaymentStatus;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepo repo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		 Optional<Payment> paymentForTransactionId = repo.findByTransactionId(paymentDTO.getTransactionId());
		
		if(paymentForTransactionId.isPresent()) {
			response.setSuccess(true);
			response.setMessage("Payment for Transaction ID already exists");
			response.setData(commonService.convertPaymentToPaymentResponseDTO(paymentForTransactionId.get()));
			return ResponseEntity.ok().body(response);
		}
		Payment payment = commonService.makePaymentForOrder(orderExternalId, paymentDTO);
		
		if(payment.getPaymentStatus().equals(PaymentStatus.FAILED)) {
			response.setSuccess(false);
			response.setMessage("Payment failed!!");
			return ResponseEntity.ok().body(response);
		}
			
		List<OrderItem> orderItems = payment.getOrder().getOrderItems();
		
		orderItems.forEach(item -> {
			Product product = item.getProduct();
			product.setStock(product.getStock() - item.getQuantity());
		});
		
		commonService.updatePaymentStatus(payment, PaymentStatus.SUCCESS);

	
		repo.save(payment);			
		
		PaymentResponseDTO dto = commonService.convertPaymentToPaymentResponseDTO(payment);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.PAYMENT_CREATED_SUCCESSFULLY);
		response.setData(dto);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveAllPayments() {
		Response<List<PaymentResponseDTO>> response = new Response<>();
		
		List<Payment> payments = repo.findAll();
		
		if(payments.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No payment received!!");
			return ResponseEntity.ok().body(response);
		}
		
		List<PaymentResponseDTO> paymentList = new ArrayList<>();
		
		payments.forEach(payment -> {
			paymentList.add(commonService.convertPaymentToPaymentResponseDTO(payment));
		});
		
		response.setSuccess(true);
		response.setMessage("Payments retrieved successfully!!");
		response.setData(paymentList);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrievePaymentForOrder(String orderExternalId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given usser id!!");
			return ResponseEntity.ok().body(response);
		}
		
		Optional<Payment> payment = repo.findByOrder_EntityId(order.getEntityId());
		
		if(payment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Payment not yet made for the order!!");
			return ResponseEntity.ok().body(response);
		} 
		
		response.setSuccess(true);
		response.setMessage("Payment retrieved successfully");
		response.setData(commonService.convertPaymentToPaymentResponseDTO(payment.get()));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> refundPayment(String orderExternalId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given user id!!");
			return ResponseEntity.ok().body(response);
		}
		
		if(!order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
			response.setSuccess(false);
			response.setMessage("Order is not yet cancelled!!");
			return ResponseEntity.ok().body(response);
		}
		
		Optional<Payment> orderPayment = repo.findByOrder_EntityId(order.getEntityId());
		
		if(orderPayment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Payment not yet made for the order!!");
			return ResponseEntity.ok().body(response);
		} 
		
		
		Payment payment = orderPayment.get();
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		
		response.setSuccess(true);
		response.setMessage("Order Payment refunded successfully");
		response.setData(commonService.convertPaymentToPaymentResponseDTO(payment));
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> refundPaymentByTransactionIdd(String transactionId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Optional<Payment> orderPayment = repo.findByTransactionId(transactionId);
		
		if(orderPayment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No order found with the given transaction ID!!");
			return ResponseEntity.ok().body(response);
		} 
		
		Payment payment = orderPayment.get();

		Order order = payment.getOrder();
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given user id!!");
			return ResponseEntity.ok().body(response);
		}
		
		if(!order.getOrderStatus().equals(OrderStatus.CANCELLED)) {
			response.setSuccess(false);
			response.setMessage("Order is not yet cancelled!!");
			return ResponseEntity.ok().body(response);
		}
		
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		
		response.setSuccess(true);
		response.setMessage("Transaction refunded.");
		response.setData(commonService.convertPaymentToPaymentResponseDTO(payment));
		
		return ResponseEntity.ok().body(response);
	}

}
