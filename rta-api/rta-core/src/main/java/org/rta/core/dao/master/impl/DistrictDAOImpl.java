package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DistrictDAOImpl extends BaseDAO<DistrictEntity> implements DistrictDAO {

	public DistrictDAOImpl() {
		super(DistrictEntity.class);
	}

	@Override
	public DistrictEntity getDistrictByName(String name) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("name", name).ignoreCase());
		criteria.add(Restrictions.eq("status", true));
		return (DistrictEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictEntity> getDistrictsByStateCode(String code) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("status", true));
		criteria.createAlias("stateEntity", "se");
		criteria.add(Restrictions.eq("se.code", code).ignoreCase());
		return (List<DistrictEntity>) criteria.list();
	}

	@Override
	public DistrictEntity getDistrictByCode(String code) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("status", true));
		criteria.add(Restrictions.eq("code", code).ignoreCase());
		return (DistrictEntity) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictEntity> getDistrictsById(List<Long> ids) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.in("districtId", ids));
		return (List<DistrictEntity>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictEntity> getDistrictsByStateId(Long stateId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("status", true));
		criteria.createAlias("stateEntity", "se");
		criteria.add(Restrictions.eq("se.stateId", stateId));
		return (List<DistrictEntity>) criteria.list();
	}
}
