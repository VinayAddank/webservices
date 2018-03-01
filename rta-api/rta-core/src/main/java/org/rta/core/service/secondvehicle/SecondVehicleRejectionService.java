package org.rta.core.service.secondvehicle;

import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.secondVechicle.SecondVehicleDetailsModel;

public interface SecondVehicleRejectionService {

    public void saveDetails(SecondVehicleDetailsModel secondVehicleDeatils, Long vehicleRcId, String username,
            Long userId, Integer userType);

    public SecondVehicleDetailsModel get(Long secondVehicleId);

    public SecondVehicleDetailsModel get(Long secondVehicleId, Integer iteration);
    
    public SaveUpdateResponse secondVehiclePaymentSkip(Long vehicleRcID, Boolean isNocApplicable);

}
