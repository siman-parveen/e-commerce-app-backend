package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


public interface OrderItemService {
	
	public Response<?> retrieveOrderItemForOrder(String orderExternalId);

}
