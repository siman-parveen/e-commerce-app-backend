package com.api.ecommerce.dto.response;

import java.time.LocalDateTime;

public class OrderItemResponseDTO {
	
	private String orderItemExternalId;
	private String productName;
	private int quantity;
	private double productPrice;
	private double discountAmount;
	private boolean isDelivered;
	private LocalDateTime deliveredDate;
	
	public OrderItemResponseDTO() {}

	public String getOrderItemExternalId() {
		return orderItemExternalId;
	}

	public void setOrderItemExternalId(String orderItemExternalId) {
		this.orderItemExternalId = orderItemExternalId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

}
