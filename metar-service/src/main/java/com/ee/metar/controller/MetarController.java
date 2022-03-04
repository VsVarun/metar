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
import com.ee.metar.model.dto.MetarDTO;
import com.ee.metar.model.persistence.Metar;
import com.ee.metar.service.MetarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.controller
 * Description: Rest Endpoints for METAR data storage/retrieval service
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 12:39
 */
@RestController
@RequestMapping("/airport")
public class MetarController {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(MetarController.class);

    @Autowired
    MetarService metorService;

    /**
     * Endpoint to store METAR data for a station/icao code
     *
     * @param icaoCode icaoCode
     * @param metarDTO metarDTO
     * @throws MetarException MetarException
     */
    @PostMapping("/{icaoCode}/METAR")
    public void storeMetarData(@PathVariable("icaoCode") String icaoCode, @RequestBody MetarDTO metarDTO) throws MetarException {
        metorService.storeMetarData(icaoCode, new Metar(metarDTO));
    }

    /**
     * Endpoint to store METAR data for list of station/icao code
     *
     * @param metarList metarList
     * @throws MetarException MetarException
     */
    @PostMapping("/METAR")
    public void storeMetarData(@RequestBody List<Metar> metarList) throws MetarException {
        metorService.storeMetarData(metarList);
    }

    /**
     * Endpoint to get METAR data for a Station/icao code
     *
     * @param icaoCode icaoCode
     * @return Metar
     * @throws MetarException MetarException
     */
    @GetMapping("/{icaoCode}/METAR")
    public Metar getMetarData(@PathVariable("icaoCode") String icaoCode) throws MetarException {
        return metorService.getMetarData(icaoCode);
    }

}
