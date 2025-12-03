
package com.MSIL.VehicleHealthCard.DTOs.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response DTO for Login API.
 * <p>
 * This class extends {@link BaseResponse} to include common response fields
 * such as status and error codes, and adds login-specific details like
 * user identifier and authentication tokens.
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends BaseResponse {

    /**
     * The unique identifier of the user who successfully logged in.
     */
    private String userId;

    /**
     * JWT Access Token for authenticating subsequent API requests.
     * <p>
     * This token is short-lived (e.g., 15 minutes) and should be sent
     * in the Authorization header as: {@code Bearer <token>}.
     * </p>
     */
    private String jwtToken;

    /**
     * Refresh Token for obtaining a new JWT Access Token without re-login.
     * <p>
     * This token is long-lived (e.g., 7 days) and should be stored securely
     * by the client. It is used in the refresh API to renew the access token.
     * </p>
     */
    private String refreshToken;
}
