package com.MSIL.VehicleHealthCard.DTOs.request;

import lombok.Data;

/**
 * DTO for requesting Job Card details.
 */
@Data
public class JCDetailsRequest {
    /**
     * Parent group identifier.
     */
    private String parentGroup;

    /**
     * Dealer map code.
     */
    private String dealerMapCd;

    /**
     * Location code.
     */
    private String locCd;

    /**
     * Company FA code.
     */
    private String compFa;

    /**
     * RO number.
     */
    private String roNum;
}
