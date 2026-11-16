package com.api.ecommerce.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductRequestDTO {

	public interface CreateValidation {}
	
	@NotBlank(groups = CreateValidation.class, message = "Product name is required")
	@Size(max = 100, message = "Product name must not exceed 100 characters")
	@Schema(description = "Name of the product; required on create, optional on patch", nullable = true)
	private String productName;
	
	@NotBlank(groups = CreateValidation.class, message = "Product description is required")
	@Size(max = 2500, message = "Description must not exceed 2500 characters")
	@Schema(description = "Product description; required on create, optional on patch", nullable = true)
	private String description;
	
	@NotNull(groups = CreateValidation.class, message = "Stock is required")
	@Min(value = 0, message = "Stock must be zero or positive")
	@Schema(description = "Available stock count; required on create, optional on patch", nullable = true)
	private Integer stock;
	
	@NotNull(groups = CreateValidation.class, message = "Price is required")
	@Positive(message = "Price must be greater than 0")
	@Schema(description = "Price of the product; required on create, optional on patch", nullable = true)
	private BigDecimal price;
	
	@Min(value = 0, message = "Discount must be between 0 and 100")
	@Max(value = 100, message = "Discount must be between 0 and 100")
	@Schema(description = "Optional discount percentage (0-100)", nullable = true)
	private Integer discountPercentage;

}
