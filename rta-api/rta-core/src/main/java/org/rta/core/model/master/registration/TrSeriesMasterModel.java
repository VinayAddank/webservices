package org.rta.core.model.master.registration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrSeriesMasterModel {

    private Long trSeriesMasterId;
    private String series;
    private String startNumber;
    private String endNumber;


    public Long getTrSeriesMasterId() {
        return trSeriesMasterId;
    }

    public void setTrSeriesMasterId(Long trSeriesMasterId) {
        this.trSeriesMasterId = trSeriesMasterId;
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
