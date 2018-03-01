package org.rta.core.dao.vehicle;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehiclePRReleaseEntity;

public interface VehiclePRReleaseDAO extends GenericDAO<VehiclePRReleaseEntity>{
	
	public VehiclePRReleaseEntity getVehiclePRReleaseDetails( String prNumber);
}
