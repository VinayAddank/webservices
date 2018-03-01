package org.rta.core.service.certificate;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.FcDetailsModel;
import org.rta.core.model.sync.SyncDataModel;

/**
 *	@Author sohan.maurya created on Nov 11, 2016.
 */

public interface FcDetailsService {

    public String saveOrUpdate(Long vehicleRcId, String userName);

    public FcDetailsModel getFcCertificate(Long vehicleRcId, Long mviUserId);
    
    public SaveUpdateResponse syncData(SyncDataModel syncDataModel, VehicleRCEntity vehicleRCEntity);

    FcDetailsModel getSuspendedFcCertificate(Long vehicleRcId);

	public void generateFinessForVA(VehicleRCEntity vehicleRcEntity, String cov, String mviUserId);
}
