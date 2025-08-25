package com.api.ecommerce.dto.response;

import java.math.BigDecimal;

public class ProductResponseDTO {
	
	private String productExternalId;
	private String productName;
	private String description;
	private int stock;
	private BigDecimal price;
	private double totalRating;
	private int discountPercentage;
	
	private int orderedCount;
	private int ratingsCount;
	
	public ProductResponseDTO() {}
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public double getTotalRating() {
		return totalRating;
	}
	public void setTotalRating(double totalRating) {
		this.totalRating = totalRating;
	}
	public int getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public int getOrderedCount() {
		return orderedCount;
	}
	public void setOrderedCount(int orderedCount) {
		this.orderedCount = orderedCount;
	}
	public int getRatingsCount() {
		return ratingsCount;
	}
	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}
	
}
