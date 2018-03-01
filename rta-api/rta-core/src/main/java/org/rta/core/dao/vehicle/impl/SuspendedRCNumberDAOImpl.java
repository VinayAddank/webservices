package org.rta.core.dao.vehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.SuspendedRCNumberDAO;
import org.rta.core.entity.vehicle.SuspendedRCNumbersEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

@Repository
public class SuspendedRCNumberDAOImpl extends BaseDAO<SuspendedRCNumbersEntity> implements SuspendedRCNumberDAO {

    public SuspendedRCNumberDAOImpl() {
        super(SuspendedRCNumbersEntity.class);
    }

    @Override
    public SuspendedRCNumbersEntity getDetails(String prNumber, Boolean isRevoked, Status status) {
        Criteria criteria = getSession().createCriteria(SuspendedRCNumbersEntity.class);
        criteria.add(Restrictions.eq("prNumber", prNumber));
        criteria.add(Restrictions.eq("status", status.getValue()));
        criteria.add(Restrictions.eq("isRevoked", isRevoked));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (SuspendedRCNumbersEntity) criteria.uniqueResult();
    }

    @Override
    public SuspendedRCNumbersEntity getDetails(String prNumber, Status status) {
        Criteria criteria = getSession().createCriteria(SuspendedRCNumbersEntity.class);
        criteria.add(Restrictions.eq("prNumber", prNumber));
        criteria.add(Restrictions.eq("status", status.getValue()));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (SuspendedRCNumbersEntity) criteria.uniqueResult();
    }
    
}
