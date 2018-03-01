package org.rta.core.dao.vehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleBharatStageDAO;
import org.rta.core.entity.vehicle.VehicleBharatStageEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleBharatStageDAOImpl extends BaseDAO<VehicleBharatStageEntity> implements VehicleBharatStageDAO {

    public VehicleBharatStageDAOImpl() {
        super(VehicleBharatStageEntity.class);
    }

    @Override
    public VehicleBharatStageEntity getByChassisNumber(String chassisNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("chassisNumber", chassisNumber));
        criteria.add(Restrictions.eq("isMigrated", false));
        return (VehicleBharatStageEntity) criteria.uniqueResult();
    }

    @Override
    public VehicleBharatStageEntity getByChassisAndEngineNumber(String chassisNumber, String engineNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("chassisNumber", chassisNumber));
        criteria.add(Restrictions.eq("engineNo", engineNo));
        criteria.add(Restrictions.eq("isMigrated", false));
        return (VehicleBharatStageEntity) criteria.uniqueResult();
    }    
}
