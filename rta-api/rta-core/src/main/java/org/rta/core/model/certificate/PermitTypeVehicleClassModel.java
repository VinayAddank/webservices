package org.rta.core.model.certificate;

import java.util.List;

import org.rta.core.model.master.PermitTypeModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;

/**
 *	@Author sohan.maurya created on Dec 30, 2016.
 */
public class PermitTypeVehicleClassModel {

    
    private VehicleClassDescModel vehicleClassDetails;
    private List<PermitTypeModel> permitType;

    public VehicleClassDescModel getVehicleClassDetails() {
        return vehicleClassDetails;
    }

    public void setVehicleClassDetails(VehicleClassDescModel vehicleClassDetails) {
        this.vehicleClassDetails = vehicleClassDetails;
    }

    public List<PermitTypeModel> getPermitType() {
        return permitType;
    }

    public void setPermitType(List<PermitTypeModel> permitType) {
        this.permitType = permitType;
    }

}
