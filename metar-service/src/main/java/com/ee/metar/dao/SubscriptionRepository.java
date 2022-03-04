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

package com.ee.metar.dao;

import com.ee.metar.exception.MetarException;
import com.ee.metar.model.persistence.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project: metar
 * Package: com.ee.metar.dao
 * Desc: JPA Repository
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 20:32
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * API to delete subscription by icao code
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    @Modifying
    @Transactional
    @Query("delete from Subscription s where s.icaoCode=:icaoCode")
    void unsubscribe(@Param("icaoCode") String icaoCode) throws MetarException;

}
