package com.MSIL.VehicleHealthCard.DTOs.response.items;

import lombok.Data;

/**
 * DTO representing Job Card information.
 */
@Data
public class JobCardInfo {

    /** Registration number of the vehicle. */
    private String regNum;

    /** Vehicle Identification Number. */
    private String vin;

    /** Parent group identifier. */
    private String parentGroup;

    /** Dealer map code. */
    private String dealerMapCd;

    /** Location code. */
    private String locCd;

    /** Company FA code. */
    private String compFa;

    /** RO number. */
    private String roNum;

    /** Service code. */
    private String srvCd;

    /** RO date (consider LocalDate if possible). */
    private String roDate;

    /** Close date (consider LocalDate if possible). */
    private String closeDate;

    /** Service advisor. */
    private String saAdv;

    /** Vehicle model. */
    private String model;

    /** Workshop code. */
    private String workshopCd;

    /** Dealer name. */
    private String dealerName;

    /** Customer name. */
    private String customerName;

    /** Odometer reading. */
    private String odometer;

    /** Vehicle Health Card status (Y/N). */
    private String vhcYn;

    /** Vehicle Health Card status (Y/N). */
    private String srvSdvName;
}
