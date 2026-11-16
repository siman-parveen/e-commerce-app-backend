package com.api.ecommerce.controller;

import com.api.ecommerce.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import com.api.ecommerce.dto.request.QuantityDTO;
import com.api.ecommerce.service.CartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user/cart/items")
@Tag(name = "Cart Item API", description = "Operations related to Cart Items of the registered user")
public class CartItemRestController {
	
	@Autowired
	private CartItemService service;
	
	@Operation(summary = "Add quantity for a user's cart item", description = "API increases the cart item quantity and returns the updated cart item")
	@PatchMapping("/{cartItemExternalId}/add")
	@PreAuthorize("@userAuthorization.ownsCartItem(#p0)")
	public ResponseEntity<?> addQuantityForCartItem(@PathVariable String cartItemExternalId, @RequestBody QuantityDTO quantityDto){
		Response<?> response = service.addQuantityForCartItem(cartItemExternalId, quantityDto.getQuantity());
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Remove quantity for a user's cart item", description = "API decreases the cart item quantity and returns the updated cart item")
	@PatchMapping("/{cartItemExternalId}/remove")
	@PreAuthorize("@userAuthorization.ownsCartItem(#p0)")
	public ResponseEntity<?> removeQuantityForCartItem(@PathVariable String cartItemExternalId, @RequestBody QuantityDTO quantityDto){
		Response<?> response = service.removeQuantityForCartItem(cartItemExternalId, quantityDto.getQuantity());
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Delete cart item for the user", description = "API deletes the product from user's cart")
	@DeleteMapping("/{cartItemExternalId}")
	@PreAuthorize("@userAuthorization.ownsCartItem(#p0)")
	public ResponseEntity<?> removeCartItem(@PathVariable String cartItemExternalId){
		Response<?> response = service.removeCartItem(cartItemExternalId);
		return ResponseEntity.ok().body(response);
	}

}
