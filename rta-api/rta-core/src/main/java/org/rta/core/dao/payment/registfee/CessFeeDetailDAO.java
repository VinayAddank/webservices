package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.registfee.CessFeeDetailsEntity;

public interface CessFeeDetailDAO extends GenericDAO<CessFeeDetailsEntity> {

	public CessFeeDetailsEntity getByVehicleRcId(long vehicleRcId);
}
