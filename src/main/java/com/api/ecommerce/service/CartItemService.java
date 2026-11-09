package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface CartItemService {
	public ResponseEntity<?> retrieveCartItems(String cartItemExternalId);
	public ResponseEntity<?> addQuantityForCartItem(String cartItemExternalId, int quantity);
	public ResponseEntity<?> removeQuantityForCartItem(String cartItemExternalId, int quantity);
	public ResponseEntity<?> removeCartItem(String cartItemExternalId);
}
