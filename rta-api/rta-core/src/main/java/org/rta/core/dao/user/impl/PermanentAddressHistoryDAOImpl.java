package org.rta.core.dao.user.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.PermanentAddressHistoryDAO;
import org.rta.core.entity.user.PermanentAddressHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

@Repository
public class PermanentAddressHistoryDAOImpl extends BaseDAO<PermanentAddressHistoryEntity> implements PermanentAddressHistoryDAO{

	public PermanentAddressHistoryDAOImpl() {
		super(PermanentAddressHistoryEntity.class);
	}

	@Override
	public String saveData(Long addressId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {
		SQLQuery query = (SQLQuery) getSession()
                .createSQLQuery("SELECT permanent_address_history_function(:addressId, :modifiedBy, :modifiedOn, :serviceCode )")
                .setParameter("addressId", addressId).setParameter("modifiedBy", modifiedBy)
                .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());

        return (String) query.list().get(0);
	}

}
