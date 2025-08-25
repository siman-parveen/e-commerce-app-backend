package com.api.ecommerce.dto.request;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class OrderRequestDTO {

    @NotNull(message = "Total amount is required")
    @Positive(message = "Total amount must be greater than 0")
    @Schema(description = "Total order amount before deductions")
    private BigDecimal totalAmount;

    @PositiveOrZero(message = "Deductions must be zero or positive")
    @Schema(description = "Total deduction amount applied")
    private BigDecimal deductions;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Phone number must be valid E.164 format")
    @Schema(description = "Customer's contact phone number")
    private String phoneNumber;

    @NotBlank(message = "Delivery address External ID is required")
    @Schema(description = "External ID of the Shipping address for theString order")
    private String deliveryAddressExternalId;

    @NotBlank(message = "User External ID is required")
    @Schema(description = "External ID of the user placing the order")
    private String userExternalId;

    @NotEmpty(message = "Order must contain at least one item")
    @Valid
    @Schema(description = "List of items in the order")
    private List<ProductQuantityRequestDTO> orderItems;

	public OrderRequestDTO() {}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getDeductions() {
		return deductions;
	}

	public void setDeductions(BigDecimal deductions) {
		this.deductions = deductions;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDeliveryAddressExternalId() {
		return deliveryAddressExternalId;
	}

	public void setDeliveryAddressExternalId(String deliveryAddressExternalId) {
		this.deliveryAddressExternalId = deliveryAddressExternalId;
	}

	public String getUserExternalId() {
		return userExternalId;
	}

	public void setUserExternalId(String userExternalId) {
		this.userExternalId = userExternalId;
	}

	public List<ProductQuantityRequestDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<ProductQuantityRequestDTO> orderItems) {
		this.orderItems = orderItems;
	}

}
