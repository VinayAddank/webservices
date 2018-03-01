package org.rta.core.dao.payment.tax;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.LifeTaxEntity;

public interface LifeTaxDAO extends GenericDAO<LifeTaxEntity>{

	public LifeTaxEntity getEntityByVehicleRcId(long vehicleRcId);
}
