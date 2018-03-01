package org.rta.core.dao.customer.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.customer.CustIndividualDetailsHistoryDAO;
import org.rta.core.entity.applicant.CustIndividualDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */
@Repository("custIndividualDetailsHistoryDAO")
public class CustIndividualDetailsHistoryDAOImpl extends BaseDAO<CustIndividualDetailsHistoryEntity>
        implements CustIndividualDetailsHistoryDAO {

    public CustIndividualDetailsHistoryDAOImpl() {
        super(CustIndividualDetailsHistoryEntity.class);
    }
    
    @Override
    public String saveData(Long CustIndivId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {
        SQLQuery query = (SQLQuery) getSession()
                        .createSQLQuery("SELECT cust_individual_details_history_function(:CustIndivId, :modifiedBy, :modifiedOn, :serviceCode )")
                        .setParameter("CustIndivId", CustIndivId).setParameter("modifiedBy", modifiedBy)
                        .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());
        return (String) query.list().get(0);
    }
}
