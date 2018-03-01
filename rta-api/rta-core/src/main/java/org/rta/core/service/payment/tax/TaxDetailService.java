package org.rta.core.service.payment.tax;

import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.tax.ApplicationTaxModel;
import org.rta.core.model.payment.tax.CessFeeModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.payment.tax.TaxModel;
import org.rta.core.model.payment.tax.TaxRuleModel;

public interface TaxDetailService {

    public TaxDetailModel saveTaxDetails(long VehicleRCID, String userName, TaxRuleModel taxRuleModel , FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicleDetailEntity , VehicleBillingDetailsEntity vehicleBillingDetailsEntity);

    public TaxRuleModel getTaxRuleModel(long VehicleRCID, String userName, TaxRuleModel taxRuleModel , VehicleDetailsEntity vehicleDetailsEntity , VehicleBillingDetailsEntity vehicleBillingDetailsEntity);

	public TaxDetailModel secondVehicleTaxCal(long vehicleRcId, String userName);

    public TaxDetailModel isPaymentVerification(Long vehicleRcId, TaxDetailModel taxDetailModel);
    
    public ApplicationTaxModel getAllTaxDetails(String trOrPrNumber);
    
    public SaveUpdateResponse syncPayTaxData(TaxModel taxModel);
    
    public TaxDetailModel viewTaxDetails(long VehicleRCID, String userName , VehicleDetailsEntity vehicleDetailEntity , VehicleBillingDetailsEntity vehicleBillingDetailsEntity);

    public CessFeeModel cessFeeDetails(VehicleDetailsEntity vehicleDetailEntity, String userName, TaxRuleModel taxRuleModel);
    
    public CessFeeModel viewCessFeeDetails(VehicleDetailsEntity vehicleDetailEntity);

	public String saveOrUpdateDifferentialTaxFeeDetails(DifferentialTaxFeeModel differentialTaxFeeModel, Long vehicleRcId, String userName, String cov);
	
	public Boolean getLifeTax(long vehicleRcId);

	public TaxModel getCurrentTaxDetails(Long vehicleRcId);
}
