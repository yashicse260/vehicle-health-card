
package com.MSIL.VehicleHealthCard.utils;

import com.MSIL.VehicleHealthCard.DTOs.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(ResponseUtil.class);
    private static final String SUCCESS_STATUS = "SUCCESS";

    /**
     * Builds a ResponseEntity based on the response status and error code.
     *
     * @param response BaseResponse or its subclass
     * @param <T>      Type extending BaseResponse
     * @return ResponseEntity with appropriate HTTP status
     */
    public <T extends BaseResponse> ResponseEntity<T> buildResponse(T response) {
        if (response == null) {
            logger.error("Response object is null. Returning INTERNAL_SERVER_ERROR.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        logger.info("Building response for status: {}, errorCode: {}", response.getStatus(), response.getErrorCode());

        if (SUCCESS_STATUS.equalsIgnoreCase(response.getStatus())) {
            return ResponseEntity.ok(response);
        }

        HttpStatus status = mapErrorCodeToHttpStatus(response.getErrorCode());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Maps error codes to HttpStatus.
     *
     * @param errorCode integer error code
     * @return HttpStatus corresponding to error code
     */
    private HttpStatus mapErrorCodeToHttpStatus(int errorCode) {
        return switch (errorCode) {
            case 400 -> HttpStatus.BAD_REQUEST;
            case 401 -> HttpStatus.UNAUTHORIZED;
            case 403 -> HttpStatus.FORBIDDEN;
            case 404 -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
