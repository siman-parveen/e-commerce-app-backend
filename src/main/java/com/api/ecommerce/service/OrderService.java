package com.api.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.request.OrderRequestDTO;
import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.entity.Order;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.exception.ApiException;
import com.api.ecommerce.repo.OrderRepo;
import com.api.ecommerce.response.Response;

import jakarta.validation.Valid;

@Service
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveOrdersForUser(String userExternalId) {
		Response<List<Order>> response = new Response<>();

		User user = commonService.findUserByExternalId(userExternalId);

		List<Order> orders = orderRepo.findByUser(user).orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,("No orders found for the user!!")));
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDERS_RETRIEVED_SUCCESS_FOR_USER);
		response.setData(orders);
			
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> retrieveOrder(String orderExternalId) {
		Response<Order> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_RETRIEVED_SUCCESS_FOR_USER);
		response.setData(order);
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> createOrderForUser(String userExternalId, @Valid OrderRequestDTO orderRqst) {
		Response<OrderResponseDTO> response = new Response<>();
		
		Order order = commonService.convertOrderRequestDtoToOrder(orderRqst);
		
		orderRepo.save(order);
		
		orderRqst.getOrderItems().stream().forEach(item -> {
			commonService.convertProductQuantityRequestDtoToOrderItem(item, order);
		});
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_CREATED_SUCCESS_FOR_USER);
		response.setData(commonService.convertOrderToOrderResponseDto(order));
		
		return ResponseEntity.ok().body(response);
	}

	public ResponseEntity<?> removeOrder(String orderExternalId) {
		Response<Order> response = new Response<>();
		
		Order order = commonService.findOrderByExternalId(orderExternalId);
		orderRepo.deleteById(order.getEntityId());
		response.setSuccess(true);
		response.setMessage(CommonConstant.ORDER_DELETED_SUCCESS_FOR_USER);

		return ResponseEntity.ok().body(response);
	}

}
