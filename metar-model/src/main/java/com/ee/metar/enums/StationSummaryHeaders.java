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

package com.ee.metar.enums;

/**
 * Project: metar
 * Package: com.ee.metar.enums
 * Desc: Enum for Station Summary Table column headers
 * User: Varun Chandresekar
 * Date: 04-03-2022
 * Time: 12:58
 */
public enum StationSummaryHeaders {
    NAME("Name"),  LAST_MODIFIED("Last modified"), SIZE("Size");

    public final String header;

    StationSummaryHeaders(){ this.header = null; }

    StationSummaryHeaders(String header) { this.header = header; }

    public String getHeader() { return this.header; }
}
