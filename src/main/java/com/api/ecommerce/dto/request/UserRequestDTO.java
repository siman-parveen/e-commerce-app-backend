package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @Size(max = 50, message = "Username must not exceed 50 characters")
    @Schema(description = "User's display or login name", nullable = true)
    private String userName;

    @Email(message = "Invalid email address")
    @Schema(description = "User's email address", nullable = true)
    private String emailId;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    @Schema(description = "Phone number in E.164 format", nullable = true)
    private String phoneNumber;

    @Valid
    @Schema(description = "User's address details", nullable = true)
    private AddressRequestDTO address;
	
	public UserRequestDTO() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public AddressRequestDTO getAddress() {
		return address;
	}

	public void setAddress(AddressRequestDTO address) {
		this.address = address;
	}
	
}
