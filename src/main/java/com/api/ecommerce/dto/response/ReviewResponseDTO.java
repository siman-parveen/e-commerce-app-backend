package com.api.ecommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewResponseDTO {
	
	private String reviewExternalId;
	private String userExternalId;
	private String productExternalId;
	private String productName;
	private String customerName;
	private int rating;
	private String comment;
	private LocalDateTime reviewedAt;
}
