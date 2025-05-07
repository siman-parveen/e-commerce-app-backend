package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequestDTO {
	
	@NotBlank(message = "Transaction ID is required")
    @Schema(description = "Unique transaction ID for the payment")
    private String transactionId;

    @NotNull(message = "Total amount is required")
    @Positive(message = "Amount must be greater than 0")
    @Schema(description = "Total amount paid")
    private Double totalAmount;

    @NotNull(message = "Payment mode is required")
    @Schema(description = "Mode of payment", allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "UPI", "NET_BANKING", "WALLET"})
    private String paymentMode;

    @NotNull(message = "Payment status is required")
    @Schema(description = "Current status of the payment", allowableValues = {"PENDING", "FAILED", "SUCCESS"})
    private String paymentStatus;

    @NotNull(message = "Order External ID is required")
    @Schema(description = "External ID of the associated order")
    private String orderExternalId;

	public PaymentRequestDTO() {}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}

}
