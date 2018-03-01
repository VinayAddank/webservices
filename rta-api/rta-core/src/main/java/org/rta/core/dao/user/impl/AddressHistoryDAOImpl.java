package org.rta.core.dao.user.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.AddressHistoryDAO;
import org.rta.core.entity.user.AddressHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 12, 2017.
 */
@Repository("addressHistoryDAO")
public class AddressHistoryDAOImpl extends BaseDAO<AddressHistoryEntity> implements AddressHistoryDAO {

    public AddressHistoryDAOImpl() {
        super(AddressHistoryEntity.class);
    }

    @Override
    public String saveData(Long addressId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {

        SQLQuery query = (SQLQuery) getSession()
                .createSQLQuery("SELECT address_history_function(:addressId, :modifiedBy, :modifiedOn, :serviceCode )")
                .setParameter("addressId", addressId).setParameter("modifiedBy", modifiedBy)
                .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());

        return (String) query.list().get(0);
    }

}
