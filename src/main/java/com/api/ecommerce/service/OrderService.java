package com.api.ecommerce.service;

import com.api.ecommerce.dto.response.OrderResponseDTO;
import com.api.ecommerce.response.Response;

import com.api.ecommerce.dto.request.OrderRequestDTO;
import jakarta.validation.Valid;

public interface OrderService {
	
	public Response<?> retrieveOrdersForUser(String userExternalId);
	public Response<?> retrieveOrder(String orderExternalId);
	public Response<?> createOrderForUser(@Valid OrderRequestDTO orderRqst);
	public Response<OrderResponseDTO> cancelOrder(String orderExternalId);
	public Response<?> retrieveAllOrders();
	public Response<?> updateOrderStatus(String orderExternalId, String status);

}
