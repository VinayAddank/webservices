package org.rta.core.service.master;

import java.util.List;

import org.rta.core.model.master.RegistrationCategoryModel;

public interface RegistrationCategoryService {

    public abstract List<RegistrationCategoryModel> getAll();
    
    public abstract RegistrationCategoryModel get(Long registrationCategoryId);

	public RegistrationCategoryModel getRegistrationCategoryDetails(Long vehicleRcId);
	
	public Boolean getIsVehicleReassignmentApplicable(String prNumber);
}
