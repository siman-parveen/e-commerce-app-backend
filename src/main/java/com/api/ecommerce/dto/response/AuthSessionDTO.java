package com.api.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthSessionDTO {
    private String userExternalId;
    private String role;
}
