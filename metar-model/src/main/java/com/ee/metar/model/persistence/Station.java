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
 * Desc: Entity to persist Station information
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 11:28
 */
@Entity
@Table(name = "station")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station implements Serializable {

    @Id
    @Column(name="icao_code", columnDefinition = "text", unique = true, nullable = false, length = 4)
    private String icaoCode;

    @Column(name="filename", columnDefinition = "text", unique = true, nullable = false, length = 8)
    private String filename;

    @Column(name="last_modified", columnDefinition = "text", nullable = false)
    private String lastModified;

    @Column(name="size", columnDefinition = "int", nullable = false)
    private int size;

    public Station() {
        super();
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Station{" +
                "icaoCode='" + icaoCode + '\'' +
                ", filename='" + filename + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", size=" + size +
                '}';
    }

    public String toCsv(){
        return filename+"|"+ lastModified +"|"+size+"\n";
    }
}
