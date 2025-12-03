package com.MSIL.VehicleHealthCard.DTOs.response.items;

import lombok.Data;

/**
 * DTO representing system information.
 */
@Data
public class SystemInfo {

    /** Code for the system. */
    private String listCode;

    /** Description for the system. */
    private String listDesc;
}
