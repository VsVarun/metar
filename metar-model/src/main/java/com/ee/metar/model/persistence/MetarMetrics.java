package com.ee.metar.model.persistence;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Project: metar
 * Package: com.ee.metar.model.persistence
 * Description: Entity to persist Metar information of various stations
 * User: Varun Chandresekar
 * Date: 24-02-2022
 * Time: 11:12
 */
@Entity
@Table(name = "metar_metrics")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetarMetrics implements Serializable {

    @Id
    @Column(name="icao_code", columnDefinition = "text", unique = true, nullable = false, length = 4)
    private String icaoCode;

    @Column(name="timestamp", columnDefinition = "text")
    private String timestamp;

    @Column(name="wind_strength", columnDefinition = "text")
    private String windStrength;

    @Column(name="temperature", columnDefinition = "text")
    private String temperature;

    @Column(name="visibility", columnDefinition = "text")
    private String visibility;

    public MetarMetrics() {
        super();
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWindStrength() {
        return windStrength;
    }

    public void setWindStrength(String windStrength) {
        this.windStrength = windStrength;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return "MetarMetrics{" +
                "icaoCode='" + icaoCode + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", windStrength='" + windStrength + '\'' +
                ", temperature='" + temperature + '\'' +
                ", visibility='" + visibility + '\'' +
                '}';
    }
}
