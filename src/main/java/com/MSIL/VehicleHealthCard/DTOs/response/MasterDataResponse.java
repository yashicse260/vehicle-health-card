
package com.MSIL.VehicleHealthCard.DTOs.response;

import com.MSIL.VehicleHealthCard.DTOs.response.items.SubSubSystemInfo;
import com.MSIL.VehicleHealthCard.DTOs.response.items.SubSystemInfo;
import com.MSIL.VehicleHealthCard.DTOs.response.items.SystemInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response DTO for Master Data API.
 * <p>
 * This class extends {@link BaseResponse} to include common response fields
 * such as status and error codes, and provides hierarchical master data
 * required for Vehicle Health Card operations.
 * </p>
 *
 * <h3>Structure:</h3>
 * <ul>
 *   <li>{@code systems} - Top-level system categories (e.g., AC, Engine).</li>
 *   <li>{@code subSystems} - Subsystems under each system (e.g., Brake fluid, Air filter).</li>
 *   <li>{@code subSubSystems} - Detailed components under each subsystem (e.g., Front Left Brake Pad Thickness).</li>
 * </ul>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MasterDataResponse extends BaseResponse {

    /**
     * List of top-level systems in the vehicle.
     * Each {@link SystemInfo} contains a code and description (e.g., AC → Air Conditioner).
     */
    private List<SystemInfo> systems;

    /**
     * List of subsystems under each system.
     * Each {@link SubSystemInfo} includes details like code, description, and lift position.
     */
    private List<SubSystemInfo> subSystems;

    /**
     * List of sub-subsystems (detailed components) under each subsystem.
     * Each {@link SubSubSystemInfo} provides granular details for inspection.
     */
    private List<SubSubSystemInfo> subSubSystems;
}
