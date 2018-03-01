package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.PermanentAddressHistoryEntity;
import org.rta.core.enums.ServiceType;

public interface PermanentAddressHistoryDAO extends GenericDAO<PermanentAddressHistoryEntity>{
	
	public String saveData(Long addressId, String modifiedBy, Long modifiedOn, ServiceType serviceType);
}
