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
import com.ee.metar.model.persistence.Subscription;
import com.ee.metar.service.MetarService;
import com.ee.metar.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Project: metar
 * Package: com.ee.metar.scheduler
 * Desc: scheduled to run in regular intervals, that retrieves a list of subscribed airports, queries an external service for METAR data, and sends the retrieved data to the METAR storage service.
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 11:18
 */
@Component
public class MetarDataScheduler {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(MetarDataScheduler.class);

    @Value("${metar.data.refresh.threadCount}")
    int threadCount;

    @Value("${metar.data.refresh.timeout}")
    int timeout;

    @Value("${station.metar.data.url}")
    public String url; // Made it public for covering Integration test suite for failure test cases

    @Autowired
    SubscriptionService subscriptionService;

    @Autowired
    MetarService metarService;

    /***
     * 1. Get the station codes from subscription
     * 2. Multithread and get station Metar data
     * 3. Save the metar data
     * 4. Log the failures
     */
    @Scheduled(fixedDelayString = "${metar.data.refresh.interval}", initialDelayString = "${metar.data.refresh.interval}")
    public void refreshSubscribedStationMetarData() {
        try {
            List<Subscription> subscriptionList = subscriptionService.getSubscriptions();
            ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
            List<MetarDataCallable> metarDataCallables = new ArrayList<>();
            subscriptionList.forEach(subscription ->
                    metarDataCallables.add(new MetarDataCallable(url, subscription.getIcaoCode()))
            );
            List<Future<Metar>> metarFutureList = executorService.invokeAll(metarDataCallables);
            executorService.shutdown();
            if (!executorService.awaitTermination(timeout, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }

            List<Metar> metarList = new ArrayList<>();
            metarFutureList.forEach(metarFuture -> {
                try {
                    Metar metar = metarFuture.get();
                    if (metar != null) {
                        metarList.add(metar);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    LOG.error("Failed to get Metar data", e);
                    Thread.currentThread().interrupt();
                }
            });

            // Storing the Metar Data
            metarService.storeMetarData(metarList);

        } catch (InterruptedException e) {
            LOG.error("Error while refreshing Metar data for subscribed stations");
            Thread.currentThread().interrupt();
        }
    }

}
