package org.rta.core.dao.payment.registfee;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;

public interface RegFeeDetailDAO extends GenericDAO<RegFeeDetailsEntity> {

    public RegFeeDetailsEntity getByVehicleRcId(long vehicleRcId);

    public List<RegFeeDetailsEntity> getByVehicleRCEntities(List<VehicleRCEntity> vehicleRCEntities);
}
