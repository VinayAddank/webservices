package org.rta.core.model.master.registration;

import javax.xml.bind.annotation.XmlRootElement;

import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.model.master.BaseMasterModel;

@XmlRootElement
public class SpecialNumberModel extends BaseMasterModel {

    private String number;
    private Boolean isLocked;
    private double cost;
    private Integer regType;
    private RtaOfficeEntity rtaOffice;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public RtaOfficeEntity getRtaOffice() {
        return rtaOffice;
    }

    public void setRtaOffice(RtaOfficeEntity rtaOffice) {
        this.rtaOffice = rtaOffice;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }

}
