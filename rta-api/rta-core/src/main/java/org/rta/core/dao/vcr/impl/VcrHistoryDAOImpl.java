package org.rta.core.dao.vcr.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vcr.VcrHistoryDAO;
import org.rta.core.entity.vcr.VcrDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VcrHistoryDAOImpl extends BaseDAO<VcrDetailsEntity> implements VcrHistoryDAO {

	public VcrHistoryDAOImpl() {
		super(VcrDetailsEntity.class);
		
	}
	
    @Override
    public VcrDetailsEntity getVcrDetailsByVcrNumber(String vcrNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vcrNum", vcrNumber)).setMaxResults(1);
        return (VcrDetailsEntity) criteria.uniqueResult();
    }

}
