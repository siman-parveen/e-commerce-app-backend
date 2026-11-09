package com.api.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.request.ProductQuantityRequestDTO;
import com.api.ecommerce.dto.response.CartResponseDTO;
import com.api.ecommerce.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/{userExternalId}/cart")
@Tag(name = "Cart API", description = "Operations related to Cart of the registered user")
public class CartRestController {
	
	@Autowired
	private CartService service;
	
	@Operation(summary = "Retrieve User's cart by UserExternalId", description = "API retrieves the cart details of the user by User's External ID")
	@GetMapping
	public ResponseEntity<?> retrieveCartForUser(@PathVariable String userExternalId) {
		return service.retrieveCartForUser(userExternalId);
	}
	
	@Operation(summary = "Add a product to the User's cart", description = "API returns the cart Item by the External ID")
	@PostMapping
	public ResponseEntity<?> addItemToCart(@PathVariable String userExternalId, @Valid @RequestBody ProductQuantityRequestDTO product) {
		return service.addItemToCart(userExternalId, product.getProductExternalId(), product.getQuantity());
		
	}

}
