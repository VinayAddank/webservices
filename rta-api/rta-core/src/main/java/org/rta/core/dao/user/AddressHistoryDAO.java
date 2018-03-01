package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.AddressHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 12, 2017.
 */
public interface AddressHistoryDAO extends GenericDAO<AddressHistoryEntity> {

    public String saveData(Long addressId, String modifiedBy, Long modifiedOn, ServiceType serviceType);
}
