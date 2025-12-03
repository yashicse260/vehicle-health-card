package com.MSIL.VehicleHealthCard.DTOs.response.items;

import lombok.Data;

/**
 * DTO representing sub-system information.
 */
@Data
public class SubSystemInfo {

    /** Code for the sub-system. */
    private String listCode;

    /** Description for the sub-system. */
    private String listDesc;

    /** Lift information for the sub-system. */
    private String lift;
}
