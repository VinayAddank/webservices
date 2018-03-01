package org.rta.core.service.payment.registfee;

import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;

public interface RegFeeDetailService {

    public RegFeeDetailModel saveFeeDetails(long VehicleRCID, String userName, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicleDetailEntity);
    
    public RegFeeDetailModel viewFeeDetails(long VehicleRCID, String userName,  VehicleDetailsEntity vehicleDetailEntity);
    
    public FeeRuleModel getFeeRuleModel(long VehicleRCID, String userName, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicldDetailsEntity);
    
}
