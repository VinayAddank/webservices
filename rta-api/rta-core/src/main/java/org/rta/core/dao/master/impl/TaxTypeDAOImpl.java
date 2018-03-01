package org.rta.core.dao.master.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.TaxTypeDAO;
import org.rta.core.entity.master.TaxTypeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TaxTypeDAOImpl  extends BaseDAO<TaxTypeEntity> implements TaxTypeDAO{
    public TaxTypeDAOImpl() {
		super(TaxTypeEntity.class);
	}

    @Override
    public TaxTypeEntity getByCode(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code));
        return (TaxTypeEntity) criteria.uniqueResult();
    }
}
