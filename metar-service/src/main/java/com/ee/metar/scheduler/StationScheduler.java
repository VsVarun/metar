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

package com.ee.metar.scheduler;

import com.ee.metar.exception.MetarException;
import com.ee.metar.service.StationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Project: metar
 * Package: com.ee.metar.scheduler
 * Desc:
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 11:29
 */
@Component
public class StationScheduler {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(StationScheduler.class);

    @Autowired
    StationService stationService;

    /***
     *
     */
    @Scheduled(fixedDelayString = "${station.metar.data.refresh.interval}")
    public void refreshStationSummary() {
        try {
            stationService.getStationFromNWS();
        } catch (MetarException e) {
            LOG.error("Error while refreshing Station data");
        }
    }

}
