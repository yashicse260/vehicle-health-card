package com.MSIL.VehicleHealthCard.service.Impl;

import com.MSIL.VehicleHealthCard.DTOs.request.*;
import com.MSIL.VehicleHealthCard.DTOs.response.*;
import com.MSIL.VehicleHealthCard.DTOs.response.items.*;
import com.MSIL.VehicleHealthCard.repository.VehicleHealthCardRepository;
import com.MSIL.VehicleHealthCard.service.VehicleHealthCardService;
import com.MSIL.VehicleHealthCard.utils.AESUtil;
import com.MSIL.VehicleHealthCard.utils.JwtUtil;
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
    private final AESUtil aesUtil;
    private final JwtUtil jwtUtil;

    @Autowired
    public VehicleHealthCardServiceImpl(VehicleHealthCardRepository repository,
                                        VehicleHealthCardMapperUtil mapperUtil, AESUtil aesUtil,
                                        JwtUtil jwtUtil) {

        this.repository = repository;
        this.mapperUtil = mapperUtil;
        this.aesUtil = aesUtil;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Validates the user login credentials by invoking the repository method {@code validateLogin}.
     * <p>
     * This method checks the provided user ID and password against the database using the repository.
     * It constructs a {@link LoginResponse} object based on the result returned by the stored procedure.
     * If the repository returns {@code null}, an error response is generated.
     * </p>
     *
     * <b>Response Details:</b>
     * <ul>
     *   <li>{@code errorCode} - Indicates success (0) or failure (non-zero).</li>
     *   <li>{@code errorMessage} - Provides details about the error or success message.</li>
     *   <li>{@code status} - Either {@code STATUS_SUCCESS} or {@code STATUS_FAILURE} based on {@code errorCode}.</li>
     *   <li>{@code userId} - The user ID from the request.</li>
     * </ul>
     *
     * @param request the {@link LoginRequest} containing user ID and password
     * @return a {@link LoginResponse} object with validation status, error code, and message
     */


    @Override
    public LoginResponse validateLogin(LoginRequest request) {
        LoginResponse response = new LoginResponse();
        String decryptedPassword;

        try {
            // Decrypt the password using AESUtil
            decryptedPassword = aesUtil.decrypt(request.getPassword());
        } catch (Exception e) {
            logger.error("Password decryption failed for userId {}", request.getUserId(), e);
            response.setErrorCode(-1);
            response.setErrorMessage("Invalid encrypted password");
            response.setStatus(STATUS_FAILURE);
            response.setUserId(request.getUserId());
            return response;
        }

        // Call repository with decrypted password
        Map<String, Object> result = repository.validateLogin(request.getUserId(), decryptedPassword);

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

        // Generate JWT and Refresh Token on successful login
        if (response.getErrorCode() == 0) {
            String jwtToken = jwtUtil.generateToken(request.getUserId());
            String refreshToken = jwtUtil.generateRefreshToken(request.getUserId());
            response.setJwtToken(jwtToken);
            response.setRefreshToken(refreshToken);
        }

        return response;
    }

    /**
     * Retrieves a list of Job Cards for the specified user by invoking the repository method {@code getJCList}.
     * <p>
     * This method calls the database stored procedure to fetch job card details associated with the given user ID.
     * It constructs a {@link JCListResponse} object containing the error code, error message, status, and a list of job cards.
     * If the repository returns {@code null}, an error response is generated.
     * </p>
     *
     * <b>Response Details:</b>
     * <ul>
     *   <li>{@code errorCode} - Indicates success (0) or failure (non-zero).</li>
     *   <li>{@code errorMessage} - Provides details about the error or success message.</li>
     *   <li>{@code status} - Either {@code STATUS_SUCCESS} or {@code STATUS_FAILURE} based on {@code errorCode}.</li>
     *   <li>{@code jobCards} - A list of job card details mapped from {@code JobCardInfo} objects.</li>
     * </ul>
     *
     * @param request the {@link JCListRequest} containing the user ID for which job cards need to be retrieved
     * @return a {@link JCListResponse} object with job card details and validation status
    **/
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

    /**
     * Retrieves detailed information about a specific Job Card by invoking the repository method {@code getJCDetails}.
     * <p>
     * This method calls the database stored procedure to fetch vehicle health details for the given job card identifiers.
     * It constructs a {@link JCDetailsResponse} object containing:
     * <ul>
     *   <li>Error code and message indicating success or failure</li>
     *   <li>Status based on the error code</li>
     *   <li>Overall remarks for the job card</li>
     *   <li>A list of vehicle health details mapped from {@code VehicleHealthDetail} objects</li>
     * </ul>
     * If the repository returns {@code null}, an error response is generated.
     * </p>
     *
     * @param request the {@link JCDetailsRequest} containing identifiers such as parent group, dealer map code,
     *                location code, company FA, and repair order number
     * @return a {@link JCDetailsResponse} object with job card details, overall remarks, and validation status
     */
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

    /**
     * Retrieves master data related to vehicle health systems by invoking the repository method {@code getMasterData}.
     * <p>
     * This method calls the database stored procedure to fetch hierarchical master data for vehicle systems,
     * subsystems, and sub-subsystems based on the provided Product Model Code (PMC).
     * It constructs a {@link MasterDataResponse} object containing:
     * <ul>
     *   <li>Error code and message indicating success or failure</li>
     *   <li>Status based on the error code</li>
     *   <li>Lists of systems, subsystems, and sub-subsystems mapped from the respective result sets</li>
     * </ul>
     * If the repository returns {@code null}, an error response is generated.
     * </p>
     *
     * @param request the {@link MasterDataRequest} containing the PMC (Product Model Code) used to retrieve master data
     * @return a {@link MasterDataResponse} object with system hierarchy details and validation status
     */
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

    /**
     * Updates the details of a Job Card by invoking the repository method {@code updateJCDetails}.
     * <p>
     * This method prepares the required parameters, including parent group, dealer mapping code, location code,
     * company FA, repair order number, overall remarks, and lists of vehicle inspections and demanded repairs.
     * These lists are expected to be mapped to Oracle ARRAY types in the repository layer.
     * It then calls the stored procedure to update the job card details and constructs an {@link UpdateJCResponse}
     * based on the result.
     * </p>
     *
     * <b>Response Details:</b>
     * <ul>
     *   <li>{@code errorCode} - Indicates success (0) or failure (non-zero).</li>
     *   <li>{@code errorMessage} - Provides details about the error or success message.</li>
     *   <li>{@code status} - Either {@code STATUS_SUCCESS} or {@code STATUS_FAILURE} based on {@code errorCode}.</li>
     * </ul>
     *
     * @param request the {@link UpdateJCRequest} containing job card identifiers, overall remarks,
     *                and lists of vehicle inspections and demanded repairs
     * @return an {@link UpdateJCResponse} object with update status, error code, and message
     */
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
