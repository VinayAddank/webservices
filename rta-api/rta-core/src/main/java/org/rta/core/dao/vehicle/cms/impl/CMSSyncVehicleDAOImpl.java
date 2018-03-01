package org.rta.core.dao.vehicle.cms.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.cms.CMSSyncVehicleDAO;
import org.rta.core.entity.vehicle.cms.CMSSyncVehicleEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Oct 5, 2016.
 */
@Repository("cmsSyncVehicleDAO")
public class CMSSyncVehicleDAOImpl extends BaseDAO<CMSSyncVehicleEntity> implements CMSSyncVehicleDAO {

    public CMSSyncVehicleDAOImpl() {
        super(CMSSyncVehicleEntity.class);
    }

}
