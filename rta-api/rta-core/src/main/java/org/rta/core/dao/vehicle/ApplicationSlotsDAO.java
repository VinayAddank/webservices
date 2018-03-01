package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.ApplicationSlotsEntity;

public interface ApplicationSlotsDAO extends GenericDAO<ApplicationSlotsEntity> {

    List<ApplicationSlotsEntity> getApplicationsByVehicleRcId(Long vehicleRcId);

}
