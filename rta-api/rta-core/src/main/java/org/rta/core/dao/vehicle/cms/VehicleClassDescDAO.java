package org.rta.core.dao.vehicle.cms;

import java.util.List;
import java.util.Set;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;

public interface VehicleClassDescDAO extends GenericDAO<VehicleClassDescriptionEntity>{

    public List<VehicleClassDescriptionEntity> getAllClassDes4VehicleCateg(String regCategoryCode,
            String vehicleClassCode, Integer alterationCategory, boolean isNewVehicle);
	
    public List<String> getAllClassDesCode4VehicleCateg(String regCategoryCode, String vehicleClassCode);

    public VehicleClassDescriptionEntity getByVehiclClassDesCode(String vehicleDescClassCode);
    
    public List<VehicleClassDescriptionEntity> getByVehiclClassDesCodeByRegCatCode(Set<String> vehicleDescClassCode,String regCategoryCode);

    /**
     * Get to Vehicle Class Description for multiple vehicle class Codes
     * 
     * @param VehiclClassCodes
     * @return
     */
    public List<VehicleClassDescriptionEntity> getByVehiclClassDescriptoin(Set<String> vehiclClassCodes);
}
