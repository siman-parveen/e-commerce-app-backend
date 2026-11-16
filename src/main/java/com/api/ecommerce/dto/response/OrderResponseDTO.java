package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
	
	private String orderExternalId;
	private String status;
	private AddressResponseDTO address;
	private String customerName;
	private String customerExternalId;
	private int orderItemCount;
	private List<OrderItemResponseDTO> orderItems;
	private BigDecimal totalCost;
	private PaymentResponseDTO payment;
	private LocalDateTime orderDate;
}
