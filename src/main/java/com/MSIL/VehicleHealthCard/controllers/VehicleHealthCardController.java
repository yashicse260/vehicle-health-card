
package com.MSIL.VehicleHealthCard.controllers;

import com.MSIL.VehicleHealthCard.DTOs.request.*;
import com.MSIL.VehicleHealthCard.DTOs.response.*;
import com.MSIL.VehicleHealthCard.service.VehicleHealthCardService;
import com.MSIL.VehicleHealthCard.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("NullableProblems")
@RestController
@RequestMapping(path = "/vhc", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class VehicleHealthCardController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleHealthCardController.class);

    private final VehicleHealthCardService service;
    private final ResponseUtil responseUtil;

    public VehicleHealthCardController(VehicleHealthCardService service,
                                       ResponseUtil responseUtil) {
        this.service = service;
        this.responseUtil = responseUtil;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.debug("login request received: {}", request);
        LoginResponse response = service.validateLogin(request);
        return responseUtil.buildResponse(response);
    }

    @PostMapping(path = "/jc-list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JCListResponse> getJCList(@Valid @RequestBody JCListRequest request) {
        logger.debug("getJCList request received");
        JCListResponse response = service.getJCList(request);
        return responseUtil.buildResponse(response);
    }

    @PostMapping(path = "/jc-details", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JCDetailsResponse> getJCDetails(@Valid @RequestBody JCDetailsRequest request) {
        logger.debug("getJCDetails request received");
        JCDetailsResponse response = service.getJCDetails(request);
        return responseUtil.buildResponse(response);
    }

    @PostMapping(path = "/master-data", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MasterDataResponse> getMasterData(@Valid @RequestBody MasterDataRequest request) {
        logger.debug("getMasterData request received");
        MasterDataResponse response = service.getMasterData(request);
        return responseUtil.buildResponse(response);
    }

    @PostMapping(path = "/update-jc", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateJCResponse> updateJCDetails(@Valid @RequestBody UpdateJCRequest request) {
        logger.debug("updateJCDetails request received");
        UpdateJCResponse response = service.updateJCDetails(request);
        return responseUtil.buildResponse(response);
    }
}
