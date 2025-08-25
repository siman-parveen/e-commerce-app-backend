package com.api.ecommerce.dto.response;

import java.util.List;

public class ProductReviewResponseDTO {
	
	private String productExternalId;
	private String productName;
	private List<ReviewDTO> reviews;
	
	public ProductReviewResponseDTO() {}

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

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}
}
