package org.rta.core.dao.hsrp;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.hsrp.HSRPDetailEntity;
import org.rta.core.model.hsrp.HSRPDetailModel;

public interface HSRPDetailDAO extends GenericDAO<HSRPDetailEntity> {

    public HSRPDetailEntity getByAuthRefNo(HSRPDetailModel hsrpDetailModel);

    public HSRPDetailEntity getByVehicleRcId(Long vehicleRcId);
    
    public List<HSRPDetailEntity> getAllOpenStatusData(Long from , Long to);
    
    public List<HSRPDetailEntity> getAllTRPostData(Long from , Long to);

}
