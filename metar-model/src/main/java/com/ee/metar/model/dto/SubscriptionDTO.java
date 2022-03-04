package com.ee.metar.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Project: metar
 * Package: com.ee.metar.model.persistence
 * Description: Entity for storing subscriptions of airports / stations
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 11:12
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionDTO implements Serializable {
    private String icaoCode;
    private Boolean active = true;
    private Timestamp createdTime;
    private Timestamp lastModifiedTime;

    public SubscriptionDTO() {
        super();
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
        return "Subscription{" +
                ", icaoCode='" + icaoCode + '\'' +
                ", active=" + active +
                ", createdTime=" + createdTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
