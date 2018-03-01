package org.rta.core.dao.vehicle.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDetailsDAOImpl extends BaseDAO<VehicleDetailsEntity> implements VehicleDetailsDAO {

    public VehicleDetailsDAOImpl() {
        super(VehicleDetailsEntity.class);
    }

    @Override
    public VehicleDetailsEntity getByVehicleRcId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (VehicleDetailsEntity) criteria.uniqueResult();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleDetailsEntity> getByVehicleRcIds(List<Long> vehicleRcIds) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRcId.vehicleRcId", vehicleRcIds));
        return (List<VehicleDetailsEntity>) criteria.list();

    }

    @Override
    public String getMakersClass(Long vehicleRcId) {

        return (String) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId))
                .setProjection(Projections.property("makerClass")).uniqueResult();
    }

    @Override
    public VehicleDetailsEntity getByChassisNo(String chassisNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("chassisNo", chassisNumber));
        return (VehicleDetailsEntity) criteria.uniqueResult();
    }
    
    @Override
    public VehicleDetailsEntity getVehicleDtlsByPrNumber(String prNumber) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("vehicleRcId", "vehicleRc");
        criteria.add(Restrictions.eq("vehicleRc.prNumber", prNumber));
        return (VehicleDetailsEntity) criteria.uniqueResult();
    }
}
