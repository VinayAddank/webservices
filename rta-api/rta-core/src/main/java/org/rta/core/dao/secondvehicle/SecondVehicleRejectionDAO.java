package org.rta.core.dao.secondvehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;

public interface SecondVehicleRejectionDAO extends GenericDAO<SecondVehicleDetailsEntity> {
    public SecondVehicleDetailsEntity getSecondVehicleDetailsByVehicleRcId(Long vehicleRcId);

    public SecondVehicleDetailsEntity getSecondVehicleDetails(Long vehicleRcId, Integer iteration);
}
