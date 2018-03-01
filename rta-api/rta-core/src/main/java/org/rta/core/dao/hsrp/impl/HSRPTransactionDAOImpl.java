package org.rta.core.dao.hsrp.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.hsrp.HSRPTransactionDAO;
import org.rta.core.entity.hsrp.HSRPTransactionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HSRPTransactionDAOImpl extends BaseDAO<HSRPTransactionEntity> implements HSRPTransactionDAO {

    public HSRPTransactionDAOImpl() {
        super(HSRPTransactionEntity.class);
    }

    public HSRPTransactionEntity getByVehicleRcId(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (HSRPTransactionEntity) criteria.uniqueResult();
    }

    @Override
    public HSRPTransactionEntity getByOtherChargesTID(String otherChargesTID) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("otherChargesTID", otherChargesTID));
        return (HSRPTransactionEntity) criteria.uniqueResult();
    }
}
