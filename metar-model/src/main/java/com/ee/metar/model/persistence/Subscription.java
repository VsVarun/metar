package com.ee.metar.model.persistence;


import com.ee.metar.model.dto.SubscriptionDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

@Entity
@Table(name = "subscriptions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription implements Serializable {

    @Id
    @Column(name="icao_code", columnDefinition = "text", unique = true, nullable = false, length = 4)
    private String icaoCode;

    @Column(name="is_active", columnDefinition = "boolean default true", nullable = false)
    private Boolean active=true;

    @CreationTimestamp
    @Column(name="created_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTime;

    @CreationTimestamp
    @Column(name = "last_modified_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastModifiedTime;

    public Subscription() {
        super();
    }

    public Subscription(SubscriptionDTO subscriptionDTO) {
        this.active = subscriptionDTO.getActive();
        this.icaoCode = subscriptionDTO.getIcaoCode();
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
