package org.rta.core.service.license;

import java.util.List;

import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.license.LicenseHolderPermitDetails;
import org.rta.core.model.license.LicenseIdpDtlModel;
import org.rta.core.model.license.SupensionCancellationModel;
import org.rta.core.model.license.SuspensionRevocationModel;

public interface DriversLicenceDetailsService {

	public List<DriversLicenceDetailsModel> getDriverDtls(String aadharNo, String llOrdlNumber)	throws NotFoundException;

	public SaveUpdateResponse saveDriverPermitDetails(LicenseHolderPermitDetails licenseHolderPermitDetails, String aadharNo, String userName);

	public SaveUpdateResponse addDriverPermitDetails(LicenseHolderPermitDetails model, String userName);

	public SaveUpdateResponse addDriverPermitDetails(List<DriversLicenceDetailsModel> models, String aadharNumber,	String userName);

	public SaveUpdateResponse updateDriverLicenceCommonService(String aadharNumber, List<DriversLicenceDetailsModel> models, String userName);

	public String getEmployeeSignature(Long userId);
	
	public SaveUpdateResponse saveInternationalDriverPermitDetails(LicenseIdpDtlModel licenseIdpDtlModel, String aadharNo, String userName);
	
	public SaveUpdateResponse suspendCancelLicense(SupensionCancellationModel supensionCancellationModel, String dLNumber, String userName);

	public SaveUpdateResponse licenseRevokeSuspension(SuspensionRevocationModel suspensionRevocationModel, String dLNumber, String userName);

}
