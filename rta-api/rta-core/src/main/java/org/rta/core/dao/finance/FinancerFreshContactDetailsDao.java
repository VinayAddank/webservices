package org.rta.core.dao.finance;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinancerFreshContactDetailsEntity;

public interface FinancerFreshContactDetailsDao extends GenericDAO<FinancerFreshContactDetailsEntity>{
	
	 public FinancerFreshContactDetailsEntity getByVehicleRcId(Long vehicleRcId);
}
