package com.api.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.OrderStatus;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.OrderService;

import jakarta.validation.Valid;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveOrdersForUser(String userExternalId) {
		Response<List<OrderResponseDTO>> response = new Response<>();

		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
	        response.setMessage(CommonConstant.NO_USER_FOUND);
	        return ResponseEntity.ok().body(response);
		}

		Optional<List<Order>> orders = orderRepo.findByUser(user);
		
		if (orders.isEmpty() || orders.get().isEmpty()) {
	        response.setSuccess(false);
	        response.setMessage("No orders found for the user!!");
	        return ResponseEntity.ok().body(response);
	    }
		
		List<OrderResponseDTO> ordersDto = orders.get().stream().map((order) -> commonService.convertOrderToOrderResponseDto(order)).toList();
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDERS_RETRIEVED_SUCCESS_FOR_USER);
		response.setResponseData(ordersDto);
			
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> retrieveOrder(String orderExternalId) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_RETRIEVED_SUCCESS_FOR_USER);
		response.setResponseData(commonService.convertOrderToOrderResponseDto(order));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> createOrderForUser(String userExternalId, @Valid OrderRequestDTO orderRequest) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.convertOrderRequestDtoToOrder(orderRequest);
		
		orderRepo.save(order);
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_CREATED_SUCCESS_FOR_USER);
		response.setResponseData(commonService.convertOrderToOrderResponseDto(order));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> cancelOrder(String orderExternalId) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return ResponseEntity.ok().body(response);
		}
		
		order.setOrderStatus(OrderStatus.CANCELLED);
		
		orderRepo.save(order);
		
		//orderRepo.deleteById(order.getEntityId());
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_DELETED_SUCCESS_FOR_USER);

		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> retrieveAllOrders() {
		Response<List<OrderResponseDTO>> response = new Response<>();

		List<Order> orders = orderRepo.findAll();
		
		if (orders.isEmpty()) {
	        response.setSuccess(false);
	        response.setMessage("No orders made yet!!");
	        return ResponseEntity.ok().body(response);
	    }
		
		List<OrderResponseDTO> ordersDto = orders.stream().map(order -> commonService.convertOrderToOrderResponseDto(order)).toList();
				
		response.setSuccess(true);
		response.setMessage("Orders retrieved successfully");
		response.setResponseData(ordersDto);
			
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> updateOrderStatus(String orderExternalId, String status) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		
		if(order == null) {
			response.setSuccess(false);
	        response.setMessage("No orders found with the given id!!");
	        return ResponseEntity.ok().body(response);
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
		
		return ResponseEntity.ok().body(response);
		
	}

}
