
package com.MSIL.VehicleHealthCard.service;

import com.MSIL.VehicleHealthCard.DTOs.request.*;
import com.MSIL.VehicleHealthCard.DTOs.response.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Service contract for Vehicle Health Card operations.
 * Implementations should validate business rules and throw well-defined runtime exceptions
 * which controllers translate to HTTP responses (via a {@code @ControllerAdvice}).
 */
@Validated
public interface VehicleHealthCardService {

    /**
     * Validate login credentials and return the login response DTO.
     *
     * @param request non-null, validated login request
     * @return LoginResponse DTO
     */
    LoginResponse validateLogin(@Valid @NotNull LoginRequest request);

    /**
     * Retrieve list of job cards based on request criteria.
     *
     * @param request non-null, validated JCListRequest
     * @return JCListResponse DTO
     */
    JCListResponse getJCList(@Valid @NotNull JCListRequest request);

    /**
     * Retrieve details for a specific job card.
     *
     * @param request non-null, validated JCDetailsRequest
     * @return JCDetailsResponse DTO
     */
    JCDetailsResponse getJCDetails(@Valid @NotNull JCDetailsRequest request);

    /**
     * Retrieve master data required by the client.
     *
     * @param request non-null, validated MasterDataRequest
     * @return MasterDataResponse DTO
     */
    MasterDataResponse getMasterData(@Valid @NotNull MasterDataRequest request);

    /**
     * Update job card details and return the outcome.
     *
     * @param request non-null, validated UpdateJCRequest
     * @return UpdateJCResponse DTO
     */
    UpdateJCResponse updateJCDetails(@Valid @NotNull UpdateJCRequest request);
}
