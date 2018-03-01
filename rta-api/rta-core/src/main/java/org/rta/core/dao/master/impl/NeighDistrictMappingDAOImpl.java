package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.NeighDistrictMappingDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.NeighDistrictMappingEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NeighDistrictMappingDAOImpl extends BaseDAO<NeighDistrictMappingEntity> implements NeighDistrictMappingDAO {

	@Autowired
	public DistrictDAO districtDAO ;
	
	public NeighDistrictMappingDAOImpl() {
		super(NeighDistrictMappingEntity.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DistrictEntity> getAllNeighbouring(Long districtId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("districtId", districtId));
		criteria.setProjection(Projections.property("neighDistrictId"));
		List<Long> list = (List<Long>) criteria.list();
		return districtDAO.getDistrictsById(list);
	}

}
