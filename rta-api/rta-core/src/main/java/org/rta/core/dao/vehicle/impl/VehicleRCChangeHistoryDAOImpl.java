package org.rta.core.dao.vehicle.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.entity.vehicle.VehicleRCChangeHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */

@Repository("vehicleRCChangeHistoryDAO")
public class VehicleRCChangeHistoryDAOImpl extends BaseDAO<VehicleRCChangeHistoryEntity>
        implements VehicleRCChangeHistoryDAO {

    public VehicleRCChangeHistoryDAOImpl() {
        super(VehicleRCChangeHistoryEntity.class);
    }

    @Override
    public String saveData(Long vehicleRcId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {
        SQLQuery query = (SQLQuery) getSession()
                        .createSQLQuery("SELECT vehicle_rc_change_history_function(:vehicleRcId, :modifiedBy, :modifiedOn, :serviceCode )")
                        .setParameter("vehicleRcId", vehicleRcId).setParameter("modifiedBy", modifiedBy)
                        .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());
        return (String) query.list().get(0);
    }
}
