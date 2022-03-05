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

import com.ee.metar.dao.MetarMetricsRepository;
import com.ee.metar.dao.MetarRepository;
import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Metar;
import com.ee.metar.model.persistence.MetarMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    MetarMetricsRepository metarMetricsRepository;

    @Autowired
    StationService stationService;

    /**
     * API to store Metar data to database
     * @param icaoCode icaoCode
     * @param metar metar
     * @throws MetarException MetarException
     */
    @CacheEvict(value = {"MetarData"})
    public void storeMetarData(String icaoCode, Metar metar) throws MetarException {
        stationService.validateStation(icaoCode);
        metar.setIcaoCode(icaoCode);
        metarRepository.save(metar);
        parseMetarData(metar.getData());
    }

    /**
     * API to persist Metar data list to database
     * @param metarList metarList
     */
    @CacheEvict(value = {"MetarData"})
    public void storeMetarData(List<Metar> metarList) {
        metarRepository.saveAll(metarList);
        metarList.forEach(metar -> parseMetarData(metar.getData()));
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

    @Transactional
    public MetarMetrics getMetarMetricsData(String icaoCode) {
        return metarMetricsRepository.findByIcaoCode(icaoCode);
    }

    private void parseMetarData(String data) {
        MetarMetrics metarMetrics = new MetarMetrics();
        if(StringUtils.hasText(data)){
            String[] dataArray = data.split("\n");
            if(dataArray.length >= 2){
                metarMetrics.setTimestamp(dataArray[0]);
                List<String> dataList = Stream.of(dataArray[1].split(" "))
                        .map(String::trim)
                        .collect(Collectors.toList());
                if(!CollectionUtils.isEmpty(dataList)){
                    metarMetrics.setIcaoCode(dataList.get(0));
                    dataList = dataList.subList(2, dataList.size());
                    boolean isVisibility = false;
                    for(String metric : dataList){

                        // Visibility
                        if(isVisibility){
                            isVisibility = false;
                            metarMetrics.setVisibility(metric);
                        }

                        // Wind Strength
                        if(metric.substring(metric.length()-2).equalsIgnoreCase("KT")){
                            metarMetrics.setWindStrength(metric);
                            isVisibility = true;
                        }

                        // Temperature
                        if(metric.chars().filter(ch->ch=='/').count()==1){
                            metarMetrics.setTemperature(metric);
                            break;
                        }
                    }
                }
            }
            metarMetricsRepository.save(metarMetrics);
        }
    }
}
