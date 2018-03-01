package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleRCChangeHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */
public interface VehicleRCChangeHistoryDAO extends GenericDAO<VehicleRCChangeHistoryEntity> {

    public String saveData(Long vehicleRcId, String modifiedBy, Long modifiedOn, ServiceType serviceType);

}
