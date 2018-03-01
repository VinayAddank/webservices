package org.rta.core.dao.master.registration.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.registration.SpecialNumberRtaOfficeDAO;
import org.rta.core.entity.master.registration.SpecialNumberRtaOfficeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialNumberRtaOfficeDAOImpl extends BaseDAO<SpecialNumberRtaOfficeEntity> implements SpecialNumberRtaOfficeDAO {

	public SpecialNumberRtaOfficeDAOImpl() {
		super(SpecialNumberRtaOfficeEntity.class);
	}
	
	
	public SpecialNumberRtaOfficeEntity getUniqueSpecialNumber(String specialNumber){
		 Criteria criteria = getSession().createCriteria(getPersistentClass());
		 criteria.add(Restrictions.eq("specialNumber", specialNumber));
		 return (SpecialNumberRtaOfficeEntity) criteria.uniqueResult();
	}


	@Override
	public SpecialNumberRtaOfficeEntity getSpecialNumberByTrNo(String trnumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("trNumber", trnumber));
		return (SpecialNumberRtaOfficeEntity) criteria.uniqueResult();
	}

}
