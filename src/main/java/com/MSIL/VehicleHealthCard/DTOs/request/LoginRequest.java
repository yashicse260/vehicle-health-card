package com.MSIL.VehicleHealthCard.DTOs.request;

import lombok.Data;

/**
 * DTO for login request.
 */
@Data
public class LoginRequest {

    /**
     * User identifier.
     */
    private String userId;

    /**
     * User password.
     */
    private String password;
}
