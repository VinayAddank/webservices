package org.rta.registration.service.vahan;

import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.model.vehicle.RegisteredVehicleModel;

public interface VahanService {

    /**
     * Fetch vehicle details based on chassis number.
     * 
     * @param chassisNumber
     * @return VehicleModel, null if unsuccessful to get VehicleDetails
     */
    public abstract RegisteredVehicleModel getVehicleInfo(String chassisNumber);

    /**
     * Fetch vehicle details based on chassis number.</br>
     * It also validates the engine number with the fetched engine number.
     * 
     * @param chassisNumber
     * @param engineNumber
     * @return VehicleModel, null if unsuccessful to get VehicleDetails
     */
    public abstract RegisteredVehicleModel getVehicleDetails(String chassisNumber, String engineNumber)
            throws InvalidEngineNumberException;

}
