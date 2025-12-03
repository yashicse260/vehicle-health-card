package com.MSIL.VehicleHealthCard.DTOs.request;

import lombok.Data;

/**
 * DTO for requesting Job Card list for a user.
 */
@Data
public class JCListRequest {
    /**
     * User identifier.
     */
    private String userId;
}
