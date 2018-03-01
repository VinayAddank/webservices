package org.rta.core.service.payment.invoice;

import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.entity.payment.tax.LifeTaxEntity;

public interface DealerInvoiceService {

    public DealerInvoiceEntity createInvoice(long vehicleRcID, String userName);

    public DealerInvoiceEntity secondVehicleTaxInvoice(long vehicleRcId, String userName);
}
