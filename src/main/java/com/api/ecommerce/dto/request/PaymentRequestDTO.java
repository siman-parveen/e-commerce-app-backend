package com.api.ecommerce.dto.request;

import java.math.BigDecimal;

import com.api.ecommerce.entity.PaymentMode;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private BigDecimal totalAmount;

    @NotNull(message = "Payment mode is required")
    @Enumerated(EnumType.STRING)
    @Schema(description = "Mode of payment", allowableValues = {"CASH", "CARD", "CHECK", "UPI", "NETBANKING"})
    private PaymentMode paymentMode;
    
    @NotBlank(message = "Order External ID is required")
    @Schema(description = "External ID of the order")
    private String orderExternalId;

	public PaymentRequestDTO() {}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getOrderExternalId() {
		return orderExternalId;
	}

	public void setOrderExternalId(String orderExternalId) {
		this.orderExternalId = orderExternalId;
	}
	
}
