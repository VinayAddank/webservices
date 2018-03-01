package org.rta.core.dao.license;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.LicenseVehicleClassRefEntity;

public interface LicenseVehicleClassRefDAO extends GenericDAO<LicenseVehicleClassRefEntity>{
	
	public LicenseVehicleClassRefEntity getLicenseVehicleClassRef(String vehicleClassCode );

}
