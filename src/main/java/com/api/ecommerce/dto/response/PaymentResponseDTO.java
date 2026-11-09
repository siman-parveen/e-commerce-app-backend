package com.api.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentResponseDTO {
	
	private String transactionId;
	private String paymentMethod;
	private String paymentStatus;
	private BigDecimal totalAmount;
	private String orderExternalId;
	private LocalDateTime transactionDate;
	
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
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderExternalId() {
		return orderExternalId;
	}
	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

}
