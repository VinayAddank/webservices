package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.registfee.PermitFeeEntity;

public interface PermitFeeDAO extends GenericDAO<PermitFeeEntity> {

	public PermitFeeEntity getByVehicleRcIdNdStatus(long vehicleRcId, Integer status);
}
