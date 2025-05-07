package com.api.ecommerce.dto.response;

public class PaymentResponseDTO {
	
	private String transactionId;
	private String paymentMethod;
	private String paymentStatus;
	private double totalAmount;
	private String orderExternalId;
	
	public PaymentResponseDTO() {}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderExternalId() {
		return orderExternalId;
	}
	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

}
