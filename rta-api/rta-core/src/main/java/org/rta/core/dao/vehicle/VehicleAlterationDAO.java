package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleAlterationEntity;
import org.rta.core.enums.Status;

/**
 * @Author sohan.maurya created on Jan 5, 2017.
 */

public interface VehicleAlterationDAO extends GenericDAO<VehicleAlterationEntity> {

	//public VehicleAlterationEntity getVehicleAlterationDetails(Long vehicleRcId);

	public VehicleAlterationEntity getVehicleAlterationDetailByAlterCat(Long vehicleRcId,int alterationCategory, Status status);
	
	public List<VehicleAlterationEntity> getVehicleAlterationDetails(Long vehicleRcId, Status status);
	
	public void updateStatus(Long vehicleRcId, Status status);
	
	public Integer getMaxRequestIdValueByRcId(Long vehicleRcId);

	public List<VehicleAlterationEntity> getVehicleAlterationDetails(Long vehicleRcId, Status fresh, List<Integer> list);
}
