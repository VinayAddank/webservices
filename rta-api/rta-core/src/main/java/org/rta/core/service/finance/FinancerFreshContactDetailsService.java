package org.rta.core.service.finance;

import org.rta.core.entity.finance.FinancerFreshContactDetailsEntity;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.finance.FinancerFreshContactDetailsModel;

public interface FinancerFreshContactDetailsService {

    public FinancerFreshContactDetailsModel getFinancerFreshContactDetails(Long vehicleRcId) throws VehicleRcNotFoundException;

    public SaveUpdateResponse saveFinancerFreshContactDetails(FinancerFreshContactDetailsModel financerFreshContactDetailsModel) throws VehicleRcNotFoundException;
}
