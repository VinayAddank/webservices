package org.rta.core.service.vehicle.cms.impl;


import org.rta.core.dao.vehicle.cms.CMSSyncVehicleDAO;
import org.rta.core.entity.vehicle.cms.CMSSyncVehicleEntity;
import org.rta.core.model.vehicle.cms.CMSSyncVehicleModel;
import org.rta.core.service.vehicle.cms.CMSSyncVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	@Author sohan.maurya created on Oct 5, 2016.
 */
@Service("cmsSyncVehicleService")
public class CMSSyncVehicleServiceImpl implements CMSSyncVehicleService {

    @Autowired
    private CMSSyncVehicleDAO cmsSyncVehicleDAO;

    @Transactional
    @Override
    public Long save(CMSSyncVehicleModel model) {

        return (Long) cmsSyncVehicleDAO.save(convertToEntity(model));
    }

    private CMSSyncVehicleEntity convertToEntity(CMSSyncVehicleModel model) {

        CMSSyncVehicleEntity cmsSyncVehicleEntity = new CMSSyncVehicleEntity();
        cmsSyncVehicleEntity.setSyncId(model.getSyncId());
        cmsSyncVehicleEntity.setVehicleNumber(model.getVehicleNumber());
        cmsSyncVehicleEntity.setVehicleRcId(model.getVehicleRcId());
        cmsSyncVehicleEntity.setResponseCode(model.getResponseCode());
        cmsSyncVehicleEntity.setMessage(model.getMessage());
        cmsSyncVehicleEntity.setCreatedOn(model.getCreatedOn());
        cmsSyncVehicleEntity.setModifiedOn(model.getModifiedOn());
        cmsSyncVehicleEntity.setServiceCode(model.getServiceCode());
        return cmsSyncVehicleEntity;
    }
}
