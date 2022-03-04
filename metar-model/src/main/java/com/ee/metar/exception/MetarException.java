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

package com.ee.metar.exception;

/**
 * Project: metar
 * Package: com.ee.metar.exception
 * Description: Central Exception for all Metar Exception
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 14:59
 */
public class MetarException extends Exception {

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     * @param message message
     * @param cause cause
     */
    public MetarException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     *
     * @param message message
     */
    public MetarException(String message) {
        super(message);
    }
}
