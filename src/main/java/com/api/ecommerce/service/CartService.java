package com.api.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.api.ecommerce.repo.CartRepo;
import com.api.ecommerce.repo.UserRepo;

public class CartService {
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private UserRepo userRepo;

	public ResponseEntity<?> retrieveCartForUser(Long userId) {
		return null;
	}
}
