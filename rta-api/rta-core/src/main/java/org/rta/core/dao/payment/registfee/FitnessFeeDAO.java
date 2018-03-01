package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;

public interface FitnessFeeDAO extends GenericDAO<FitnessFeeDetailsEntity> {

	public FitnessFeeDetailsEntity getByVehicleRcId(long vehicleRcId);
	
}
