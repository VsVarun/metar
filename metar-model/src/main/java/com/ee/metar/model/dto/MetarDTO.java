package com.ee.metar.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Project: metar
 * Package: com.ee.metar.model.persistence
 * Description: Entity to persist Metar information of various stations
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 11:12
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetarDTO implements Serializable {

    /**
     * Station code / icao code
     */
    private String icaoCode;
    /**
     * Metar Data
     */
    private String data;
    /**
     * Created Time
     */
    private Timestamp createdTime;
    /**
     * Last Modified Time
     */
    private Timestamp lastModifiedTime;

    public MetarDTO() {
        super();
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Timestamp lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return "Metar{" +
                ", icaoCode='" + icaoCode + '\'' +
                ", data='" + data + '\'' +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
