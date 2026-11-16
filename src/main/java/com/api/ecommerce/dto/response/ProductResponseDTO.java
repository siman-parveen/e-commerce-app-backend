package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
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
}
