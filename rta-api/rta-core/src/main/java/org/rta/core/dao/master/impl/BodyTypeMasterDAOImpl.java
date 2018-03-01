package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.BodyTypeMasterDAO;
import org.rta.core.entity.master.BodyTypeMasterEntity;
import org.springframework.stereotype.Repository;

@Repository
public class BodyTypeMasterDAOImpl extends BaseDAO<BodyTypeMasterEntity> implements BodyTypeMasterDAO{

	public BodyTypeMasterDAOImpl() {
		super(BodyTypeMasterEntity.class);
	}

	@Override
	public List<String> getActiveBodyTypeList() {
		Criteria criteria=  getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("isActive", true));
		criteria.setProjection(Projections.property("bodyType"));
		return criteria.list();
	}
	
	
}
