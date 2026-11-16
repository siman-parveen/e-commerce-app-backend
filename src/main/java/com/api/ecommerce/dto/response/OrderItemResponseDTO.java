package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderItemResponseDTO {
	
	private String orderItemExternalId;
	private String productName;
	private int quantity;
	private BigDecimal productPrice;
	private BigDecimal discountAmount;
	private LocalDateTime deliveredDate;
}
