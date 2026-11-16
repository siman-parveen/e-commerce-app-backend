package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


public interface CartService {

	public Response<?> retrieveCartForUser(String userExternalId);
	public Response<?> addItemToCart(String userExternalId, String productExternalId, Integer quantity);
	public Response<?> clearCart(String userExternalId);

}
