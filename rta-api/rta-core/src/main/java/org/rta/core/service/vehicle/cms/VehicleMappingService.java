package org.rta.core.service.vehicle.cms;

import java.util.List;

import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.rta.core.model.vehicle.cms.VehicleClassModel;

public interface VehicleMappingService {
	public List<VehicleClassModel> getAllVehicleClass();

    public List<VehicleClassDescModel> getAllVehicleClassDescription(String regCategoryCode, String vehicleClassCatg);

    public TaxTypeModel getTaxType4VehicleSubClass( String vehicleClassCode, Integer seatingCapacity);

    List<VehicleClassDescModel> getVehicleClassDesc(String regCategoryCode, String classCode, Integer alterationCategory, boolean isNewVehicle);

    public VehicleClassDescModel getVehicleClassDesc(String prNumber);

}
