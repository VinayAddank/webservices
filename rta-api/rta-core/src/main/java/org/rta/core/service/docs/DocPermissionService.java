package org.rta.core.service.docs;

import java.util.List;

import org.rta.core.enums.UserType;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.model.user.UserModel;

/**
 *	@Author sohan.maurya created on Aug 25, 2016.
 */
public interface DocPermissionService {
    /**
     * get doc type for a vehicle
     * 
     * @param vehicleRcId
     * @return
     */
    public List<DocTypesModel> getDocTypes(Long vehicleRcId, UserModel userModel, boolean isOldVehicle);

    /**
     * get Mandatory doc Ids
     * 
     * @param vehicleRcId
     * @return
     */
    public List<Integer> getMandatoryDocTypesId(Long vehicleRcId, UserType userType);

    /**
     * Get Form docs
     * 
     * @param vehicleRcId
     * @return
     */
    public List<DocTypesModel> getFormDocs(Long vehicleRcId);
}
