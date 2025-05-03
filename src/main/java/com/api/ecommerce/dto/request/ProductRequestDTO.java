package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequestDTO {
	
	@NotBlank(message = "Product External ID is required")
	@Schema(description = "Unique identifier for the product")
	private String productExternalId;
	
	@Size(max = 100, message = "Product name must not exceed 100 characters")
	@Schema(description = "Name of the product", nullable = true)
	private String productName;
	
	@Size(max = 2500, message = "Description must not exceed 2500 characters")
	@Schema(description = "Product description", nullable = true)
	private String description;
	
	@Min(value = 0, message = "Stock must be zero or positive")
	@Schema(description = "Available stock count", nullable = true)
	private Integer stock;
	
	@Positive(message = "Price must be greater than 0")
	@Schema(description = "Price of the product", nullable = true)
	private Double price;
	
	@Min(value = 0, message = "Discount must be between 0 and 100")
	@Max(value = 100, message = "Discount must be between 0 and 100")
	@Schema(description = "Optional discount percentage (0-100)", nullable = true)
	private Integer discountPercentage;
	
	public ProductRequestDTO() {}

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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Integer discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	
}
