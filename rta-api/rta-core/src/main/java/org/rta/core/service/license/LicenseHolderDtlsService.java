package org.rta.core.service.license;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.license.ForgetLicenseModel;
import org.rta.core.model.license.LicenseHolderDtlsModel;
import org.rta.core.model.license.LicenseHolderPermitDetails;

public interface LicenseHolderDtlsService {
	
	public LicenseHolderPermitDetails getLicenseHolderDtls(String aadharNo, Long licenceHolderId, String llrNumber) throws NotFoundException;
	
	public SaveUpdateResponse saveLicenseHolderDtls( LicenseHolderDtlsModel model, String userName );

	public SaveUpdateResponse saveOnlyLicenseHolderDtlsAndPermitDetails(LicenseHolderPermitDetails models, String userName) throws Exception;
	
	public LicenseHolderPermitDetails getDriverLicenseHolderDetails(String aadharNo, String dlNumber) throws NotFoundException;

	public LicenseHolderPermitDetails getDriverLicenseHolderDetails( String uniqueKey) throws NotFoundException;

	public SaveUpdateResponse updateLicenseHodlerDetails(LicenseHolderDtlsModel model, String userName) throws DataMismatchException;

	public List<String> getLicensePermitVehicle(String aadharNumber, String showVehicleClass) throws NotFoundException;
	
	public List<DriversLicenceDetailsModel> getDriverLicensePermitDetails(String dlNo, String rtaOfficeCode) throws NotFoundException;

	public LicenseHolderDtlsModel getLicenseHolderDetails(String aadhaarNumber, String passportNumber) throws NotFoundException;

	public String getReferenceId(Long licenceHolderId, String rtaOfficeCode, String dlType);

	public String updateInLicenceHodlerDetails(LicenseHolderDtlsModel model, String userName, String serviceCode) throws DataMismatchException;

	public LicenseHolderPermitDetails forgotLicenceNumber(ForgetLicenseModel forgetLicenseModel) throws NotFoundException,ParseException;
	
}
