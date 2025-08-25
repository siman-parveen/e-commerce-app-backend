package com.api.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderItemResponseDTO {
	
	private String orderItemExternalId;
	private String productName;
	private int quantity;
	private BigDecimal productPrice;
	private BigDecimal discountAmount;
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

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public LocalDateTime getDeliveredDate() {
		return deliveredDate;
	}

	public void setDeliveredDate(LocalDateTime deliveredDate) {
		this.deliveredDate = deliveredDate;
	}

}
