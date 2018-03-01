package org.rta.core.dao.customer;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.applicant.CustIndividualDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */
public interface CustIndividualDetailsHistoryDAO extends GenericDAO<CustIndividualDetailsHistoryEntity> {

    public String saveData(Long CustIndivId, String modifiedBy, Long modifiedOn, ServiceType serviceType);

}
