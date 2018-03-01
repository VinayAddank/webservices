package org.rta.core.dao.hsrp;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.hsrp.HSRPFeeDetailsEntity;

public interface HSRPFeeDetailsDAO extends GenericDAO<HSRPFeeDetailsEntity> {

	public HSRPFeeDetailsEntity getByVehicleRcId(Long vehicleRcId);
}
