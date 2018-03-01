package org.rta.core.dao.vehicle.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.VehicleRCMigrationDAO;
import org.rta.core.entity.vehicle.VehicleRCMigrationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleRCMigrationDAOImpl extends BaseDAO<VehicleRCMigrationEntity> implements VehicleRCMigrationDAO{

    public VehicleRCMigrationDAOImpl() {
        super(VehicleRCMigrationEntity.class);
    }

}
