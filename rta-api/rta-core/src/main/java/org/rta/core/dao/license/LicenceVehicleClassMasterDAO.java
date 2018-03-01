package org.rta.core.dao.license;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.licence.LicenseVehicleClassMasterEntity;

public interface LicenceVehicleClassMasterDAO extends GenericDAO<LicenseVehicleClassMasterEntity>{

	public List<LicenseVehicleClassMasterEntity> getVehicleTransportType(String covcode);
}
