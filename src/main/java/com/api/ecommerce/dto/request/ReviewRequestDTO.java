package com.api.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ReviewRequestDTO {
	
	@NotNull(message = "User External ID cannot be null")
	private String userExternalId;

	@NotNull(message = "Product External ID cannot be null")
	private String productExternalId;

	@Min(value = 1, message = "Rating must be between 1 and 5")
	@Max(value = 5, message = "Rating must be between 1 and 5")
	@Schema(description = "Review rating (1 to 5)")
	private int rating;

	@Size(max = 256, message = "Comment cannot exceed 256 characters")
    @Schema(description = "Review comment")
	private String comment;
}
