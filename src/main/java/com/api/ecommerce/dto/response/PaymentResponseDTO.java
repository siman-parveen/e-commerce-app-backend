package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
	
	private String transactionId;
	private String paymentMethod;
	private String paymentStatus;
	private BigDecimal totalAmount;
	private String orderExternalId;
	private LocalDateTime transactionDate;

}
