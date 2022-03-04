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

package com.ee.metar.controller;

import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Station;
import com.ee.metar.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.controller
 * Desc:
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 13:53
 */
@RestController
@RequestMapping("/airport")
public class StationController {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(StationController.class);

    @Autowired
    StationService stationService;

    /**
     * Endpoint to get Station info with starts with Keyword search
     * @param startsWith startsWith
     * @return Station list
     * @throws MetarException MetarException
     */
    @GetMapping("/stations/{startsWith}")
    public List<Station> getStationFromDB(
            @PathVariable("startsWith") String startsWith) throws MetarException {
        return stationService.getStationStartsWithFromDB(startsWith);
    }
}
