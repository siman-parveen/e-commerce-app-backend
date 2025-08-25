package com.api.ecommerce.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class CartResponseDTO {
	
	private String cartExternalId;
	private String userExternalId;
	private List<CartItemResponseDTO> cartItemResponseDto;
	private int cartItemCount;
	private BigDecimal totalAmount;
	
	public CartResponseDTO() {}
	
	public String getCartExternalId() {
		return cartExternalId;
	}
	public void setCartExternalId(String cartExternalId) {
		this.cartExternalId = cartExternalId;
	}
	public String getUserExternalId() {
		return userExternalId;
	}
	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}
	public List<CartItemResponseDTO> getCartItemResponseDto() {
		return cartItemResponseDto;
	}
	public void setCartItemResponseDto(List<CartItemResponseDTO> cartItemResponseDto) {
		this.cartItemResponseDto = cartItemResponseDto;
	}
	public int getCartItemCount() {
		return cartItemCount;
	}
	public void setCartItemCount(int cartItemCount) {
		this.cartItemCount = cartItemCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

}
