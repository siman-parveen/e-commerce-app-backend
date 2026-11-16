package com.api.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.api.ecommerce.dto.request.ProductQuantityRequestDTO;
import com.api.ecommerce.dto.response.PaymentResponseDTO;
import com.api.ecommerce.entity.*;
import com.api.ecommerce.repo.PaymentRepo;
import com.api.ecommerce.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.OrderService;

import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CommonService commonService;

    @Autowired
    private PaymentService paymentService;

	@Autowired
	private PaymentRepo paymentRepo;

	public Response<?> retrieveOrdersForUser(String userExternalId) {
		Response<List<OrderResponseDTO>> response = new Response<>();

		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
	        response.setMessage(CommonConstant.NO_USER_FOUND);
	        return response;
		}

		Optional<List<Order>> orders = orderRepo.findByUser(user);
		
		if (orders.isEmpty() || orders.get().isEmpty()) {
	        response.setSuccess(false);
	        response.setMessage("No orders found for the user!!");
	        return response;
	    }
		
		List<OrderResponseDTO> ordersDto = orders.get().stream().map((order) -> commonService.convertOrderToOrderResponseDto(order)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDERS_RETRIEVED_SUCCESS_FOR_USER);
		response.setResponseData(ordersDto);
			
		return response;
	}

	public Response<?> retrieveOrder(String orderExternalId) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return response;
		}
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_RETRIEVED_SUCCESS_FOR_USER);
		response.setResponseData(commonService.convertOrderToOrderResponseDto(order));
		
		return response;
	}

	public Response<?> createOrderForUser(@Valid OrderRequestDTO orderRequest) {
		Response<OrderResponseDTO> response = new Response<>();
		User user = commonService.findUserByExternalId(orderRequest.getUserExternalId());

		if(user == null) {
			response.setSuccess(false);
			response.setMessage("No user found with the given id!!");
			return response;
		}

		List<UserAddress> addresses = user.getAddress();
		boolean isDeliveryAddressValid = addresses.stream()
				.anyMatch(adr -> adr.getExternalId().equals(orderRequest.getDeliveryAddressExternalId()));

		if (!isDeliveryAddressValid) {
			response.setSuccess(false);
			response.setMessage("Invalid Delivery Address for user!!");
			return response;
		}

		List<String> invalidProductIds = new ArrayList<>();
		List<BigDecimal> totalAmountsForProduct = new ArrayList<>();
		List<ProductQuantityRequestDTO> orderItems = orderRequest.getOrderItems();
		orderItems.forEach(item -> {
			String productId = item.getProductExternalId();
			Product product = commonService.findProductByExternalId(productId);
			if (product == null || !product.isActive()) {
				invalidProductIds.add(productId);
			} else {
				totalAmountsForProduct.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
			}
		});

		if (!invalidProductIds.isEmpty()) {
			response.setSuccess(false);
			response.setMessage(String.format("Invalid Product Ids - %s", String.join(",", invalidProductIds)));
			return response;
		}

		BigDecimal totalAmounts = BigDecimal.ZERO;
		for (BigDecimal amount : totalAmountsForProduct) {
			totalAmounts = totalAmounts.add(amount);
		}

		if (totalAmounts.compareTo(orderRequest.getTotalAmount()) != 0) {
			response.setSuccess(false);
			response.setMessage("Wrong Order Amount.");
			return response;
		}

		Order order = commonService.convertOrderRequestDtoToOrder(orderRequest);
		
		orderRepo.save(order);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_CREATED_SUCCESS_FOR_USER);
		response.setResponseData(commonService.convertOrderToOrderResponseDto(order));
		
		return response;
	}

	@Transactional
	public Response<OrderResponseDTO> cancelOrder(String orderExternalId) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return response;
		}

		if (order.getOrderStatus().equals(OrderStatus.DELIVERED)) {
			response.setSuccess(false);
			response.setMessage("Delivered Order cannot be cancelled");
			return response;
		}
		if (order.getOrderStatus().equals(OrderStatus.CANCELLED) || order.getOrderStatus().equals(OrderStatus.RETURNED)) {
			response.setSuccess(false);
			response.setMessage("Order is already cancelled or returned");
			return response;
		}

		Optional<Payment> paymentOp = paymentRepo.findByOrder_EntityId(order.getEntityId());

		if (paymentOp.isPresent()) {
			Payment payment = paymentOp.get();
			PaymentStatus status = payment.getPaymentStatus();
			if (status.equals(PaymentStatus.REFUNDED)) {
				response.setSuccess(false);
				response.setMessage("Payment already refunded for this order");
				return response;
			}
			if (status.equals(PaymentStatus.SUCCESS)) {
				payment.setPaymentStatus(PaymentStatus.REFUNDED);
				commonService.releaseStockForOrderItems(order.getOrderItems());
				order.setOrderStatus(OrderStatus.CANCELLED);
				payment.setOrder(order);
				paymentRepo.save(payment);
				orderRepo.save(order);
			} else {
				order.setOrderStatus(OrderStatus.CANCELLED);
				orderRepo.save(order);
			}
		} else {
			order.setOrderStatus(OrderStatus.CANCELLED);
			orderRepo.save(order);
		}
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_DELETED_SUCCESS_FOR_USER);
		response.setResponseData(commonService.convertOrderToOrderResponseDto(order));

		return response;
	}

	public Response<?> retrieveAllOrders() {
		Response<List<OrderResponseDTO>> response = new Response<>();

		List<Order> orders = orderRepo.findAll();
		
		if (orders.isEmpty()) {
	        response.setSuccess(false);
	        response.setMessage("No orders made yet!!");
	        return response;
	    }
		
		List<OrderResponseDTO> ordersDto = orders.stream().map(order -> commonService.convertOrderToOrderResponseDto(order)).toList();
				
		response.setSuccess(true);
		response.setMessage("Orders retrieved successfully");
		response.setResponseData(ordersDto);
			
		return response;
	}

	public Response<?> updateOrderStatus(String orderExternalId, String status) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return response;
		}
		
		try {
			
			order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
			
			orderRepo.save(order);
			
			response.setSuccess(true);
			response.setMessage("Order Status Updated Successfully!!");

		}catch(Exception e) {
			
			response.setSuccess(false);
	        response.setMessage("Status is not valid!!");
		}
		
		return response;
		
	}

}
