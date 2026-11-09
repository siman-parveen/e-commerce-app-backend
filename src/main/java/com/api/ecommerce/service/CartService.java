package com.api.ecommerce.service;

import org.springframework.http.ResponseEntity;

public interface CartService {

	public ResponseEntity<?> retrieveCartForUser(String userExternalId);
	public ResponseEntity<?> addItemToCart(String userExternalId, String productExternalId, Integer quantity);
	public ResponseEntity<?> clearCart(String userExternalId);

}
