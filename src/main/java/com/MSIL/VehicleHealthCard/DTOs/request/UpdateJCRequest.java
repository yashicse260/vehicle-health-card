package com.MSIL.VehicleHealthCard.DTOs.request;

import com.MSIL.VehicleHealthCard.DTOs.request.items.VHCInspection;
import lombok.Data;
import java.util.List;

/**
 * DTO for updating Job Card details.
 */
@Data
public class UpdateJCRequest {
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

    /**
     * Overall remarks for the job card.
     */
    private String overallRemarks;

    /**
     * List of vehicle health inspections.
     */
    private List<VHCInspection> vhcInspections;

    /**
     * List of demanded repairs.
     * TODO: Update type based on stored procedure definition.
     */
    private List<String> demandedRepairs;
}
