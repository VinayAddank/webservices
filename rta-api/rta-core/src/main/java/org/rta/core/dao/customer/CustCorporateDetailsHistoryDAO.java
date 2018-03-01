package org.rta.core.dao.customer;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */
public interface CustCorporateDetailsHistoryDAO extends GenericDAO<CustCorporateDetailsHistoryEntity> {

    public String saveData(Long CustCorpoId, String modifiedBy, Long modifiedOn, ServiceType serviceType);

}
