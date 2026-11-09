package com.api.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductUserBroughtResponseDTO {
	
	private String productExternalId;
	private String productName;
	private String userName;
	private int quantity;
	private BigDecimal price;
	private String ratingExternalId;
	private int rating;
	private String comment;
	private LocalDateTime reviewedAt;
	private String orderExternalId;
	private String orderStatus;
	private LocalDateTime productDeliveryDate;
	
	public ProductUserBroughtResponseDTO() {}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getRatingExternalId() {
		return ratingExternalId;
	}

	public void setRatingExternalId(String ratingExternalId) {
		this.ratingExternalId = ratingExternalId;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getReviewedAt() {
		return reviewedAt;
	}

	public void setReviewedAt(LocalDateTime reviewedAt) {
		this.reviewedAt = reviewedAt;
	}

	public String getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getProductDeliveryDate() {
		return productDeliveryDate;
	}

	public void setProductDeliveryDate(LocalDateTime productDeliveryDate) {
		this.productDeliveryDate = productDeliveryDate;
	}
	
}
