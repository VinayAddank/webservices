package org.rta.core.dao.payment.cms;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.VehicleWgtMasterEntity;

public interface VehicleWgtDAO extends GenericDAO<VehicleWgtMasterEntity>{
	
	public VehicleWgtMasterEntity getVehicleWgtCatgFrmWgt(Long wgt);

}
