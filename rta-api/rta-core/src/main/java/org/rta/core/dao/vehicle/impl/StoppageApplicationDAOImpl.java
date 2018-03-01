/**
 * 
 */
package org.rta.core.dao.vehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.StoppageApplicationDAO;
import org.rta.core.entity.vehicle.StoppageApplicationEntity;
import org.springframework.stereotype.Repository;

/**
 * @author sohan.maurya
 *
 */
@Repository
public class StoppageApplicationDAOImpl extends BaseDAO<StoppageApplicationEntity> implements StoppageApplicationDAO{

	public StoppageApplicationDAOImpl() {
		super(StoppageApplicationEntity.class);
	}

	@Override
	public StoppageApplicationEntity getStoppageApplication(String prNumber) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("regnNo", prNumber));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
		return (StoppageApplicationEntity) criteria.uniqueResult();
	}
	
}
