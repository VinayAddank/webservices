package org.rta.core.dao.payment.tax;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.PeriodicTaxEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PeriodicTaxDAOImpl extends BaseDAO< PeriodicTaxEntity> implements  PeriodicTaxDAO{

	public PeriodicTaxDAOImpl() {
		super( PeriodicTaxEntity.class);
	}

	@Override
	public PeriodicTaxEntity getEntityByVehicleRcId(Long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        criteria.addOrder(Order.desc("taxValidFrom"));
        criteria.setMaxResults(1);
        return (PeriodicTaxEntity) criteria.uniqueResult();
	}

}
