package org.rta.core.dao.payment.cms;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.RTAServiceChargeEntity;

public interface RTAServiceChargeDAO extends GenericDAO<RTAServiceChargeEntity>{

    public RTAServiceChargeEntity getByVehicleClass(String vehicleClassCode);
}
