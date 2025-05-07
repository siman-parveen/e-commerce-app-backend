package com.api.ecommerce.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.ecommerce.dto.response.CartItemResponseDTO;
import com.api.ecommerce.service.CartItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user/cart/items")
@Tag(name = "Cart Item API", description = "Operations related to Cart Items of the registered user")
public class CartItemRestController {
	
	@Autowired
	private CartItemService service;
	
//	@Operation(summary = "Retrieve a cart item by its External ID", description = "API returns the cart Item by the External ID")
//	@ApiResponses({
//		@ApiResponse(responseCode = "200", description = "Cart Item retrieved successfully!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
//	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
//	})
//	@GetMapping("/{cartItemExternalId}")
//	public ResponseEntity<?> retrieveCartItems(@PathVariable String cartItemExternalId){
//		return service.retrieveCartItems(cartItemExternalId);
//	}
	
	@Operation(summary = "Add quantity for a user's cart item", description = "API increases the cart item quantity and returns the updated cart item")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cart Item quantity increased", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/{cartItemExternalId}/add")
	public ResponseEntity<?> addQuantityForCartItem(@PathVariable String cartItemExternalId, @RequestBody int quantity){
		return service.addQuantityForCartItem(cartItemExternalId, quantity);
	}
	
	@Operation(summary = "Remove quantity for a user's cart item", description = "API decreases the cart item quantity and returns the updated cart item")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cart Item quantity decreased!!", content = @Content(mediaType = "/application/json", schema = @Schema(implementation = CartItemResponseDTO.class))),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@PatchMapping("/{cartItemExternalId}/remove")
	public ResponseEntity<?> removeQuantityForCartItem(@PathVariable String cartItemExternalId, @RequestBody int quantity){
		return service.removeQuantityForCartItem(cartItemExternalId, quantity);
	}
	
	@Operation(summary = "Delete cart item for the user", description = "API deletes the product from user's cart")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cart Item removed successfully!!", content = @Content(mediaType = "/application/json")),
	    @ApiResponse(responseCode = "500", description = "Call failed on the Server!!")
	})
	@DeleteMapping("/{cartItemExternalId}")
	public ResponseEntity<?> removeCartItem(@PathVariable String cartItemExternalId){
		return service.removeCartItem(cartItemExternalId);
	}

}
