package org.rta.core.dao.vehicle.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleBillingDetailsDAOImpl extends BaseDAO<VehicleBillingDetailsEntity>
        implements VehicleBillingDetailsDAO {

    public VehicleBillingDetailsDAOImpl() {
        super(VehicleBillingDetailsEntity.class);
    }

    @Override
    public VehicleBillingDetailsEntity get(Long vehicleBillingDetails) {
        return (VehicleBillingDetailsEntity) getSession().get(VehicleBillingDetailsEntity.class,
                (Long) vehicleBillingDetails);
    }

    public VehicleBillingDetailsEntity getByVehicleDetailId(VehicleDetailsEntity vehicleDetails) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleDtlId.vehicleDtlId", vehicleDetails.getVehicleDtlId()));
        return (VehicleBillingDetailsEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleBillingDetailsEntity> getByVehicleBillingIds(List<Long> ids) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleDtlId.vehicleDtlId", ids));
        return (List<VehicleBillingDetailsEntity>) criteria.list();
    }

}
