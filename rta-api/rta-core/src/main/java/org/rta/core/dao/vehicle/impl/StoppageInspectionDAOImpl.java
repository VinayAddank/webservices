package org.rta.core.dao.vehicle.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.StoppageInspectionDAO;
import org.rta.core.entity.vehicle.StoppageInspectionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class StoppageInspectionDAOImpl extends BaseDAO<StoppageInspectionEntity> implements  StoppageInspectionDAO{

	public StoppageInspectionDAOImpl() {
		super(StoppageInspectionEntity.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoppageInspectionEntity> getStoppageInspections(String applicationNo) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("stoppageNo", applicationNo));
		return criteria.list();
	}

}
