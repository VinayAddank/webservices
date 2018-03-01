/**
 * 
 */
package org.rta.core.dao.vehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.RCLockDAO;
import org.rta.core.entity.vehicle.RCLockEntity;
import org.rta.core.enums.Status;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class RCLockDAOImpl  extends BaseDAO<RCLockEntity> implements RCLockDAO{
	public RCLockDAOImpl() {
        super(RCLockEntity.class);
    }

	@Override
	public RCLockEntity getLastEntity(String prNumber, Status status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("prNumber", prNumber));
        if(ObjectsUtil.isNotNull(status)){
        	criteria.add(Restrictions.eq("curPrStatus", status.getValue()));
        }
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (RCLockEntity) criteria.uniqueResult();
	}
}