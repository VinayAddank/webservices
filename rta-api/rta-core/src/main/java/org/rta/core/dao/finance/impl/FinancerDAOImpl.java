package org.rta.core.dao.finance.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinancerDAO;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.springframework.stereotype.Repository;


@Repository
public class FinancerDAOImpl extends BaseDAO<FinancerMasterEntity> implements FinancerDAO {

	public FinancerDAOImpl() {
		super(FinancerMasterEntity.class);
	}

    @Override
    public FinancerMasterEntity getByUserId(Long userId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("userId", userId));
        return (FinancerMasterEntity) criteria.uniqueResult();
    }

}
