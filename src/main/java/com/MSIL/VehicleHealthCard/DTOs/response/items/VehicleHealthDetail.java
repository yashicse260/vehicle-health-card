package com.MSIL.VehicleHealthCard.DTOs.response.items;

import lombok.Data;

/**
 * DTO representing vehicle health detail information.
 */
@Data
public class VehicleHealthDetail {

    /** Vehicle system code. */
    private String vehSys;

    /** Vehicle sub-system code. */
    private String vehSubSys;

    /** Vehicle sub-sub-system code. */
    private String vehSubSubSys;

    /** Action code for the health detail. */
    private String actionCode;

    /** Status of the health detail. */
    private String status;

    /** Remarks for the health detail. */
    private String remarks;

    /** Minimum value for the health parameter. */
    private String minValue;

    /** Maximum value for the health parameter. */
    private String maxValue;

    /** Standard value for the health parameter. */
    private String standValue;

    /** Variance percentage for the health parameter. */
    private String variancePer;

    /** Measured value for the health parameter. */
    private String measuredValue;

    private String svarCd ;

}
