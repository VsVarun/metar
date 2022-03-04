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

package com.ee.metar.service;

import com.ee.metar.dao.MetarRepository;
import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Metar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.service
 * Desc: Service to serve and manage Metar data
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 19:53
 */
@Service
public class MetarService {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(MetarService.class);

    @Autowired
    MetarRepository metarRepository;

    @Autowired
    StationService stationService;

    /**
     * API to store Metar data to database
     * @param icaoCode icaoCode
     * @param metar metar
     * @throws MetarException MetarException
     */
    @CacheEvict(value = "MetarData")
    public void storeMetarData(String icaoCode, Metar metar) throws MetarException {
        stationService.validateStation(icaoCode);
        metar.setIcaoCode(icaoCode);
        metarRepository.save(metar);
    }

    /**
     * API to persist Metar data list to database
     * @param metarList metarList
     */
    @CacheEvict(value = "MetarData")
    public void storeMetarData(List<Metar> metarList) {
        metarRepository.saveAll(metarList);
    }

    /**
     * API to get Metar data by icao Code
     * @param icaoCode icaoCode
     * @return Metar data
     */
    @Cacheable("MetarData")
    @Transactional
    public Metar getMetarData(String icaoCode) {
        return metarRepository.findByIcaoCode(icaoCode);
    }
}
