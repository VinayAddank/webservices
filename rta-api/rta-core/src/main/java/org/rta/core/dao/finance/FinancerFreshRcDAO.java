package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinancerFreshRcEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;

public interface FinancerFreshRcDAO extends GenericDAO<FinancerFreshRcEntity>{
	
	public FinancerFreshRcEntity getFreshRcFromVehicleRc(Long vehicleRc);
	
	/**
	 * List of all fresh Rc list for approver type and status.
	 * @param approver
	 * @param status
	 * @return
	 */
	public List<FinancerFreshRcEntity> getFreshRcList(UserType approver, Status status);
	
	public FinancerFreshRcEntity getFreshRcFromApplicationNumber(String applicationNumber);

	public List<FinancerFreshRcEntity> getOpenedApplications();
}
