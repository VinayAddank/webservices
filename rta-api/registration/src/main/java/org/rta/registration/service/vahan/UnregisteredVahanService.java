package org.rta.registration.service.vahan;

import org.rta.core.exception.BSIIIVehicleException;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.FoundException;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;

public interface UnregisteredVahanService {

    public abstract UnregisteredVehicleModel getChassisInfo(String chassisNumber, String engineNumber , boolean migration) throws InvalidEngineNumberException, ConflictException, FoundException, BSIIIVehicleException;
    
    public UnregisteredVehicleModel saveMIgrate(UnregisteredVehicleModel unregisteredVehicleModel);
}
