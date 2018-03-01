package org.rta.core.dao.hsrp.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.hsrp.HSRPFeeDetailsDAO;
import org.rta.core.entity.hsrp.HSRPFeeDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HSRPFeeDetailsDAOImpl extends BaseDAO<HSRPFeeDetailsEntity> implements HSRPFeeDetailsDAO {

	public HSRPFeeDetailsDAOImpl() {
		super(HSRPFeeDetailsEntity.class);
	}

	@Override
	public HSRPFeeDetailsEntity getByVehicleRcId(Long vehicleRcId) {
		    Criteria criteria = getSession().createCriteria(getPersistentClass());
	        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
	        return (HSRPFeeDetailsEntity) criteria.uniqueResult();
	}
}
