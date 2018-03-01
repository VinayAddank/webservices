package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;

public interface VehicleBillingDetailsDAO extends GenericDAO<VehicleBillingDetailsEntity> {

    public abstract VehicleBillingDetailsEntity get(Long vehicleBillingDetails);

    public abstract VehicleBillingDetailsEntity getByVehicleDetailId(VehicleDetailsEntity vehicleDetails);

    public List<VehicleBillingDetailsEntity> getByVehicleBillingIds(List<Long> ids);

}
