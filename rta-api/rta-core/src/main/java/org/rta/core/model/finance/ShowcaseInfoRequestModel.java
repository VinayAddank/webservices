package org.rta.core.model.finance;

import org.rta.core.enums.ServiceType;

public class ShowcaseInfoRequestModel {

    private Long vehicleRcId;
    private String aadharNumber;
    private ServiceType ServiceType;
    
    public Long getVehicleRcId() {
        return vehicleRcId;
    }
    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }
    public String getAadharNumber() {
        return aadharNumber;
    }
    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }
	public ServiceType getServiceType() {
		return ServiceType;
	}
	public void setServiceType(ServiceType serviceType) {
		ServiceType = serviceType;
	}
}
