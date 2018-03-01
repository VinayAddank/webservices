package org.rta.core.model.master.registration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PrSeriesMasterModel {

    private Long prSeriesMasterId;
    private String series;
    private String startNumber;
    private String endNumber;

    public Long getPrSeriesMasterId() {
        return prSeriesMasterId;
    }

    public void setPrSeriesMasterId(Long prSeriesMasterId) {
        this.prSeriesMasterId = prSeriesMasterId;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(String startNumber) {
        this.startNumber = startNumber;
    }

    public String getEndNumber() {
        return endNumber;
    }

    public void setEndNumber(String endNumber) {
        this.endNumber = endNumber;
    }
}
