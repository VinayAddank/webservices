package org.rta.core.service.report.invoice;

import org.rta.core.model.reports.invoice.VehicleInvoiceModel;

public interface VehicleInvoiceService {

    public VehicleInvoiceModel getCustomerInvoice(long vehicleRcId, String invoiceType);
}
