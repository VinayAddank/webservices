package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */
public interface VehicleDetailsHistoryDAO extends GenericDAO<VehicleDetailsHistoryEntity> {

    public String saveData(Long vehicleDtlId, String modifiedBy, Long modifiedOn, ServiceType serviceType);

}
