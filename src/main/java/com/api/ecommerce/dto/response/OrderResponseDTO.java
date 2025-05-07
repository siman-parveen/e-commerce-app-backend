package com.api.ecommerce.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
	
	private String orderExternalId;
	private String personName;
	private String address;
	private String phoneNumber;
	private String emailID;
	private int orderItemCount;
	private List<OrderItemResponseDTO> orderItems;
	private double totalCost;
	private boolean isPaid;
	private PaymentResponseDTO payment;
	private LocalDateTime orderDate;
	
	public OrderResponseDTO() {}
	
	public String getOrderExternalId() {
		return orderExternalId;
	}
	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public int getOrderItemCount() {
		return orderItemCount;
	}
	public void setOrderItemCount(int orderItemCount) {
		this.orderItemCount = orderItemCount;
	}
	public List<OrderItemResponseDTO> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemResponseDTO> orderItems) {
		this.orderItems = orderItems;
	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public PaymentResponseDTO getPayment() {
		return payment;
	}
	public void setPayment(PaymentResponseDTO payment) {
		this.payment = payment;
	}
	public LocalDateTime getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	
	

	

}
