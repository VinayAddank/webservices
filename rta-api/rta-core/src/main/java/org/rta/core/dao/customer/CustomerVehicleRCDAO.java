package org.rta.core.dao.customer;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface CustomerVehicleRCDAO extends GenericDAO<VehicleRCEntity> {

public VehicleRCEntity findByTrNumber(String trnumber);

}
