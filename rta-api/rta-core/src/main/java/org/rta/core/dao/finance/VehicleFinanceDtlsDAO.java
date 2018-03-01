package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;



public interface VehicleFinanceDtlsDAO extends GenericDAO<VehicleFinanceDtlstEntity>{
	
	public VehicleFinanceDtlstEntity getRcrdForVehicleRc(Long vehicleRcId);
	public VehicleFinanceDtlstEntity getVehicleFinanceRcr(Long vehicleRcId,Integer status);
    public List<VehicleFinanceDtlstEntity> getByVehicleRcId(Long vehicleRcId);

}
