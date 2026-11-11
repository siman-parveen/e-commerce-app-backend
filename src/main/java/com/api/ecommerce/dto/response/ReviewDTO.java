package com.api.ecommerce.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
	
	private String userExternalId;
	private String customerName;
	private int rating;
	private String comment;
	private LocalDateTime reviewedAt;
}
