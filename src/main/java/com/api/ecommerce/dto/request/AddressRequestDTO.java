package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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
    private String pincode;

    @Size(max = 100, message = "Country name cannot exceed 100 characters")
    @Schema(description = "Country name")
    private String country;
    
    @Schema(description = "User External Id")
    private String userExternalId;
	
	public AddressRequestDTO() {}

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

	public String getUserExternalId() {
		return userExternalId;
	}

	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}

//	@Override
//	public String toString() {
//		return "AddressRequestDTO [addressName=" + addressName + ", addressLine=" + addressLine + ", city=" + city
//				+ ", state=" + state + ", pincode=" + pincode + ", country=" + country + ", userExternalId="
//				+ userExternalId + "]";
//	}
	
	
	
}
