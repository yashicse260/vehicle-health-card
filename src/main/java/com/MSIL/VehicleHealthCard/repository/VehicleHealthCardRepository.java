
package com.MSIL.VehicleHealthCard.repository;

import com.MSIL.VehicleHealthCard.DTOs.response.items.JobCardInfo;
import com.MSIL.VehicleHealthCard.DTOs.response.items.VehicleHealthDetail;
import com.MSIL.VehicleHealthCard.DTOs.response.items.SystemInfo;
import com.MSIL.VehicleHealthCard.DTOs.response.items.SubSystemInfo;
import com.MSIL.VehicleHealthCard.DTOs.response.items.SubSubSystemInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository class responsible for interacting with Oracle stored procedures
 * for Vehicle Health Card operations. Uses {@link SimpleJdbcCall} for clean and
 * type-safe procedure calls.
 */
@Repository
public class VehicleHealthCardRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constants for parameter names
    private static final String PARAM_USER_ID = "pn_userid";
    private static final String PARAM_PASSWORD = "pn_pwd";
    private static final String PARAM_PARENT_GROUP = "PN_PARENT_GROUP";
    private static final String PARAM_DEALER_MAP_CD = "PN_DEALER_MAP_CD";
    private static final String PARAM_LOC_CD = "PN_LOC_CD";
    private static final String PARAM_COMP_FA = "PN_COMP_FA";
    private static final String PARAM_RO_NUM = "PN_RO_NUM";
    private static final String PARAM_PMC = "PN_PMC";

    // SimpleJdbcCall instances for each stored procedure
    private SimpleJdbcCall spLoginValidate;
    private SimpleJdbcCall spGetJCList;
    private SimpleJdbcCall spGetJCDetails;
    private SimpleJdbcCall spGetMasterData;
    private SimpleJdbcCall spUpdateJCDetails;

    @Autowired
    public VehicleHealthCardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        spLoginValidate = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VHC_LOGIN_VALIDATE");


        spGetJCList = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VHC_GET_JC_LIST")
                .returningResultSet("po_jc_list", (rs, rowNum) -> {
                    JobCardInfo jc = new JobCardInfo();
                    jc.setParentGroup(rs.getString("parent_group"));
                    jc.setDealerMapCd(rs.getString("dealer_map_cd"));
                    jc.setLocCd(rs.getString("loc_cd"));
                    jc.setCompFa(rs.getString("comp_fa"));
                    jc.setRoNum(rs.getString("ro_num"));
                    jc.setRegNum(rs.getString("reg_num"));
                    jc.setVin(rs.getString("vin"));
                    jc.setRoDate(rs.getString("ro_date"));
                    jc.setCloseDate(rs.getString("ACTUAL_DATE"));
                    jc.setSrvCd(rs.getString("SRV_CD"));
                    jc.setSaAdv(rs.getString("SA_ADV"));
                    jc.setModel(rs.getString("MODEL"));
                    jc.setWorkshopCd(rs.getString("WORKSHOP_CD"));
                    jc.setDealerName(rs.getString("DEALER_NAME"));
                    jc.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    jc.setOdometer(rs.getString("ODOMETER"));
                    jc.setVhcYn(rs.getString("VHC_YN"));
                    jc.setSrvSdvName(rs.getString("SRV_SDV_NAME"));
                    return jc;
                });


        spGetJCDetails = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VHC_GET_JC_DTL")
                .returningResultSet("PO_JC_VEH_REFCUR", (rs, rowNum) -> {
                    VehicleHealthDetail detail = new VehicleHealthDetail();
                    detail.setVehSys(rs.getString("VEH_SYS"));
                    detail.setVehSubSys(rs.getString("VEH_SUB_SYS"));
                    detail.setVehSubSubSys(rs.getString("VEH_SUB_SUB_SYS"));
                    detail.setActionCode(rs.getString("ACTION_CODE"));
                    detail.setStatus(rs.getString("STATUS"));
                    detail.setRemarks(rs.getString("REMARKS"));
                    detail.setMinValue(String.valueOf(rs.getBigDecimal("MIN_VALUE")));
                    detail.setMaxValue(String.valueOf(rs.getBigDecimal("MAX_VALUE")));
                    detail.setStandValue(String.valueOf(rs.getBigDecimal("STAND_VALUE")));
                    detail.setVariancePer(String.valueOf(rs.getBigDecimal("VARIANCE_PER")));
                    detail.setMeasuredValue(String.valueOf(rs.getBigDecimal("MEASURED_VALUE")));
                    return detail;
                });

        spGetMasterData = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VHC_SYS_LIST")
                .returningResultSet("PO_VHC_SYS_REFCUR", (rs, rowNum) -> {
                    SystemInfo sys = new SystemInfo();
                    sys.setListCode(rs.getString("LIST_CODE"));
                    sys.setListDesc(rs.getString("LIST_DESC"));
                    return sys;
                })
                .returningResultSet("PO_VHC_SUB_SYS_REFCUR", (rs, rowNum) -> {
                    SubSystemInfo subSys = new SubSystemInfo();
                    subSys.setListCode(rs.getString("LIST_CODE"));
                    subSys.setListDesc(rs.getString("LIST_DESC"));
                    subSys.setLift(rs.getString("LIFT"));
                    return subSys;
                })
                .returningResultSet("PO_VHC_SUB_SUB_SYS_REFCUR", (rs, rowNum) -> {
                    SubSubSystemInfo subSubSys = new SubSubSystemInfo();
                    subSubSys.setListCode(rs.getString("LIST_CODE"));
                    subSubSys.setListDesc(rs.getString("LIST_DESC"));
                    return subSubSys;
                });

        spUpdateJCDetails = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("SP_VHC_UPDATE_JC_DTL");
    }

    /**
     * Validates the login credentials of a user by invoking the stored procedure {@code SP_VHC_LOGIN_VALIDATE}.
     * <p>
     * This method calls the database procedure to check whether the provided user ID and password are valid.
     * It returns a map containing the output parameters and any result sets returned by the procedure.
     * </p>
     *
     * @param userId   the unique identifier of the user attempting to log in
     * @param password the password associated with the user ID
     * @return a {@link Map} containing the stored procedure's output parameters and result sets
     * @throws RuntimeException if an error occurs while executing the stored procedure
     */
    public Map<String, Object> validateLogin(String userId, String password) {
        try {
            return spLoginValidate.execute(Map.of(PARAM_USER_ID, userId, PARAM_PASSWORD, password));
        } catch (Exception e) {
            throw new RuntimeException("Error executing SP_VHC_LOGIN_VALIDATE", e);
        }
    }

    /**
     * Retrieves the list of Job Cards associated with the specified user by invoking the stored procedure {@code SP_VHC_GET_JC_LIST}.
     * <p>
     * This method calls the database procedure to fetch job card details such as registration number, VIN, dealer information,
     * and other related attributes for the given user ID. The result is returned as a map containing output parameters and
     * a result set mapped to {@code JobCardInfo} objects.
     * </p>
     *
     * @param userId the unique identifier of the user whose job card list needs to be retrieved
     * @return a {@link Map} containing the stored procedure's output parameters and the job card list result set
     * @throws RuntimeException if an error occurs while executing the stored procedure
     */
    public Map<String, Object> getJCList(String userId) {
        try {
            return spGetJCList.execute(Map.of(PARAM_USER_ID, userId));
        } catch (Exception e) {
            throw new RuntimeException("Error executing SP_VHC_GET_JC_LIST", e);
        }
    }

    /**
     * Retrieves detailed information about a specific Job Card by invoking the stored procedure {@code SP_VHC_GET_JC_DTL}.
     * <p>
     * This method fetches vehicle health details such as system, subsystem, status, remarks, and measurement values
     * for the given job card identifiers. The result is returned as a map containing output parameters and
     * a result set mapped to {@code VehicleHealthDetail} objects.
     * </p>
     *
     * @param parentGroup  the parent group identifier associated with the job card
     * @param dealerMapCd  the dealer mapping code for the job card
     * @param locCd        the location code where the job card was created
     * @param compFa       the company FA (Financial Account) code related to the job card
     * @param roNum        the repair order number for the job card
     * @return a {@link Map} containing the stored procedure's output parameters and the job card details result set
     * @throws RuntimeException if an error occurs while executing the stored procedure
     */
    public Map<String, Object> getJCDetails(String parentGroup, String dealerMapCd, String locCd, String compFa, String roNum) {
        try {
            return spGetJCDetails.execute(Map.of(
                    PARAM_PARENT_GROUP, parentGroup,
                    PARAM_DEALER_MAP_CD, dealerMapCd,
                    PARAM_LOC_CD, locCd,
                    PARAM_COMP_FA, compFa,
                    PARAM_RO_NUM, roNum
            ));
        } catch (Exception e) {
            throw new RuntimeException("Error executing SP_VHC_GET_JC_DTL", e);
        }
    }

    /**
     * Retrieves master data related to vehicle health systems by invoking the stored procedure {@code SP_VHC_SYS_LIST}.
     * <p>
     * This method fetches hierarchical data including systems, subsystems, and sub-subsystems based on the provided
     * PMC (Product Model Code). The result is returned as a map containing output parameters and multiple result sets
     * mapped to {@code SystemInfo}, {@code SubSystemInfo}, and {@code SubSubSystemInfo} objects.
     * </p>
     *
     * @param pmc the Product Model Code used to filter and retrieve relevant master data
     * @return a {@link Map} containing the stored procedure's output parameters and master data result sets
     * @throws RuntimeException if an error occurs while executing the stored procedure
     */
    public Map<String, Object> getMasterData(int pmc) {
        try {
            return spGetMasterData.execute(Map.of(PARAM_PMC, pmc));
        } catch (Exception e) {
            throw new RuntimeException("Error executing SP_VHC_SYS_LIST", e);
        }
    }

    /**
     * Updates the details of a Job Card by invoking the stored procedure {@code SP_VHC_UPDATE_JC_DTL}.
     * <p>
     * This method performs an update operation on vehicle health card details, including overall remarks,
     * inspection data, and demanded repairs. It uses Oracle ARRAY types to handle complex data structures
     * such as inspection details and repair lists. The update is executed within a transactional context
     * to ensure data integrity.
     * </p>
     *
     * <b>Expected Parameters in the Map:</b>
     * <ul>
     *   <li>{@code PN_PARENT_GROUP} - Parent group identifier</li>
     *   <li>{@code PN_DEALER_MAP_CD} - Dealer mapping code</li>
     *   <li>{@code PN_LOC_CD} - Location code</li>
     *   <li>{@code PN_COMP_FA} - Company FA code</li>
     *   <li>{@code PN_RO_NUM} - Repair order number</li>
     *   <li>{@code PN_OVERALL_REMARKS} - Overall remarks for the job card</li>
     *   <li>{@code PN_VHC_INS_ARR} - List of vehicle inspection details (mapped to Oracle ARRAY type {@code VHC_INS_TYPE})</li>
     *   <li>{@code PN_DEMANDED_REPAIR} - List of demanded repairs (mapped to Oracle ARRAY type {@code REPAIR_TYPE})</li>
     * </ul>
     *
     * @param params a {@link Map} containing all required parameters for the update operation
     * @return a {@link Map} containing the stored procedure's output parameters after execution
     * @throws RuntimeException if an error occurs while executing the stored procedure or mapping Oracle ARRAY types
     */
    @Transactional
    public Map<String, Object> updateJCDetails(Map<String, Object> params) {
        try {
            // Handle Oracle ARRAY mapping for PN_VHC_INS_ARR
            SqlParameterSource sqlParams = new MapSqlParameterSource()
                    .addValue(PARAM_PARENT_GROUP, params.get(PARAM_PARENT_GROUP))
                    .addValue(PARAM_DEALER_MAP_CD, params.get(PARAM_DEALER_MAP_CD))
                    .addValue(PARAM_LOC_CD, params.get(PARAM_LOC_CD))
                    .addValue(PARAM_COMP_FA, params.get(PARAM_COMP_FA))
                    .addValue(PARAM_RO_NUM, params.get(PARAM_RO_NUM))
                    .addValue("PN_OVERALL_REMARKS", params.get("PN_OVERALL_REMARKS"))
                    .addValue("PN_VHC_INS_ARR", createOracleArray("VHC_INS_TYPE", (List<?>) params.get("PN_VHC_INS_ARR")))
                    .addValue("PN_DEMANDED_REPAIR", createOracleArray("REPAIR_TYPE", (List<?>) params.get("PN_DEMANDED_REPAIR")));

            return spUpdateJCDetails.execute(sqlParams);
        } catch (Exception e) {
            throw new RuntimeException("Error executing SP_VHC_UPDATE_JC_DTL", e);
        }
    }

    private Array createOracleArray(String typeName, List<?> data) throws SQLException {
        assert jdbcTemplate.getDataSource() != null;
        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            return conn.createArrayOf(typeName, data.toArray());
        }
    }

}
