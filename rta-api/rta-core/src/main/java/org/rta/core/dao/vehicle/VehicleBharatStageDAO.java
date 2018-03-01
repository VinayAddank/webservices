package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleBharatStageEntity;

public interface VehicleBharatStageDAO extends GenericDAO<VehicleBharatStageEntity> {

    public VehicleBharatStageEntity getByChassisNumber(String chassisNumber);
    
    public VehicleBharatStageEntity getByChassisAndEngineNumber(String chassisNumber, String engineNo);
    
}
