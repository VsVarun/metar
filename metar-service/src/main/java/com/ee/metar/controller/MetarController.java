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
import com.ee.metar.model.persistence.MetarMetrics;
import com.ee.metar.service.MetarService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity getMetarData(
            @Parameter(example = "KCKN")
            @PathVariable("icaoCode") String icaoCode,
            @RequestParam(required = false) boolean subset,
            @Parameter(example = "wind_strength,temperature,visibility")
            @RequestParam(required = false) String columns
            ) throws MetarException {
        if(subset){
            if(StringUtils.hasText(columns)){
                MetarMetrics metarMetrics = metorService.getMetarMetricsData(icaoCode);
                if(metarMetrics!=null){
                    List<String> columnList = Arrays.asList(columns.split(","));
                    Map<String, String> metarMetricsMap = new HashMap<>();
                    metarMetricsMap.put("icaoCode", metarMetrics.getIcaoCode());
                    metarMetricsMap.put("timestamp", metarMetrics.getTimestamp());
                    columnList.forEach(column -> {
                        if(column.equalsIgnoreCase("wind_strength")){
                            metarMetricsMap.put("wind_strength", metarMetrics.getWindStrength());
                        }else if(column.equalsIgnoreCase("temperature")){
                            metarMetricsMap.put("temperature", metarMetrics.getTemperature());
                        }else if(column.equalsIgnoreCase("visibility")){
                            metarMetricsMap.put("visibility", metarMetrics.getVisibility());
                        }
                    });
                    return new ResponseEntity(metarMetricsMap, HttpStatus.OK);
                }else{
                    return new ResponseEntity("Invalid Station Code", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity("Columns Missing, Specify in comma separated value", HttpStatus.BAD_REQUEST);
            }
        }else{
            Metar metar = metorService.getMetarData(icaoCode);
            if(metar!=null){
                return new ResponseEntity(metar, HttpStatus.OK);
            }else{
                return new ResponseEntity("Invalid Station Code", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
