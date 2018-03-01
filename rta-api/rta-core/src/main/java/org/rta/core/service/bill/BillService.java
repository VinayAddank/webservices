package org.rta.core.service.bill;

import org.rta.core.model.vehicle.BillingDetailsModel;

public interface BillService {

    public abstract Long save(BillingDetailsModel billModel);
    
}