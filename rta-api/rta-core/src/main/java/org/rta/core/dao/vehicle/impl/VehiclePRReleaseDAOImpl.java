package org.rta.core.dao.vehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehiclePRReleaseDAO;
import org.rta.core.entity.vehicle.VehiclePRReleaseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehiclePRReleaseDAOImpl extends  BaseDAO<VehiclePRReleaseEntity> implements VehiclePRReleaseDAO{

	public VehiclePRReleaseDAOImpl() {
		super(VehiclePRReleaseEntity.class);		
	}

	@Override
	public VehiclePRReleaseEntity getVehiclePRReleaseDetails(String prNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("prNumber", prNumber));
        return (VehiclePRReleaseEntity) criteria.uniqueResult();
	}

}
