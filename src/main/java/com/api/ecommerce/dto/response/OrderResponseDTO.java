package com.api.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponseDTO {
	
	private String orderExternalId;
	private String status;
	private AddressResponseDTO address;
	private String customerName;
	private String customerExternalId;
	private int orderItemCount;
	private List<OrderItemResponseDTO> orderItems;
	private BigDecimal totalCost;
	private PaymentResponseDTO payment;
	private LocalDateTime orderDate;
	
	public OrderResponseDTO() {}
	
	public String getOrderExternalId() {
		return orderExternalId;
	}
	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}
	public AddressResponseDTO getAddress() {
		return address;
	}
	public void setAddress(AddressResponseDTO address) {
		this.address = address;
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
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerExternalId() {
		return customerExternalId;
	}

	public void setCustomerExternalId(String customerExternalId) {
		this.customerExternalId = customerExternalId;
	}
	
}
