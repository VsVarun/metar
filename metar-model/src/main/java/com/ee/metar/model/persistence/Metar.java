package com.ee.metar.model.persistence;


import com.ee.metar.model.dto.MetarDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
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
@Entity
@Table(name = "metar")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metar implements Serializable {

    @Id
    @Column(name="icao_code", columnDefinition = "text", unique = true, nullable = false, length = 4)
    private String icaoCode;

    @Lob
    @Column(name="data", columnDefinition = "text", nullable = false)
    private String data;

    @CreationTimestamp
    @Column(name="created_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdTime;

    @CreationTimestamp
    @Column(name = "last_modified_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp lastModifiedTime;

    public Metar() {
        super();
    }

    public Metar(MetarDTO metarDTO){
        this.icaoCode = metarDTO.getIcaoCode();
        this.data = metarDTO.getData();
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
