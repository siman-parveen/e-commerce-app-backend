package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.service.CartService;

@RestController
@RequestMapping("/api")
public class CartRestController {
	
	@Autowired
	private CartService service;
	
	@GetMapping("/user/{userId}/cart")
	public ResponseEntity<?> retrieveCartForUser(@PathVariable Long userId) {
		return service.retrieveCartForUser(userId);
		
	}

}
