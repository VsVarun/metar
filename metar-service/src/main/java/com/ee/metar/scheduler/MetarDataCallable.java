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

import com.ee.metar.model.persistence.Metar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

/**
 * Project: metar
 * Package: com.ee.metar.scheduler
 * Desc:
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 16:26
 */
public class MetarDataCallable implements Callable<Metar> {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(MetarDataCallable.class);

    private final String url;
    private final String icaoCode;

    /**
     * Constructor to pass url and icao Code
     * @param url url
     * @param icaoCode icaoCode
     */
    public MetarDataCallable(String url, String icaoCode) {
        this.url = url + icaoCode + ".TXT";
        this.icaoCode = icaoCode;
    }

    @Override
    public Metar call() throws Exception {
        Metar metar = null;
        try {
            String metarData = new RestTemplate().getForObject(url, String.class);
            if (StringUtils.hasText(metarData)) {
                metar = new Metar();
                metar.setIcaoCode(icaoCode);
                metar.setData(metarData);
            }
        } catch (Exception e) {
            LOG.error("Failed to fetch Metar data for ICAO Code : {}", icaoCode, e);
        }
        return metar;
    }
}
