package org.rta.core.dao.license.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenceIdpDtlsDAO;
import org.rta.core.entity.licence.LicenseIdpDtlEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LicenceIdplDtlsDAOImpl extends BaseDAO<LicenseIdpDtlEntity>
		implements LicenceIdpDtlsDAO {

	public LicenceIdplDtlsDAOImpl() {
		super(LicenseIdpDtlEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LicenseIdpDtlEntity> getLicenseIdpDtl(Long holderId) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("id.licenceHolderId", holderId)).addOrder(Order.asc("id.idpSequenceId"));
		return  criteria.list();
	}

}
