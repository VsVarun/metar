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

import com.ee.metar.model.persistence.Metar;
import com.ee.metar.model.persistence.MetarMetrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Project: metar
 * Package: com.ee.metar.dao
 * Desc:
 * User: Varun Chandresekar
 * Date: 28-02-2022
 * Time: 21:56
 */
@Repository
public interface MetarMetricsRepository extends JpaRepository<MetarMetrics, Long> {

    /**
     * API to find MetarMetrics data by icaoCode
     * @param icaoCode icaoCode
     * @return MetarMetrics MetarMetrics
     */
    MetarMetrics findByIcaoCode(String icaoCode);

}
