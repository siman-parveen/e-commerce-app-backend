package com.api.ecommerce.dto.response;

import java.time.LocalDateTime;

public class ReviewResponseDTO {
	
	private String reviewExternalId;
	private String userExternalId;
	private String productExternalId;
	private String productName;
	private String customerName;
	private int rating;
	private String comment;
	private LocalDateTime reviewedAt;
	
	public ReviewResponseDTO() {}

	public String getReviewExternalId() {
		return reviewExternalId;
	}

	public void setReviewExternalId(String reviewExternalId) {
		this.reviewExternalId = reviewExternalId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public String getUserExternalId() {
		return userExternalId;
	}

	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}

	public String getProductExternalId() {
		return productExternalId;
	}

	public void setProductExternalId(String productExternalId) {
		this.productExternalId = productExternalId;
	}

}
