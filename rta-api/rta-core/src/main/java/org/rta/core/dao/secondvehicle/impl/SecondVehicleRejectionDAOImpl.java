package org.rta.core.dao.secondvehicle.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.secondvehicle.SecondVehicleRejectionDAO;
import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SecondVehicleRejectionDAOImpl extends BaseDAO<SecondVehicleDetailsEntity>
        implements SecondVehicleRejectionDAO {

    public SecondVehicleRejectionDAOImpl() {
        super(SecondVehicleDetailsEntity.class);
    }

    @Override
    public SecondVehicleDetailsEntity getSecondVehicleDetailsByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        return (SecondVehicleDetailsEntity) criteria.uniqueResult();
    }

    @Override
    public SecondVehicleDetailsEntity getSecondVehicleDetails(Long vehicleRcId, Integer iteration) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("iteration", iteration));
        return (SecondVehicleDetailsEntity) criteria.uniqueResult();
    }
}
