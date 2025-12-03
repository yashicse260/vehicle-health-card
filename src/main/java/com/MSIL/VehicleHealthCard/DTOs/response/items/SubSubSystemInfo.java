package com.MSIL.VehicleHealthCard.DTOs.response.items;

import lombok.Data;

/**
 * DTO representing sub-sub-system information.
 */
@Data
public class SubSubSystemInfo {

    /** Code for the sub-sub-system. */
    private String listCode;

    /** Description for the sub-sub-system. */
    private String listDesc;
}
