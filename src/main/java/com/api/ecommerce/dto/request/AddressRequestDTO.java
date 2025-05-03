package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AddressRequestDTO {
	
	@NotBlank(message = "Address External ID is required")
    @Schema(description = "Unique external ID of the address")
    private String addressExternalId;

    @Size(max = 10, message = "Address Name cannot exceed 10 characters")
    @Schema(description = "Name of the address", nullable = true)
    private String addressName;

    @Size(max = 256, message = "Address Line cannot exceed 256 characters")
    @Schema(description = "Street address or detailed address line", nullable = true)
    private String addressLine;

    @Size(max = 100, message = "City name cannot exceed 100 characters")
    @Schema(description = "City name", nullable = true)
    private String city;

    @Size(max = 100, message = "State name cannot exceed 100 characters")
    @Schema(description = "State name", nullable = true)
    private String state;

    @Pattern(regexp = "^[0-9]{5,6}$", message = "Pincode must be 5 or 6 digits")
    @Schema(description = "Postal code / ZIP", nullable = true)
    private String pincode;

    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    @Schema(description = "Country name", nullable = true)
    private String country;
	
	public AddressRequestDTO() {}

	public String getAddressExternalId() {
		return addressExternalId;
	}

	public void setAddressExternalId(String addressExternalId) {
		this.addressExternalId = addressExternalId;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
