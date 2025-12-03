
package com.MSIL.VehicleHealthCard.DTOs.response;

import com.MSIL.VehicleHealthCard.DTOs.response.items.JobCardInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response DTO for Job Card List API.
 * <p>
 * This class extends {@link BaseResponse} to include common response fields
 * such as status and error codes, and adds a list of job cards retrieved
 * for the given user.
 * </p>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class JCListResponse extends BaseResponse {

    /**
     * List of job cards associated with the user.
     * Each {@link JobCardInfo} contains details like registration number,
     * VIN, dealer information, service details, and other metadata.
     */
    private List<JobCardInfo> jobCards;
}
