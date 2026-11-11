package com.api.ecommerce.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDTO {

    @Size(max = 50, message = "Username must not exceed 50 characters")
    @Schema(description = "User's display or login name", nullable = true)
    private String userName;

    @Email(message = "Invalid email address")
    @Schema(description = "User's email address", nullable = true)
    private String emailId;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number")
    @Nullable
    @Schema(description = "Phone number in E.164 format", nullable = true)
    private String phoneNumber;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
