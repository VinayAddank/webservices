package org.rta.core.dao.vehicle.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.ApplicationSlotsDAO;
import org.rta.core.entity.vehicle.ApplicationSlotsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ApplicationSlotsDAOImpl extends BaseDAO<ApplicationSlotsEntity> implements ApplicationSlotsDAO {

    public ApplicationSlotsDAOImpl() {
        super(ApplicationSlotsEntity.class);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<ApplicationSlotsEntity> getApplicationsByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        return (List<ApplicationSlotsEntity>)criteria.list();
    }

}
