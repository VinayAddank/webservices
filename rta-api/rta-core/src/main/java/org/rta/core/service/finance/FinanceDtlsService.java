package org.rta.core.service.finance;

import java.util.List;

import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FinancerModel;


public interface FinanceDtlsService {
	public List<FinancerModel> getFinancers();
	public SaveUpdateResponse saveUpdateFinanceDtls(FinanceModel model);
	public FinanceModel getFinanceDtlsByVehicleRcId(Long vehicleRcId,UserType userRole);
	
}
