package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;


public interface FinanceDetailsDAO extends GenericDAO<FinanceDetailsEntity>{
    public FinanceDetailsEntity getFinanceDetailsOfVehicleRcd(Long vehicleId);

    /**
     * Get FinanceDetails for multiple VehicleRCEntiteis
     * 
     * @param vehicleRCEntities
     * @return
     */
    public List<FinanceDetailsEntity> getFinanceDetailsByVehicleRCEntities(List<VehicleRCEntity> vehicleRCEntities);

}
