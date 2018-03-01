package org.rta.core.dao.finance.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinancerDAO;
import org.rta.core.dao.finance.FinancerFreshContactDetailsDao;
import org.rta.core.entity.finance.FinancerFreshContactDetailsEntity;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.springframework.stereotype.Repository;


@Repository
public class FinancerFreshContactDetailsDaoImpl extends BaseDAO<FinancerFreshContactDetailsEntity> implements FinancerFreshContactDetailsDao {

	public FinancerFreshContactDetailsDaoImpl() {
		super(FinancerFreshContactDetailsEntity.class);
	}

	@Override
	public FinancerFreshContactDetailsEntity getByVehicleRcId(Long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        return (FinancerFreshContactDetailsEntity) criteria.uniqueResult();
	}

}
