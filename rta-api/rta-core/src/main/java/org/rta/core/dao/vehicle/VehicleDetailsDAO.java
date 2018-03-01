package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;

public interface VehicleDetailsDAO extends GenericDAO<VehicleDetailsEntity> {

    public abstract VehicleDetailsEntity getByVehicleRcId(Long vehicleRcId);

    public String getMakersClass(Long vehicleRcId);

    public List<VehicleDetailsEntity> getByVehicleRcIds(List<Long> vehicleRcIds);

    public VehicleDetailsEntity getByChassisNo(String chassisNumber);

    public VehicleDetailsEntity getVehicleDtlsByPrNumber(String prNumber);

}
