package org.rta.core.service.application.specialnumber;

import java.util.List;

import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.master.registration.SpecialNumberModel;
import org.rta.core.model.specialnumber.SpecialNumberFeeModel;

public interface SpecialNumberService {
	
	public SaveUpdateResponse vehicleSpecialNumberFee(SpecialNumberFeeModel specialNumberFeeModel);
	
	public List<SpecialNumberModel> specialNumberList(UserType userRole,Long userId,String rtaOfcId);
	
	public SaveUpdateResponse lockVehicleSpecialNumber(Long userId,String userName,String prNumber,String specialNumber);

}
