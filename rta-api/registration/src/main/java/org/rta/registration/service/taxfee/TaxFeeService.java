package org.rta.registration.service.taxfee;

import org.rta.core.model.payment.TaxFeeDetailModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.tax.TaxRuleModel;

public interface TaxFeeService {

    public TaxRuleModel getTaxByRule(TaxRuleModel taxRuleModel);
    
    public FeeRuleModel getFeeByRule(FeeRuleModel feeRuleModel);
    
    public TaxFeeDetailModel getTaxFee(Long vehicleRcId , String userName);
    
    public TaxFeeDetailModel viewTaxFee(Long vehicleRcId , String userName, String token);

}
