package com.api.ecommerce.dto.response;

import java.math.BigDecimal;

public class CartItemResponseDTO {
	
	private String cartItemExternalId;
	private String productExternalId;
	private String productName;
	private BigDecimal price;
	private int quantity;
	private int productStock;
	
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

}
