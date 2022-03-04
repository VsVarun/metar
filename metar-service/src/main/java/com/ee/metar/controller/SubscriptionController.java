package com.ee.metar.controller;

import com.ee.metar.exception.MetarException;
import com.ee.metar.model.dto.SubscriptionDTO;
import com.ee.metar.model.persistence.Subscription;
import com.ee.metar.service.SubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.controller
 * Description: airport subscription/unsubscription and subscription listing endpoint provides support for POST, GET, and DELETE predicate
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 12:23
 */
@RestController
@RequestMapping("/airport")
public class SubscriptionController {

    /**
     * Logger
     */
    protected static final Logger LOG = LoggerFactory.getLogger(SubscriptionController.class);

    @Autowired
    SubscriptionService subscriptionService;

    /**
     * Endpoint to subscribe station/icaoCode for Metar data
     *
     * @param subscriptionDTO subscriptionDTO
     * @throws MetarException MetarException
     */
    @PostMapping("/subscriptions")
    public void subscribe(@RequestBody SubscriptionDTO subscriptionDTO) throws MetarException {
        subscriptionService.subscribe(new Subscription(subscriptionDTO));
        subscriptionService.evictSubscriptions();
    }

    /**
     * Endpoint to get Metar Data subscriptions for station/icaoCode
     *
     * @return Subscription list
     */
    @GetMapping("/subscriptions")
    public List<Subscription> getSubscriptions() {
        return subscriptionService.getSubscriptions();
    }

    /**
     * Endpoint to unsubscribe station/icaoCode for Metar Data
     *
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    @DeleteMapping("/subscriptions/{icaoCode}")
    public void unsubscribe(@PathVariable("icaoCode") String icaoCode) throws MetarException {
        subscriptionService.unsubscribe(icaoCode);
        subscriptionService.evictSubscriptions();
    }


}
