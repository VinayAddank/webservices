package org.rta.core.dao.license;



import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.LicenseIdpDtlEntity;

public interface LicenceIdpDtlsDAO extends GenericDAO<LicenseIdpDtlEntity> {
	
	public List<LicenseIdpDtlEntity> getLicenseIdpDtl(Long holderId);

}
