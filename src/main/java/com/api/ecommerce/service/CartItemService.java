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

	public ResponseEntity<?> retrieveCartItems(String cartItemExternalId) {
		
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = findCartItemByExternalId(cartItemExternalId);
			response.setSuccess(true);
			response.setMessage("Cart item retrieved successfully!!");
			response.setData(cartItem);
			
			
		} catch (RuntimeException e) {
			response.setSuccess(false);
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

	public ResponseEntity<?> addQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		try {
			
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
			
			
		} catch (RuntimeException e) {
			response.setSuccess(false);
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

	public ResponseEntity<?> removeQuantityForCartItem(String cartItemExternalId, int quantity) {
		Response<CartItem> response = new Response<>();
		
		try {
			
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
			
			
		} catch (RuntimeException e) {
			response.setSuccess(false);
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
	
	public ResponseEntity<?> removeCartItem(String cartItemExternalId) {
		Response<CartItem> response = new Response<>();
		
		try {
						
			CartItem cartItem = findCartItemByExternalId(cartItemExternalId);

			itemRepo.deleteById(cartItem.getEntityId());
			
			response.setSuccess(true);
			response.setMessage("Cart item removed successfully!!");			
			
		} catch (RuntimeException e) {
			response.setSuccess(false);
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
	
	private CartItem findCartItemByExternalId(String cartItemExternalId){
		return itemRepo.findByExternalId(cartItemExternalId).orElseThrow(() -> new RuntimeException("Cart item not found!!"));
	}
}
