package org.rta.core.dao.customer.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.customer.CustCorporateDetailsHistoryDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */

@Repository("custCorporateDetailsHistoryDAO")
public class CustCorporateDetailsHistoryDAOImpl extends BaseDAO<CustCorporateDetailsHistoryEntity>
        implements CustCorporateDetailsHistoryDAO {

    public CustCorporateDetailsHistoryDAOImpl() {
        super(CustCorporateDetailsHistoryEntity.class);

    }

    @Override
    public String saveData(Long CustCorpoId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {
        SQLQuery query = (SQLQuery) getSession()
                        .createSQLQuery("SELECT cust_corporate_details_history_function(:CustCorpoId, :modifiedBy, :modifiedOn, :serviceCode )")
                        .setParameter("CustCorpoId", CustCorpoId).setParameter("modifiedBy", modifiedBy)
                        .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());
        return (String) query.list().get(0);
    }
}
