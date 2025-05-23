package com.api.ecommerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.CartRepo;
import com.api.ecommerce.response.Response;

@Service
public class CartService {
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveCartForUser(String userExternalId) {
		
		Response<Cart> response = new Response<Cart>();
		
		try {
			User user = commonService.findUserByExternalId(userExternalId);

			Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("No cart found for the user!!"));
			response.setSuccess(true);
			response.setMessage(CommonConstant.CART_RETRIEVED_SUCCESS_FOR_USER);
			response.setData(cart);
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

	public ResponseEntity<?> addItemToCart(String userExternalId, String productExternalId, Integer quantity) {
		Response<Cart> response = new Response<Cart>();
		
		try {
			
			User user = commonService.findUserByExternalId(userExternalId);
			
			Product product = commonService.findProductByExternalId(productExternalId);
			
			Cart cart = cartRepo.findByUser(user).orElseGet(() -> {
				Cart newCart = new Cart();
				newCart.setUser(user);
				newCart.setCartItem(new ArrayList<>());
				return newCart;
			});
			
			 Optional<CartItem> existingCartItem = cart.getCartItem().stream()
					 .filter(item -> item.getProduct().getEntityId().equals(product.getEntityId())).findFirst();
			 
			 CartItem cartItem;
			 
			 if(existingCartItem.isPresent()) {
				 cartItem = existingCartItem.get();
				 cartItem.setQuantity(cartItem.getQuantity() + quantity);
			 } else {
				 cartItem = new CartItem();
				 cartItem.setCart(cart);
				 cartItem.setProduct(product);
				 cartItem.setQuantity(quantity);
				 cart.getCartItem().add(cartItem);
			 }
			 
			 cartRepo.save(cart);
			 
			 response.setSuccess(true);
			 response.setMessage("Item added to cart!!");
			 response.setData(cart);
			
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
	
	
	public ResponseEntity<?> clearCart(String userExternalId){
		
		Response<Cart> response = new Response<Cart>();
		
		try {
			
			User user = commonService.findUserByExternalId(userExternalId);
			
			Cart cart = cartRepo.findByUser(user).orElseThrow(() -> new RuntimeException("No cart found for the user!!"));
			cart.getCartItem().clear();
			
			cartRepo.save(cart);
			
			response.setSuccess(true);
			response.setMessage("Cart is cleared!!");
			response.setData(cart);
			
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
