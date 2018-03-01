package org.rta.core.dao.vehicle.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleAlterationDAO;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.enums.Status;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 5, 2017.
 */

@Repository("vehicleAlterationDAO")
public class VehicleAlterationDAOImpl extends BaseDAO<VehicleAlterationEntity> implements VehicleAlterationDAO {

    public VehicleAlterationDAOImpl() {
        super(VehicleAlterationEntity.class);
    }

//    @Override
//    public VehicleAlterationEntity getVehicleAlterationDetails(Long vehicleRcId) {
//        Criteria criteria = getSession().createCriteria(getPersistentClass());
//        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
//        return (VehicleAlterationEntity) criteria.uniqueResult();
//    }
    
	@Override
    public VehicleAlterationEntity getVehicleAlterationDetailByAlterCat(Long vehicleRcId, int alterationCategory, Status status ) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("alterationCategory", alterationCategory));
        criteria.add(Restrictions.eq("status", status.getValue()));
        return (VehicleAlterationEntity) criteria.uniqueResult();	
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAlterationEntity> getVehicleAlterationDetails(Long vehicleRcId, Status status) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("status", status.getValue()));
        return criteria.list();
	}

	@Override
	public void updateStatus(Long vehicleRcId, Status status) {
		Query query = getSession().createQuery("UPDATE VehicleAlterationEntity set status = :status WHERE vehicleRcId.vehicleRcId = :vehicleRcId");
		query.setParameter("status", status.getValue());
		query.setParameter("vehicleRcId", vehicleRcId);
		int result = query.executeUpdate();
		System.out.println("Rows affected: " + result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VehicleAlterationEntity> getVehicleAlterationDetails(Long vehicleRcId, Status status, List<Integer> list) {
		
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("status", status.getValue()));
        criteria.add(Restrictions.in("alterationCategory", list));
        return criteria.list();
	}

	@Override
	public Integer getMaxRequestIdValueByRcId(Long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
		criteria.setProjection(Projections.max("requestId"));
		Integer maxRequestId = (Integer) criteria.uniqueResult();
		return maxRequestId;
	}

}
