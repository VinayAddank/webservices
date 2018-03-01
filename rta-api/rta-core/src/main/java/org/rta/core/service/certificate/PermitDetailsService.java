package org.rta.core.service.certificate;

import java.util.List;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.PermitDetailsModel;
import org.rta.core.model.permit.PermitHeaderModel;
import org.rta.core.model.sync.SyncDataModel;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
public interface PermitDetailsService {

    public SaveUpdateResponse saveOrUpdate(PermitDetailsModel permitDetailsModel, String userName);

    public PermitDetailsModel getPermitDetails(Long vehicleRcId);

    public PermitDetailsModel getPermitCertificate(Long vehicleRcId, Long mviUserId);
    
    public SaveUpdateResponse syncData(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity);
    
    public List<PermitHeaderModel> getPermitHeaderDetails(Long vehicleRcId) throws VehicleRcNotFoundException, NotFoundException;

    public PermitDetailsModel getPermitCertifcate(Long vehicleRcId, Long mviUserId);
    

    public PermitDetailsModel getTempPermitCertifcate(Long vehicleRcId, Long mviUserId);

	List<PermitHeaderModel> getPermitHeaderDetails(Long vehicleRcId, String permitType)
			throws VehicleRcNotFoundException, NotFoundException;

}
