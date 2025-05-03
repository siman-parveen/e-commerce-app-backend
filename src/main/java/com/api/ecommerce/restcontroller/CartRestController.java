package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.ProductQuantityRequestDTO;
import com.api.ecommerce.service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
	
	@Autowired
	private CartService service;
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> retrieveCartForUser(@PathVariable Long userId) {
		return service.retrieveCartForUser(userId);
	}
	
	@PostMapping("/user/{userId}")
	public ResponseEntity<?> addItemToCart(@PathVariable Long userId, @RequestBody @Valid ProductQuantityRequestDTO product) {
		return service.addItemToCart(userId, product.getProductExternalId(), product.getQuantity());
		
	}

}
