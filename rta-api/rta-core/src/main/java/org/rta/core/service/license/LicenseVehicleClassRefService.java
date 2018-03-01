package org.rta.core.service.license;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.LicenseVehicleClassRefModel;

public interface LicenseVehicleClassRefService {
	
	public LicenseVehicleClassRefModel getLicenseVehicleClassRef(String vehicleClassCode );
	
	public SaveUpdateResponse saveLicenseVehicleClassRef( LicenseVehicleClassRefModel model);

}
