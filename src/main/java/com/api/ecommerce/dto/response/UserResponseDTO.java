package com.api.ecommerce.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseDTO {
	
    private String userExternalId;
    private String userName;
    private String emailId;
    private String phoneNumber;
    private List<AddressResponseDTO> address;
	private int addressCount;
    private int ordersCount;
    private int cartItemsCount;
    private int reviewsCount;

}
