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
import com.ee.metar.model.persistence.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Project: metar
 * Package: com.ee.metar.dao
 * Desc: JPA Repository for Station table
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 20:32
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    /**
     * API to find Station by icao code
     * @param icaoCode icaoCode
     * @return Station Station
     */
    Station findByIcaoCode(String icaoCode);

    /**
     * API to find station starting with provided keyword
     * @param startsWith startsWith
     * @return Station list
     */
    List<Station> findByIcaoCodeStartsWith(String startsWith);

    /**
     * API to delete station by icao code
     * @param icaoCode icaoCode
     * @throws MetarException MetarException
     */
    @Modifying
    @Transactional
    @Query("delete from Station s where s.icaoCode=:icaoCode")
    void delete(@Param("icaoCode") String icaoCode) throws MetarException;

}
