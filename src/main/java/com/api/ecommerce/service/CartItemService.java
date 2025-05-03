package com.api.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.repo.CartItemRepo;
import com.api.ecommerce.response.Response;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepo itemRepo;

	public ResponseEntity<?> retrieveCartItems(Long cartItemId) {
		
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = itemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found!!"));
			
			response.setSuccess(true);
			response.setMessage("Cart item retrieved successfully!!");
			response.setData(cartItem);
			
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> addQuantityForCartItem(Long cartItemId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = itemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found!!"));
			
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			
			itemRepo.save(cartItem);
			
			response.setSuccess(true);
			response.setMessage("Cart item quantity increased successfully!!");
			response.setData(cartItem);
			
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> removeQuantityForCartItem(Long cartItemId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = itemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found!!"));

			if((cartItem.getQuantity() - quantity) <= 0) {
				cartItem.setQuantity(0);
			}else {
				cartItem.setQuantity(cartItem.getQuantity() - quantity);
			}
			
			itemRepo.save(cartItem);
			
			response.setSuccess(true);
			response.setMessage("Cart item quantity removed successfully!!");
			response.setData(cartItem);
			
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
	
	public ResponseEntity<?> removeCartItem(Long cartItemId) {
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = itemRepo.findById(cartItemId).orElseThrow(() -> new RuntimeException("Cart item not found!!"));

			itemRepo.delete(cartItem);
			
			response.setSuccess(true);
			response.setMessage("Cart item removed successfully!!");			
			
		} catch (RuntimeException e) {
			response.setSuccess(true);
			response.setMessage(e.getMessage());
		}
		catch (Exception e) {
			response.setSuccess(false);
			response.setMessage(e.getMessage() != null ? e.getMessage() : CommonConstant.CALL_FAILED_ON_SERVER);
			response.setErrorTrace(e.getStackTrace().toString());
			return ResponseEntity.internalServerError().body(response);
		}
		
		return ResponseEntity.ok().body(response);
	}
}
