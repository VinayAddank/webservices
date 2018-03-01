package org.rta.core.dao.customer.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.customer.CustomerVehicleRCDAO;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerVehicleRCDAOImpl extends BaseDAO<VehicleRCEntity> implements CustomerVehicleRCDAO {

    public CustomerVehicleRCDAOImpl() {
		super(VehicleRCEntity.class);
    }

	@Override
	public VehicleRCEntity findByTrNumber(String trnumber) {
		 Criteria criteria = getSession().createCriteria(getPersistentClass());
		 criteria.add(Restrictions.eq("trNumber", trnumber));
		return (VehicleRCEntity) criteria.uniqueResult();
	}

}
