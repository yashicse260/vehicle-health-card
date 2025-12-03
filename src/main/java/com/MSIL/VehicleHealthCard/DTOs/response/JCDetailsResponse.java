package com.MSIL.VehicleHealthCard.DTOs.response;

import com.MSIL.VehicleHealthCard.DTOs.response.items.VehicleHealthDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response DTO for Job Card details.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JCDetailsResponse extends BaseResponse {

    /**
     * Overall remarks for the job card.
     */
    private String overallRemarks;

    /**
     * List of vehicle health details.
     */
    private List<VehicleHealthDetail> details;
}
