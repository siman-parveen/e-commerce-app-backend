package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
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
	
}
