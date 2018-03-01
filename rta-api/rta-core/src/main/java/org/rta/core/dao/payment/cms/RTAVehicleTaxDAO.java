package org.rta.core.dao.payment.cms;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.RTAVehicleTaxEntity;

public interface RTAVehicleTaxDAO extends GenericDAO<RTAVehicleTaxEntity> {

    public RTAVehicleTaxEntity getVehicleWgtCatgFrmWgt(Long wgt, String vehicleCode);
}
