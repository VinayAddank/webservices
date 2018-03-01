package org.rta.core.dao.license;

import java.text.ParseException;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.model.license.ForgetLicenseModel;

public interface LicenseHolderDtlsDAO extends GenericDAO<LicenseHolderDtlsEntity>{

	public LicenseHolderDtlsEntity getLicenseHolderDtls(String aadharNo );
	
	public LicenseHolderDtlsEntity getLicenseHolderDtlsByPassportNumber(String passportNumber );

//	public LicenseHolderDtlsEntity getLicenseHolderDtls(Date dob, String aadharNo);
	
	public LicenseHolderDtlsEntity getLicenseHolderDtlsByHolderId(Long holderId);

	public LicenseHolderDtlsEntity getLicenseHolderDtls(ForgetLicenseModel forgetLicenseModel) throws ParseException;
	
}
