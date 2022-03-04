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
import com.ee.metar.dao.SubscriptionRepository;
import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.service
 * Desc: Service to serve and manage station icao subscriptions
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 20:01
 */

@Service
public class SubscriptionService {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(SubscriptionService.class);

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    MetarRepository metarRepository;

    @Autowired
    StationService stationService;

    /**
     * API to subscribe for station metar data
     * @param subscription subscription
     * @throws MetarException MetarException
     */
    @CacheEvict(value = "Subscriptions")
    public void subscribe(Subscription subscription) throws MetarException {
        stationService.validateStation(subscription.getIcaoCode());
        subscriptionRepository.save(subscription);
    }

    /**
     * API to get all subscribed stations
     * @return Subscription list
     */
    @Cacheable("Subscriptions")
    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    /**
     * API to unsubscribe station
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    @CacheEvict(value = "Subscriptions")
    public void unsubscribe(String icaoCode) throws MetarException {
        // Validate valid station codes
        stationService.validateStation(icaoCode);
        metarRepository.unsubscribe(icaoCode);
        subscriptionRepository.unsubscribe(icaoCode);
    }

    /**
     * API to Evict Subscription Cache
     */
    @CacheEvict(value = "Subscriptions")
    public void evictSubscriptions() {
        // Evict Subscription Cache
    }
}
