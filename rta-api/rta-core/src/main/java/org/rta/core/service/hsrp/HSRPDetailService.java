package org.rta.core.service.hsrp;

import java.util.List;

import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.rta.core.model.hsrp.HSRPRTADetailModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.HSRPModel;

public interface HSRPDetailService {

	public SaveUpdateResponse confirmPaymentOfHSRP(HSRPDetailModel hsrpDetailModel);

	public SaveUpdateResponse updateHSRPLaserCodes(HSRPDetailModel hsrpDetailModel);

	public SaveUpdateResponse notifyAffixation(HSRPDetailModel hsrpDetailModel);

	public SaveUpdateResponse confirmAffixation(HSRPDetailModel hsrpDetailModel);

	public void saveHSRPDetail(Long vehicleRcId, String userName);

	public void updateHSRPTRStatus(Long vehicleRcId, Integer status, String message);

	public void updateHSRPPRStatus(Long vehicleRcId, Integer status, String message);

	public HSRPRTADetailModel getTRData(HSRPDetailModel hsrpDetailModel);

	public List<HSRPDetailModel> getAllOpenStatusData(Long from , Long to);

	public List<HSRPDetailModel> getAllTRPostData(Long from , Long to);

	public HSRPRTADetailModel getPRData(HSRPDetailModel hsrpDetailModel);
	
	public HSRPModel saveHSRP(String userName, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicleDetailEntity);
	
	public HSRPModel viewHSRP(String userName, VehicleDetailsEntity vehicleDetailEntity);
}
