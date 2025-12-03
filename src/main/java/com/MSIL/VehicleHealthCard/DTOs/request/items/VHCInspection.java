package com.MSIL.VehicleHealthCard.DTOs.request.items;

import lombok.Data;

/**
 * DTO representing a Vehicle Health Card Inspection item.
 */
@Data
public class VHCInspection {
    /**
     * Vehicle System code.
     */
    private String vehSys;

    /**
     * Vehicle Sub-System code.
     */
    private String vehSubSys;

    /**
     * Vehicle Sub-Sub-System code.
     */
    private String vehSubSubSys;

    /**
     * Action code for the inspection.
     * Consider using an enum if values are fixed.
     */
    private String actionCode;

    /**
     * Status of the inspection.
     * Consider using an enum if values are fixed.
     */
    private String status;

    /**
     * Remarks for the inspection.
     */
    private String remarks;

    /**
     * Measured value for the inspection.
     */
    private String measuredValue;
}
