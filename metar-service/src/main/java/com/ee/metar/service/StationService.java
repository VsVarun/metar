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

import com.ee.metar.dao.StationRepository;
import com.ee.metar.enums.StationSummaryHeaders;
import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Station;
import com.ee.metar.parser.HtmlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Project: metar
 * Package: com.ee.metar.service
 * Desc: Service bean to get Station data from db and also from tgftp.nws.noaa.gov
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 11:20
 */
@Service
public class StationService {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(StationService.class);

    @Autowired
    HtmlParser htmlParser;

    @Autowired
    StationRepository stationRepository;

    @Value("${station.metar.data.url}")
    public String metarDataUrl;  // Made it public for covering Integration test suite for failure test cases

    /**
     * Get Station info by icaoCode
     *
     * @param icaoCode icaoCode
     * @return Station
     */
    public Station getStation(String icaoCode) {
        return stationRepository.findByIcaoCode(icaoCode);
    }

    /**
     * Validate if the station code is correct
     *
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    public void validateStation(String icaoCode) throws MetarException {
        if (getStation(icaoCode) == null) {
            throw new MetarException("Invalid Station Code : " + icaoCode);
        }
    }

    /**
     * Get Station data from database starting with
     *
     * @param startsWith startsWith
     * @return Station list
     */
    public List<Station> getStationStartsWithFromDB(String startsWith) {
        return stationRepository.findByIcaoCodeStartsWith(startsWith.toUpperCase());
    }

    /**
     * Get Station data from NWS
     *
     * @return Station list
     * @throws MetarException MetarException
     */
    public List<Station> getStationFromNWS() throws MetarException {
        List<Station> stationList = null;
        try {
            Document document = Jsoup.parse(new URL(metarDataUrl), 10000);
            stationList = parseStationSummary(document);
            stationRepository.saveAll(stationList);
        } catch (Exception e) {
            String errorMessage = "Error while reading Station information from " + metarDataUrl;
            LOG.error(errorMessage, e);
            throw new MetarException(errorMessage);
        }
        return stationList;
    }

    private List<Station> parseStationSummary(Document document) {
        List<Station> stationList = new ArrayList<>();
        if (document == null) {
            return stationList;
        }
        List<Map<String, String>> stationSummaryTable = htmlParser.parseTable(document);
        stationList = stationSummaryTable.stream()
                .filter(stationSummaryRow -> stationSummaryRow.get(StationSummaryHeaders.NAME.getHeader()).length() == 8)
                .map(stationSummaryRow -> {
                    Station station = new Station();
                    station.setFilename(stationSummaryRow.get(StationSummaryHeaders.NAME.getHeader()));
                    station.setIcaoCode(station.getFilename().substring(0, 4));
                    station.setSize(Integer.parseInt(stationSummaryRow.get(StationSummaryHeaders.SIZE.getHeader())));
                    station.setLastModified(stationSummaryRow.get(StationSummaryHeaders.LAST_MODIFIED.getHeader()));
                    return station;
                })
                .collect(Collectors.toList());
        return stationList;
    }
}
