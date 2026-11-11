package com.api.ecommerce.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponseDTO {
	
	private String cartExternalId;
	private String userExternalId;
	private List<CartItemResponseDTO> cartItemResponseDto;
	private int cartItemCount;
	private BigDecimal totalAmount;

}
