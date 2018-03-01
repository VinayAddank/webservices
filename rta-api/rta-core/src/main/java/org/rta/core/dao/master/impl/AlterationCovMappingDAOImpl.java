package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.AlterationCovMappingDAO;
import org.rta.core.entity.master.AlterationCovMappingEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AlterationCovMappingDAOImpl extends BaseDAO<AlterationCovMappingEntity> implements AlterationCovMappingDAO {

	public AlterationCovMappingDAOImpl() {
		super(AlterationCovMappingEntity.class);
	}

	@Override
	public List<String> getMappedAlterationCovListByCode(String code, boolean isTransportVehicle) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if(isTransportVehicle){
			criteria.add(Restrictions.eq("transportCovCode", code));
			criteria.setProjection(Projections.projectionList()
				      .add(Projections.property("nonTransportCovCode"), "nonTransportCovCode"));
		}else{
			criteria.add(Restrictions.eq("nonTransportCovCode", code));
			criteria.setProjection(Projections.projectionList()
				      .add(Projections.property("transportCovCode"), "transportCovCode"));
		}
		
		return criteria.list();
	}
}
