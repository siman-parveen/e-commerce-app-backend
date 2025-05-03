package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.service.CartItemService;

@RestController
@RequestMapping("/api/cart/items")
public class CartItemRestController {
	
	@Autowired
	private CartItemService service;
	
	@GetMapping("/{cartItemId}")
	public ResponseEntity<?> retrieveCartItems(@PathVariable Long cartItemId){
		return service.retrieveCartItems(cartItemId);
	}
	
	@PatchMapping("/{cartItemId}/add")
	public ResponseEntity<?> addQuantityForCartItem(@PathVariable Long cartItemId, @RequestBody int quantity){
		return service.addQuantityForCartItem(cartItemId, quantity);
	}
	
	@PatchMapping("/{cartItemId}/remove")
	public ResponseEntity<?> removeQuantityForCartItem(@PathVariable Long cartItemId, @RequestBody int quantity){
		return service.removeQuantityForCartItem(cartItemId, quantity);
	}
	
	@DeleteMapping("/{cartItemId}")
	public ResponseEntity<?> removeCartItem(@PathVariable Long cartItemId){
		return service.removeCartItem(cartItemId);
	}

}
