/**
 * 
 */
package org.rta.core.dao.permit.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.permit.PermitAuthCardDAO;
import org.rta.core.entity.permit.PermitAuthCardDetailsEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class PermitAuthCardDAOImpl extends BaseDAO<PermitAuthCardDetailsEntity> implements PermitAuthCardDAO{

    public PermitAuthCardDAOImpl() {
        super(PermitAuthCardDetailsEntity.class);
    }

    @Override
    public PermitAuthCardDetailsEntity getActiveAuthCard(String permitNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitNo", permitNumber));
        criteria.add(Restrictions.eq("status", Status.OPEN.getValue()));
        return (PermitAuthCardDetailsEntity) criteria.uniqueResult();
    }
    
    @Override
    public PermitAuthCardDetailsEntity getAuthCard(String permitNumber, Status status) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitNo", permitNumber));
        if(null != status) criteria.add(Restrictions.eq("status", status));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (PermitAuthCardDetailsEntity) criteria.uniqueResult();
    }
}
