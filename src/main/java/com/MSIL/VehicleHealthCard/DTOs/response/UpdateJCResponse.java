
package com.MSIL.VehicleHealthCard.DTOs.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response DTO for Update Job Card API.
 * <p>
 * This class extends {@link BaseResponse} to include common response fields
 * such as status and error codes. It represents the outcome of an update
 * operation on a Job Card, including success or failure details.
 * </p>
 *
 * <h3>Usage:</h3>
 * <ul>
 *   <li>Returned after updating Vehicle Health Card details for a specific Job Card.</li>
 *   <li>Contains status and error information inherited from {@link BaseResponse}.</li>
 *   <li>Can be extended in the future to include additional fields like updated timestamp or confirmation ID.</li>
 * </ul>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateJCResponse extends BaseResponse {

    /**
     * Optional: A confirmation message or identifier for the update operation.
     * <p>
     * Currently not implemented but reserved for future enhancements.
     * </p>
     */
    private String confirmationId;

    /**
     * Optional: Timestamp indicating when the update was successfully applied.
     * <p>
     * Useful for audit logs and tracking changes.
     * </p>
     */
    private String updatedAt;

}
