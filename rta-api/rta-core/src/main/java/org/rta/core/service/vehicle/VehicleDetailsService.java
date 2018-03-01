package org.rta.core.service.vehicle;

import org.rta.core.enums.UserType;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.vehicle.SeatingCovValueModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleAlterationUpdateModel;
import org.rta.core.model.vehicle.VehicleBodyModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

public interface VehicleDetailsService {

    public abstract UnregisteredVehicleModel get(Long vehicleRcId);
    
    public String getMakersClass(Long vehicleRcId);

    public VehicleDetailsModel getVehicleDetails(Long vehicleRcId) throws VehicleRcNotFoundException;

    public SaveUpdateResponse saveOrUpdateVehicleAlteration(VehicleBodyModel model, String userName, UserType userType, Boolean isOldVehicle, String token) throws DataIntegrityViolationException, InvalidDataAccessApiUsageException, DataMismatchException;

    public Integer getMinimumSeatingCapacity(Long vehicleRcId);

    public VehicleBodyModel getVehicleAlterationDetails(Long vehicleRcId, UserType userType) throws VehicleRcNotFoundException;

    public SaveUpdateResponse saveOrUpdateVehicleAlteration(VehicleAlterationUpdateModel model, String userName);

	public SaveUpdateResponse vehicleAlterationSyncWithVehicleDetails(Long vehicleRcId, String userName, String token);
	
	public SeatingCovValueModel getSeatingCovValidateValues(String cov);
        
}
