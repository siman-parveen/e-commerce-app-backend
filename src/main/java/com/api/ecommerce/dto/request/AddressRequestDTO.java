package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressRequestDTO {

    @Size(max = 10, message = "Address Name cannot exceed 10 characters")
    @Schema(description = "Name of the address")
    private String addressName;

    @Size(max = 256, message = "Address Line cannot exceed 256 characters")
    @Schema(description = "Street address or detailed address line")
    private String addressLine;

    @Size(max = 100, message = "City name cannot exceed 100 characters")
    @Schema(description = "City name")
    private String city;

    @Size(max = 100, message = "State name cannot exceed 100 characters")
    @Schema(description = "State name")
    private String state;

    @Pattern(regexp = "^[0-9]{5,6}$", message = "Pincode must be 5 or 6 digits")
    @Schema(description = "Postal code / ZIP")
    private String pinCode;

    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    @Schema(description = "Country name")
    private String country;
    
    @Schema(description = "User External Id")
    private String userExternalId;
	
}
