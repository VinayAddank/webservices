package org.rta.core.dao.license.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.SuspDriversLicenseDetailDAO;
import org.rta.core.entity.licence.SuspDriversLicenseDetailEntity;
import org.rta.core.entity.licence.SuspDriversLicenseDtlPK;
import org.springframework.stereotype.Repository;

@Repository
public class SuspDriversLicenseDetailDAOImpl extends BaseDAO<SuspDriversLicenseDetailEntity> implements SuspDriversLicenseDetailDAO{

	public SuspDriversLicenseDetailDAOImpl() {
		super(SuspDriversLicenseDetailEntity.class);
	}

	@Override
	public SuspDriversLicenseDetailEntity getSuspDriversLicenseDetail(SuspDriversLicenseDtlPK pk) {
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("licenceHolderId", pk.getLicenceHolderId()))
				.add(Restrictions.eq("dlSequenceId", pk.getDlSequenceId()));
		return (SuspDriversLicenseDetailEntity) criteria.uniqueResult();
	}

	
}
