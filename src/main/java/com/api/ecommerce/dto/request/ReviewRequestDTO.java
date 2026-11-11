package com.api.ecommerce.dto.request;

import lombok.Data;

@Data
public class ReviewRequestDTO {
	
	private String userExternalId;
	private String productExternalId;
	private int rating;
	private String comment;
}
