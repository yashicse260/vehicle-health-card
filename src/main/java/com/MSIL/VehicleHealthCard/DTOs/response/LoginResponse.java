
package com.MSIL.VehicleHealthCard.DTOs.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response DTO for Login API.
 * <p>
 * This class extends {@link BaseResponse} to include common response fields
 * such as status and error codes, and adds login-specific details like
 * user identifier and authentication token.
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends BaseResponse {

    /**
     * The unique identifier of the user who attempted login.
     * <p>
     * This is typically echoed back from the request for reference.
     * </p>
     */
    private String userId;

    /**
     * Authentication token (e.g., JWT or session token).
     * <p>
     * Currently optional and reserved for future enhancements where
     * token-based authentication will be implemented.
     * </p>
     */
    private String token;
}
