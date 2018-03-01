package org.rta.core.entity.vehicle.cms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * this entity store record CMS syncing status log
 * 
 * @Author sohan.maurya created on Oct 5, 2016.
 */

@Entity
@Table(name = "cms_sync_vehicle")
public class CMSSyncVehicleEntity {
    /**
     * 
     */
    @Id
    @Column(name = "sync_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cms_sync_vehicle_gen")
    @SequenceGenerator(name = "cms_sync_vehicle_gen", sequenceName = "cms_sync_vehicle_sequence", allocationSize = 1)
    private Long syncId;

    @Column(name = "vehicle_number", length = 20)
    private String vehicleNumber;

    @Column(name = "vehicle_rc_id")
    private Long vehicleRcId;

    @Column(name="response_code")
    private String responseCode;

    @Column(name = "message", length = 1000)
    private String message;

    @Column(name = "created_on", length = 50)
    private Long createdOn;

    @Column(name = "modified_on", length = 30)
    private Long modifiedOn;

    @Column(name = "service_code", length = 50)
    private String serviceCode;
    
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
