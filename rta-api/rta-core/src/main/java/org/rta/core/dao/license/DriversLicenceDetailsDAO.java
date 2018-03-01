package org.rta.core.dao.license;


import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.DriversLicenceDetailsEntity;

public interface DriversLicenceDetailsDAO extends GenericDAO<DriversLicenceDetailsEntity> {

	public List<DriversLicenceDetailsEntity> getDriversLicenceDetails(String llOrDlOrApplicationNumber);
	
	public List<DriversLicenceDetailsEntity> getDriverPermitDtl(Long pk);
	
	public List<DriversLicenceDetailsEntity> getDriverPermitDtl(String dlNo,String rtaOfficeCode);
	
	public DriversLicenceDetailsEntity getLastAppliedBadgeDetails();
	
	public DriversLicenceDetailsEntity getDriversLicenceDetails(String vehicleClass, Long licenceHolderId);

}
