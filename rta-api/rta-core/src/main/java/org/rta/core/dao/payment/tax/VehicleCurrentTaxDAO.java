package org.rta.core.dao.payment.tax;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface VehicleCurrentTaxDAO extends GenericDAO<VehicleCurrentTaxEntity> {

	public VehicleCurrentTaxEntity getEntityByVehicleRcId(long vehicleRcId);

	public List<VehicleCurrentTaxEntity> getByVehicleRcEntities(List<VehicleRCEntity> vehicleRCEntities);
}
