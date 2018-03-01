package org.rta.core.dao.payment.cms;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.VehRegFeesMasterEntity;

public interface VehRegFeesMasterDAO extends GenericDAO<VehRegFeesMasterEntity> {

    public VehRegFeesMasterEntity getByWeghtCategoryNdVehicleclass(String weightCategory, String vehicleClass,
            String regFeesType);
}
