package org.rta.core.dao.payment.tax;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.PeriodicTaxEntity;

public interface PeriodicTaxDAO extends GenericDAO<PeriodicTaxEntity>{

	
	public PeriodicTaxEntity getEntityByVehicleRcId(Long vehicleRcId);
}
