package org.rta.core.model.vehicle;

import org.rta.core.enums.VehicleCategory;

/**
 *	@Author sohan.maurya created on Nov 23, 2016.
 */

public class VehicleDetailsModel {

    private Long vehicleRcId;
    private String registrationCategory;
    private String prNumber;
    private UnregisteredVehicleModel vehicleDetails;
    private VehicleCategory vehicleCategory;
    
    public Long getVehicleRcId() {
        return vehicleRcId;
    }

    public void setVehicleRcId(Long vehicleRcId) {
        this.vehicleRcId = vehicleRcId;
    }

    public String getRegistrationCategory() {
        return registrationCategory;
    }

    public void setRegistrationCategory(String registrationCategory) {
        this.registrationCategory = registrationCategory;
    }

    public UnregisteredVehicleModel getVehicleDetails() {
        return vehicleDetails;
    }

    public void setVehicleDetails(UnregisteredVehicleModel vehicleDetails) {
        this.vehicleDetails = vehicleDetails;
    }

    public String getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(String prNumber) {
        this.prNumber = prNumber;
    }

    public VehicleCategory getVehicleCategory() {
        return vehicleCategory;
    }

    public void setVehicleCategory(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }
}
