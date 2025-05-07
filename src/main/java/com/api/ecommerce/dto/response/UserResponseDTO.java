package com.api.ecommerce.dto.response;

public class UserResponseDTO {
	
    private String userExternalId;
    private String userName;
    private String emailId;
    private String phoneNumber;
    private int addressCount;
    private int ordersCount;
    private int cartItemsCount;
    private int ratingsCount;
    
    public UserResponseDTO() {}
    
	public String getUserExternalId() {
		return userExternalId;
	}
	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}
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
	public int getAddressCount() {
		return addressCount;
	}
	public void setAddressCount(int addressCount) {
		this.addressCount = addressCount;
	}
	public int getOrdersCount() {
		return ordersCount;
	}
	public void setOrdersCount(int ordersCount) {
		this.ordersCount = ordersCount;
	}
	public int getCartItemsCount() {
		return cartItemsCount;
	}
	public void setCartItemsCount(int cartItemsCount) {
		this.cartItemsCount = cartItemsCount;
	}
	public int getRatingsCount() {
		return ratingsCount;
	}
	public void setRatingsCount(int ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

}
