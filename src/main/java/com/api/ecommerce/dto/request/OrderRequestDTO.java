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
import lombok.Data;

@Data
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
    
    @NotEmpty(message = "Order receiver name is required")
    @Schema(description = "Name of the person to which the order is delivered")
    private String orderReceiverName;
}
