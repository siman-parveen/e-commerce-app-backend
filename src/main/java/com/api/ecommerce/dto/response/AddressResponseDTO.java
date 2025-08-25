package com.api.ecommerce.dto.response;

public class AddressResponseDTO {
	
    private String addressExternalId;
    private String addressName;
    private String addressLine;
    private String city;
    private String state;
    private String pincode;
    private String country;
    
    public AddressResponseDTO() {}

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
