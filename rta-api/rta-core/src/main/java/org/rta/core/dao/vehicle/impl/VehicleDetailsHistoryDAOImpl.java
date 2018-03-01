package org.rta.core.dao.vehicle.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleDetailsHistoryDAO;
import org.rta.core.entity.vehicle.VehicleDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */

@Repository("vehicleDetailsHistoryDAO")
public class VehicleDetailsHistoryDAOImpl extends BaseDAO<VehicleDetailsHistoryEntity>
        implements VehicleDetailsHistoryDAO {

    public VehicleDetailsHistoryDAOImpl() {
        super(VehicleDetailsHistoryEntity.class);
    }

    @Override
    public String saveData(Long vehicleDtlId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {
        SQLQuery query = (SQLQuery) getSession()
                        .createSQLQuery("SELECT vehicle_details_history_function(:vehicleDtlId, :modifiedBy, :modifiedOn, :serviceCode )")
                        .setParameter("vehicleDtlId", vehicleDtlId).setParameter("modifiedBy", modifiedBy)
                        .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());
        return (String) query.list().get(0);
    }
    
}
