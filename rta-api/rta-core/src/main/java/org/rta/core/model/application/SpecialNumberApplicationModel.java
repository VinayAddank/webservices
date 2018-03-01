package org.rta.core.model.application;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *	@Author sohan.maurya created on Oct 26, 2016.
 */

@XmlRootElement
public class SpecialNumberApplicationModel {


    private Long vehicleRcId;
    private String trNumber;
    private String customerName;
    private String customerMobile;


    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getTrNumber() {
        return trNumber;
    }

    public void setTrNumber(String trNumber) {
        this.trNumber = trNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

}
