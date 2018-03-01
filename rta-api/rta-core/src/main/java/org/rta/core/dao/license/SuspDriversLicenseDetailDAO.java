package org.rta.core.dao.license;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.SuspDriversLicenseDetailEntity;
import org.rta.core.entity.licence.SuspDriversLicenseDtlPK;

public interface SuspDriversLicenseDetailDAO extends GenericDAO<SuspDriversLicenseDetailEntity> {

	
	public SuspDriversLicenseDetailEntity getSuspDriversLicenseDetail(SuspDriversLicenseDtlPK pk);
}
