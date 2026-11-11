package com.api.ecommerce.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductReviewResponseDTO {
	
	private String productExternalId;
	private String productName;
	private List<ReviewDTO> reviews;
}
