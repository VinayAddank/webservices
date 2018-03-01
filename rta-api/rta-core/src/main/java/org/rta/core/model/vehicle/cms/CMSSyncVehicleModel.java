package org.rta.core.model.vehicle.cms;

/**
 *	@Author sohan.maurya created on Oct 5, 2016.
 */
public class CMSSyncVehicleModel {

    private Long syncId;
    private String vehicleNumber;
    private Long vehicleRcId;
    private String responseCode;
    private String message;
    private String serviceCode;
    private Long createdOn;
    private Long modifiedOn;

    public Long getSyncId() {
        return syncId;
    }

    public void setSyncId(Long syncId) {
        this.syncId = syncId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Long modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

}
