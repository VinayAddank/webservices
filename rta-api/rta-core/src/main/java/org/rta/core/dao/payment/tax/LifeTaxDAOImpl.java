package org.rta.core.dao.payment.tax;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.LifeTaxEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LifeTaxDAOImpl extends BaseDAO<LifeTaxEntity> implements LifeTaxDAO {

	public LifeTaxDAOImpl() {
		super(LifeTaxEntity.class);
	}

	@Override
	public LifeTaxEntity getEntityByVehicleRcId(long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (LifeTaxEntity) criteria.uniqueResult();
	}

}
