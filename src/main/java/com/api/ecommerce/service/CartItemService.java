package com.api.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.exception.ApiException;
import com.api.ecommerce.repo.CartItemRepo;
import com.api.ecommerce.response.Response;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepo itemRepo;

	public ResponseEntity<?> retrieveCartItems(String cartItemExternalId) {
		
		Response<CartItem> response = new Response<>();
				
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
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

		if((cartItem.getQuantity() - quantity) <= 0) {
			cartItem.setQuantity(0);
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

		itemRepo.deleteById(cartItem.getEntityId());
		
		response.setSuccess(true);
		response.setMessage("Cart item removed successfully!!");			
		
		return ResponseEntity.ok().body(response);
	}
	
	private CartItem findCartItemByExternalId(String cartItemExternalId){
		return itemRepo.findByExternalId(cartItemExternalId).orElseThrow(() -> new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Cart item not found!!"));
	}
}
