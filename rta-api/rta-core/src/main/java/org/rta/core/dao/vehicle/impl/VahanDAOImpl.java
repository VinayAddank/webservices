package org.rta.core.dao.vehicle.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.entity.vehicle.VahanEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VahanDAOImpl extends BaseDAO<VahanEntity> implements VahanDAO {

    public VahanDAOImpl() {
        super(VahanEntity.class);
    }

    @Override
    public VahanEntity getByChassisNumber(String chassisNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("chassisNumber", chassisNumber));
        return (VahanEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VahanEntity> getVahanEntitiesByChassisNumber(Set<String> chassisNumbers) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("chassisNumber", chassisNumbers));
        return criteria.list();
    }
    
}
