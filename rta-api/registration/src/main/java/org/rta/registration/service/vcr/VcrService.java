package org.rta.registration.service.vcr;

import org.rta.core.entity.vcr.VcrDetailsEntity;
import org.rta.core.model.payment.tax.VcrResponseModel;



public interface VcrService {
	public VcrResponseModel saveCalculateVcrDetails(String docType, String docNumber);
	public String updateVcrDetails(String docNumber,String transactionNo, String challanNo);
	//public double getTaxOnNotPliedas(VcrDetailsEntity vcrList,Long vehicleRcId,TaxRuleModel taxAmount);
	//public double getTaxOnPliedas(VcrDetailsEntity vcrHistory,Long vehicleRcId, TaxRuleModel taxOfCov,int seatingCapacity)throws ParseException;
	public VcrDetailsEntity getVcrDetails(String prNumber);

}
