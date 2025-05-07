package com.api.ecommerce.dto.response;

public class CartItemResponseDTO {
	
	private String cartItemExternalId;
	private String productExternalId;
	private String productName;
	private double totalPrice;
	private int quantity;
	
	public CartItemResponseDTO() {}

	public String getCartItemExternalId() {
		return cartItemExternalId;
	}

	public void setCartItemExternalId(String cartItemExternalId) {
		this.cartItemExternalId = cartItemExternalId;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
