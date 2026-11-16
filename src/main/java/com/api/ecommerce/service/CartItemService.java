package com.api.ecommerce.service;

import com.api.ecommerce.response.Response;


public interface CartItemService {
	public Response<?> retrieveCartItems(String cartItemExternalId);
	public Response<?> addQuantityForCartItem(String cartItemExternalId, int quantity);
	public Response<?> removeQuantityForCartItem(String cartItemExternalId, int quantity);
	public Response<?> removeCartItem(String cartItemExternalId);
}
