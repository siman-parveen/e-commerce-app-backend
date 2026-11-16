package com.api.ecommerce.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.repo.CartItemRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {
	
	@Autowired
	private CartItemRepo itemRepo;

	public Response<?> retrieveCartItems(String cartItemExternalId) {
		
		Response<CartItem> response = new Response<>();
				
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return response;
		}
		
		response.setSuccess(true);
		response.setMessage("Cart item retrieved successfully!!");
		response.setResponseData(cartItem);
			
		return response;
	}

	public Response<?> addQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		if(quantity < 1) {
			response.setSuccess(false);
			response.setMessage("Item quantity must be greater than 1");
			return response;
		}
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return response;
		}
		
		int updatedQuantity = cartItem.getQuantity() + quantity;
		if (updatedQuantity > cartItem.getProduct().getStock()) {
			response.setSuccess(false);
			response.setMessage(String.format("Only %d units available for this product", cartItem.getProduct().getStock()));
			return response;
		}
		cartItem.setQuantity(updatedQuantity);
		
		itemRepo.save(cartItem);
		
		response.setSuccess(true);
		response.setMessage("Cart item quantity increased successfully!!");
		response.setResponseData(cartItem);
			
		return response;
	}

	public Response<?> removeQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		if(quantity < 1) {
			response.setSuccess(false);
			response.setMessage("Item quantity must be greater than 1");
			return response;
		}
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return response;
		}

		if((cartItem.getQuantity() - quantity) < 0) {
			
			itemRepo.deleteById(cartItem.getEntityId());
			response.setSuccess(false);
			response.setMessage("Cart item removed!!");
			return response;
			
		}else {
			cartItem.setQuantity(cartItem.getQuantity() - quantity);
		}
		
		itemRepo.save(cartItem);
		
		response.setSuccess(true);
		response.setMessage("Cart item quantity removed successfully!!");
		response.setResponseData(cartItem);
		
		return response;
	}
	
	public Response<?> removeCartItem(String cartItemExternalId) {
		Response<CartItem> response = new Response<>();
					
		CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
		
		if(cartItem == null) {
			response.setSuccess(false);
			response.setMessage("Cart Item not found!!");
			return response;
		}

		itemRepo.deleteById(cartItem.getEntityId());
		
		response.setSuccess(true);
		response.setMessage("Cart item removed successfully!!");			
		
		return response;
	}
	
	private CartItem findCartItemByExternalId(String cartItemExternalId){
		Optional<CartItem> cartItem = itemRepo.findByExternalId(cartItemExternalId);
        return cartItem.orElse(null);
    }
}
