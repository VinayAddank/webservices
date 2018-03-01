package org.rta.core.service.license;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.SuspDriversLicenseDetailModel;

public interface SuspDriversLicenseDetailService {
	
	public SuspDriversLicenseDetailModel getSuspDriversLicenseDetail(String dlNumber);
	
	public SaveUpdateResponse saveSuspDriversLicenseDetail( SuspDriversLicenseDetailModel model);
}
