package com.MSIL.VehicleHealthCard.DTOs.response;

import lombok.Data;

/**
 * Base response DTO for API responses.
 * Extend this class for all response types.
 */
@Data
public abstract class BaseResponse {

    /**
     * Status of the response (e.g., SUCCESS, FAILURE).
     * Consider using an enum for strict typing.
     */
    private String status;

    /**
     * Error code for the response.
     */
    private int errorCode;

    /**
     * Error message for the response.
     */
    private String errorMessage;
}
