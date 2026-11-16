package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.ecommerce.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.PaymentRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.PaymentService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
	
	@Autowired
	private PaymentRepo repo;
	
	@Autowired
	private CommonService commonService;

	@Transactional
	public Response<?> makePaymentForOrder(String orderExternalId, PaymentRequestDTO paymentDTO) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		 Optional<Payment> paymentForTransactionId = repo.findByTransactionId(paymentDTO.getTransactionId());
		
		if(paymentForTransactionId.isPresent()) {
			Payment existingPayment = paymentForTransactionId.get();
			if (existingPayment.getOrder() != null && existingPayment.getOrder().getExternalId().equals(orderExternalId)) {
				response.setSuccess(true);
				response.setMessage("Payment for Transaction ID already exists");
				response.setResponseData(commonService.convertPaymentToPaymentResponseDTO(existingPayment));
				return response;
			}
			response.setSuccess(false);
			response.setMessage("Transaction ID already used for another order");
			return response;
		}
		Payment payment = commonService.makePaymentForOrder(orderExternalId, paymentDTO);
		
		if(payment.getPaymentStatus().equals(PaymentStatus.FAILED)) {
			response.setSuccess(false);
			response.setMessage("Payment failed!!");
			return response;
		}

		Order order = payment.getOrder();
		if (order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given id!!");
			return response;
		}

		if (repo.findByOrder_EntityId(order.getEntityId()).isPresent()) {
			response.setSuccess(false);
			response.setMessage("Payment already exists for this order");
			return response;
		}

		if (order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus().equals(OrderStatus.RETURNED)) {
			response.setSuccess(false);
			response.setMessage("Order is no longer payable");
			return response;
		}

		if (order.getTotalAmount().compareTo(paymentDTO.getTotalAmount()) != 0) {
			response.setSuccess(false);
			response.setMessage("Wrong Order Amount.");
			return response;
		}
			
		List<OrderItem> orderItems = order.getOrderItems();

		List<String> invalidOrderItems = new ArrayList<>();
		for (OrderItem item : orderItems) {
			Product product = item.getProduct();
			if (product == null) {
				invalidOrderItems.add("Unknown Product");
				continue;
			}
			if (item.getQuantity() == null || item.getQuantity() < 1 || item.getQuantity() > product.getStock()) {
				invalidOrderItems.add(product.getProductName());
			}
		}

		if (!invalidOrderItems.isEmpty()) {
			response.setSuccess(false);
			response.setMessage(String.format("Products Not Available - %s", String.join(",", invalidOrderItems)));
			return response;
		}
		
		commonService.reserveStockForOrderItems(orderItems);
		commonService.updatePaymentStatus(payment, PaymentStatus.SUCCESS);

		repo.save(payment);			
		
		PaymentResponseDTO dto = commonService.convertPaymentToPaymentResponseDTO(payment);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.PAYMENT_CREATED_SUCCESSFULLY);
		response.setResponseData(dto);
		
		return response;
	}

	public Response<?> retrieveAllPayments() {
		Response<List<PaymentResponseDTO>> response = new Response<>();
		
		List<Payment> payments = repo.findAll();
		
		if(payments.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No payment received!!");
			return response;
		}
		
		List<PaymentResponseDTO> paymentList = new ArrayList<>();

        for (Payment payment : payments) {
            paymentList.add(commonService.convertPaymentToPaymentResponseDTO(payment));
        }

        response.setSuccess(true);
		response.setMessage("Payments retrieved successfully!!");
		response.setResponseData(paymentList);
		
		return response;
	}

	public Response<?> retrievePaymentForOrder(String orderExternalId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given user id!!");
			return response;
		}
		
		Optional<Payment> payment = repo.findByOrder_EntityId(order.getEntityId());
		
		if(payment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Payment not yet made for the order!!");
			return response;
		} 
		
		response.setSuccess(true);
		response.setMessage("Payment retrieved successfully");
		response.setResponseData(commonService.convertPaymentToPaymentResponseDTO(payment.get()));
		
		return response;
	}

	@Transactional
	public Response<PaymentResponseDTO> refundPayment(String orderExternalId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given user id!!");
			return response;
		}

		if(!order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			response.setSuccess(false);
			response.setMessage("Order is not yet delivered!!");
			return response;
		}

		Optional<Payment> orderPayment = repo.findByOrder_EntityId(order.getEntityId());

		if(orderPayment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("Payment not yet made for the order!!");
			return response;
		}

		Payment payment = orderPayment.get();
		if (payment.getPaymentStatus().equals(PaymentStatus.REFUNDED) || order.getOrderStatus().equals(OrderStatus.RETURNED)) {
			response.setSuccess(false);
			response.setMessage("Order payment already refunded");
			return response;
		}
		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		commonService.releaseStockForOrderItems(order.getOrderItems());
		order.setOrderStatus(OrderStatus.RETURNED);
		payment.setOrder(order);
		repo.save(payment);

		response.setSuccess(true);
		response.setMessage("Order Payment refunded successfully");
		response.setResponseData(commonService.convertPaymentToPaymentResponseDTO(payment));

		return response;
	}

	@Transactional
	public Response<PaymentResponseDTO> refundPaymentByTransactionId(String transactionId) {
		Response<PaymentResponseDTO> response = new Response<>();
		
		Optional<Payment> orderPayment = repo.findByTransactionId(transactionId);
		
		if(orderPayment.isEmpty()) {
			response.setSuccess(false);
			response.setMessage("No order found with the given transaction ID!!");
			return response;
		} 
		
		Payment payment = orderPayment.get();

		Order order = payment.getOrder();
		
		if (order == null) {
			response.setSuccess(false);
			response.setMessage("No order found with given user id!!");
			return response;
		}

		if (!order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			response.setSuccess(false);
			response.setMessage("Order is not yet delivered!!");
			return response;
		}
		if (payment.getPaymentStatus().equals(PaymentStatus.REFUNDED) || order.getOrderStatus().equals(OrderStatus.RETURNED)) {
			response.setSuccess(false);
			response.setMessage("Order payment already refunded");
			return response;
		}

		payment.setPaymentStatus(PaymentStatus.REFUNDED);
		commonService.releaseStockForOrderItems(order.getOrderItems());
		order.setOrderStatus(OrderStatus.RETURNED);
		payment.setOrder(order);
		repo.save(payment);

		response.setSuccess(true);
		response.setMessage("Transaction refunded.");
		response.setResponseData(commonService.convertPaymentToPaymentResponseDTO(payment));
		
		return response;
	}

}
