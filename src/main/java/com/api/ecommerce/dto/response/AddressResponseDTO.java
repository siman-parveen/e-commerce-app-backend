package com.api.ecommerce.dto.response;

import lombok.Data;

@Data
public class AddressResponseDTO {
	
    private String addressExternalId;
    private String addressName;
    private String addressLine;
    private String city;
    private String state;
    private String pinCode;
    private String country;

}
