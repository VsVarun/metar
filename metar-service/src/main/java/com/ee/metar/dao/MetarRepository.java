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
import com.ee.metar.model.persistence.Metar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project: metar
 * Package: com.ee.metar.dao
 * Desc:
 * User: Varun Chandresekar
 * Date: 28-02-2022
 * Time: 21:56
 */
@Repository
public interface MetarRepository extends JpaRepository<Metar, Long> {

    /**
     * API to find Metar data by icaoCode
     * @param icaoCode icaoCode
     * @return Metar Metar
     */
    Metar findByIcaoCode(String icaoCode);

    /**
     * API to unsubscribe station metar data
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    @Modifying
    @Transactional
    @Query("delete from Metar m where m.icaoCode=:icaoCode")
    void unsubscribe(@Param("icaoCode") String icaoCode) throws MetarException;
}
