package org.rta.core.service.vehicle;

import org.rta.core.model.vehicle.RegisteredVehicleModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;

public interface VahanServiceCore {

    public Long save(UnregisteredVehicleModel vehicleModel);
    public Long save(RegisteredVehicleModel vehicleModel);
    public UnregisteredVehicleModel getByChassisNumber(String chassisNumber);
    
}
