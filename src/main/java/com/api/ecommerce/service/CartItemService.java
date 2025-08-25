package com.api.ecommerce.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.repo.CartItemRepo;
import com.api.ecommerce.response.Response;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepo itemRepo;

	public ResponseEntity<?> retrieveCartItems(String cartItemExternalId) {
		
		Response<CartItem> response = new Response<>();
				
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage("Cart item retrieved successfully!!");
		response.setData(cartItem);
			
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> addQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		if(quantity < 1) {
			response.setSuccess(false);
			response.setMessage("Item quantity must be greater than 1");
			return ResponseEntity.ok().body(response);
		}
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		cartItem.setQuantity(cartItem.getQuantity() + quantity);
		
		itemRepo.save(cartItem);
		
		response.setSuccess(true);
		response.setMessage("Cart item quantity increased successfully!!");
		response.setData(cartItem);
			
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> removeQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		if(quantity < 1) {
			response.setSuccess(false);
			response.setMessage("Item quantity must be greater than 1");
			return ResponseEntity.ok().body(response);
		}
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return ResponseEntity.ok().body(response);
		}

		if((cartItem.getQuantity() - quantity) <= 0) {
			
			itemRepo.deleteById(cartItem.getEntityId());
			response.setSuccess(false);
			response.setMessage("Cart item removed!!");
			return ResponseEntity.ok().body(response);
			
		}else {
			cartItem.setQuantity(cartItem.getQuantity() - quantity);
		}
		
		itemRepo.save(cartItem);
		
		response.setSuccess(true);
		response.setMessage("Cart item quantity removed successfully!!");
		response.setData(cartItem);
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeCartItem(String cartItemExternalId) {
		Response<CartItem> response = new Response<>();
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return ResponseEntity.ok().body(response);
		}

		itemRepo.deleteById(cartItem.getEntityId());
		
		response.setSuccess(true);
		response.setMessage("Cart item removed successfully!!");			
		
		return ResponseEntity.ok().body(response);
	}
	
	private CartItem findCartItemByExternalId(String cartItemExternalId){
		Optional<CartItem> cartItem = itemRepo.findByExternalId(cartItemExternalId);
		
		if(cartItem.isEmpty()) {
			return null;
		}
		
		return cartItem.get();
	}
}
