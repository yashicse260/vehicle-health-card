package com.MSIL.VehicleHealthCard.service.Impl;

import com.MSIL.VehicleHealthCard.DTOs.request.*;
import com.MSIL.VehicleHealthCard.DTOs.response.*;
import com.MSIL.VehicleHealthCard.DTOs.response.items.*;
import com.MSIL.VehicleHealthCard.repository.VehicleHealthCardRepository;
import com.MSIL.VehicleHealthCard.service.VehicleHealthCardService;
import com.MSIL.VehicleHealthCard.utils.VehicleHealthCardMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
@Service
public class VehicleHealthCardServiceImpl implements VehicleHealthCardService {

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_FAILURE = "FAILURE";
    private final Logger logger = LoggerFactory.getLogger(VehicleHealthCardServiceImpl.class);

    private final VehicleHealthCardRepository repository;
    private final VehicleHealthCardMapperUtil mapperUtil;

    @Autowired
    public VehicleHealthCardServiceImpl(VehicleHealthCardRepository repository,
                                        VehicleHealthCardMapperUtil mapperUtil) {
        this.repository = repository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public LoginResponse validateLogin(LoginRequest request) {
        Map<String, Object> result = repository.validateLogin(request.getUserId(), request.getPassword());
        LoginResponse response = new LoginResponse();

        if (result == null) {
            logger.error("validateLogin: Repository returned null for userId {}", request.getUserId());
            response.setErrorCode(-1);
            response.setErrorMessage("Internal error");
            response.setStatus(STATUS_FAILURE);
            response.setUserId(request.getUserId());
            return response;
        }

        response.setErrorCode((Integer) result.get("po_err_cd"));
        response.setErrorMessage((String) result.get("po_err_msg"));
        response.setStatus(response.getErrorCode() == 0 ? STATUS_SUCCESS : STATUS_FAILURE);
        response.setUserId(request.getUserId());
        return response;
    }

    @Override
    public JCListResponse getJCList(JCListRequest request) {
        Map<String, Object> result = repository.getJCList(request.getUserId());
        JCListResponse response = new JCListResponse();

        if (result == null) {
            logger.error("getJCList: Repository returned null for userId {}", request.getUserId());
            response.setErrorCode(-1);
            response.setErrorMessage("Internal error");
            response.setStatus(STATUS_FAILURE);
            return response;
        }

        response.setErrorCode((Integer) result.get("po_err_cd"));
        response.setErrorMessage((String) result.get("po_err_msg"));
        response.setStatus(response.getErrorCode() == 0 ? STATUS_SUCCESS : STATUS_FAILURE);

        // Cast to correct type before passing to mapper
        List<JobCardInfo> jcList = (List<JobCardInfo>) result.get("po_jc_list");
        response.setJobCards(mapperUtil.mapJobCardInfoList(jcList));

        return response;
    }

    @Override
    public JCDetailsResponse getJCDetails(JCDetailsRequest request) {
        Map<String, Object> result = repository.getJCDetails(
                request.getParentGroup(),
                request.getDealerMapCd(),
                request.getLocCd(),
                request.getCompFa(),
                request.getRoNum()
        );

        JCDetailsResponse response = new JCDetailsResponse();

        if (result == null) {
            logger.error("getJCDetails: Repository returned null for request {}", request);
            response.setErrorCode(-1);
            response.setErrorMessage("Internal error");
            response.setStatus(STATUS_FAILURE);
            return response;
        }

        response.setErrorCode((Integer) result.get("po_err_cd"));
        response.setErrorMessage((String) result.get("po_err_msg"));
        response.setStatus(response.getErrorCode() == 0 ? STATUS_SUCCESS : STATUS_FAILURE);
        response.setOverallRemarks((String) result.get("PO_OVERALL_REMARKS"));

        List<VehicleHealthDetail> vhcDetails = (List<VehicleHealthDetail>) result.get("PO_JC_VEH_REFCUR");
        response.setDetails(mapperUtil.mapVehicleHealthDetails(vhcDetails));

        return response;
    }

    @Override
    public MasterDataResponse getMasterData(MasterDataRequest request) {
        Map<String, Object> result = repository.getMasterData(request.getPmc());
        MasterDataResponse response = new MasterDataResponse();

        if (result == null) {
            logger.error("getMasterData: Repository returned null for pmc {}", request.getPmc());
            response.setErrorCode(-1);
            response.setErrorMessage("Internal error");
            response.setStatus(STATUS_FAILURE);
            return response;
        }

        response.setErrorCode((Integer) result.get("po_err_cd"));
        response.setErrorMessage((String) result.get("po_err_msg"));
        response.setStatus(response.getErrorCode() == 0 ? STATUS_SUCCESS : STATUS_FAILURE);

        response.setSystems(mapperUtil.mapSystemInfoList((List<SystemInfo>) result.get("PO_VHC_SYS_REFCUR")));
        response.setSubSystems(mapperUtil.mapSubSystemInfoList((List<SubSystemInfo>) result.get("PO_VHC_SUB_SYS_REFCUR")));
        response.setSubSubSystems(mapperUtil.mapSubSubSystemInfoList((List<SubSubSystemInfo>) result.get("PO_VHC_SUB_SUB_SYS_REFCUR")));

        return response;
    }

    @Override
    public UpdateJCResponse updateJCDetails(UpdateJCRequest request) {
        // TODO: Ensure Oracle ARRAY mapping for PN_VHC_INS_ARR and demanded repairs
        Map<String, Object> params = Map.of(
                "PN_PARENT_GROUP", request.getParentGroup(),
                "PN_DEALER_MAP_CD", request.getDealerMapCd(),
                "PN_LOC_CD", request.getLocCd(),
                "PN_COMP_FA", request.getCompFa(),
                "PN_RO_NUM", request.getRoNum(),
                "PN_OVERALL_REMARKS", request.getOverallRemarks(),
                "PN_VHC_INS_ARR", request.getVhcInspections(), // Oracle ARRAY mapping required
                "PN_DEMANDED_REPAIR", request.getDemandedRepairs() // TBD: Oracle type mapping
        );

        Map<String, Object> result = repository.updateJCDetails(params);
        UpdateJCResponse response = new UpdateJCResponse();

        if (result == null) {
            logger.error("updateJCDetails: Repository returned null for request {}", request);
            response.setErrorCode(-1);
            response.setErrorMessage("Internal error");
            response.setStatus(STATUS_FAILURE);
            return response;
        }

        response.setErrorCode((Integer) result.get("po_err_cd"));
        response.setErrorMessage((String) result.get("po_err_msg"));
        response.setStatus(response.getErrorCode() == 0 ? STATUS_SUCCESS : STATUS_FAILURE);
        return response;
    }

}
