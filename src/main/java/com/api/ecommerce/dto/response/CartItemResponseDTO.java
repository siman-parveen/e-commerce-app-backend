package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponseDTO {
	
	private String cartItemExternalId;
	private String productExternalId;
	private String productName;
	private BigDecimal price;
	private int quantity;
	private int productStock;
}
