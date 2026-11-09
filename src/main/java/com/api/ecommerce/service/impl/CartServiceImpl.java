package com.api.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.api.ecommerce.common.CommonConstant;
import com.api.ecommerce.common.CommonService;
import com.api.ecommerce.dto.response.CartResponseDTO;
import com.api.ecommerce.entity.Cart;
import com.api.ecommerce.entity.CartItem;
import com.api.ecommerce.entity.Product;
import com.api.ecommerce.entity.User;
import com.api.ecommerce.repo.CartRepo;
import com.api.ecommerce.response.Response;
import com.api.ecommerce.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CommonService commonService;

	public ResponseEntity<?> retrieveCartForUser(String userExternalId) {
		
		Response<CartResponseDTO> response = new Response<>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User not found!!");
			return ResponseEntity.ok().body(response);
		}

		Optional<Cart> cartFound = cartRepo.findByUser(user);
		
		if(cartFound.isEmpty()) {
			response.setSuccess(false);
	        response.setMessage("No cart found for the user!!");
	        return ResponseEntity.ok().body(response);
		}
		
		CartResponseDTO cartDto = commonService.convertCartToCartResponseDTO(cartFound.get());
		
		if(cartDto.getCartItemCount() == 0) {
			response.setSuccess(false);
	        response.setMessage("No product added to the cart yet!!");
	        return ResponseEntity.ok().body(response);
		}
		
		response.setSuccess(true);
		response.setMessage(CommonConstant.CART_RETRIEVED_SUCCESS_FOR_USER);
		response.setData(cartDto);
		
		return ResponseEntity.ok().body(response);	
	}

	public ResponseEntity<?> addItemToCart(String userExternalId, String productExternalId, Integer quantity) {
		Response<CartResponseDTO> response = new Response<>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		Product product = commonService.findProductByExternalId(productExternalId);
		
		if(product == null) {
			response.setSuccess(false);
			response.setMessage("Product not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		if(product.getStock() <= 0) {
			response.setSuccess(false);
			response.setMessage("Product not available at the moment!!");
			return ResponseEntity.ok().body(response);
		}
		
		Optional<Cart> cartFound = cartRepo.findByUser(user);
		Cart cart = null;
		if(cartFound.isEmpty()) {
			cart = new Cart();
			cart.setExternalId(commonService.generateExternalIdForEntities("CRT"));
			cart.setUser(user);
			cart.setCartItem(new ArrayList<>());
		}else {
			cart = cartFound.get();
		}
		
		 Optional<CartItem> existingCartItem = cart.getCartItem().stream()
				 .filter(item -> item.getProduct().getEntityId().equals(product.getEntityId())).findFirst();
		 
		 CartItem cartItem;
		 
		 if(existingCartItem.isPresent()) {
			 cartItem = existingCartItem.get();
			 cartItem.setQuantity(cartItem.getQuantity() + quantity);
		 } else {
			 cartItem = new CartItem();
			 cartItem.setExternalId(commonService.generateExternalIdForEntities("CRTITM"));
			 cartItem.setCart(cart);
			 cartItem.setProduct(product);
			 cartItem.setQuantity(quantity);
			 cart.getCartItem().add(cartItem);
		 }
		 
		 cartRepo.save(cart);
		 
		 response.setSuccess(true);
		 response.setMessage("Item added to cart!!");
		 response.setData(commonService.convertCartToCartResponseDTO(cart));
		
		return ResponseEntity.ok().body(response);	
	}
	
	
	public ResponseEntity<?> clearCart(String userExternalId){
		
		Response<Cart> response = new Response<Cart>();
		
		User user = commonService.findUserByExternalId(userExternalId);
		
		if(user == null) {
			response.setSuccess(false);
			response.setMessage("User not found!!");
			return ResponseEntity.ok().body(response);
		}
		
		Optional<Cart> cartFound = cartRepo.findByUser(user);
		
		if(cartFound.isEmpty()) {
			response.setSuccess(false);
	        response.setMessage("No cart found for the user!!");
	        return ResponseEntity.ok().body(response);
		}
		
		Cart cart = cartFound.get();
		
		cart.getCartItem().clear();
		
		cartRepo.save(cart);
		
		response.setSuccess(true);
		response.setMessage("Cart is cleared!!");
		response.setData(cart);
			
		return ResponseEntity.ok().body(response);
		
	}
}
