/*
 * *
 *  * Project: ${PROJECT_NAME}
 *  * Package: ${PACKAGE_NAME}
 *  * Desc:
 *  * User: Varun Chandresekar
 *  * Date: ${DATE}
 *  * Time: ${TIME}
 *  * Copyright (c) 2022.
 *
 *
 */

package com.ee.metar.controller.advice;

import com.ee.metar.exception.MetarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Project: metar
 * Package: com.ee.metar.controller.advice
 * Desc: Controller Advice for handling rest endpoint exceptions
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 20:24
 */
@ControllerAdvice
public class MetarControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(MetarControllerAdvice.class);

    /**
     * Exception handler to handle Metar Exceptions
     *
     * @param ex MetarException
     * @param request request
     * @return ResponseEntity
     */
    @ExceptionHandler(MetarException.class)
    public ResponseEntity<Object> handleMetarException(
            MetarException ex, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
