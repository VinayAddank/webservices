package org.rta.core.dao.payment.invoice;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;

public interface DealerInvoiceDAO extends GenericDAO<DealerInvoiceEntity> {

    public DealerInvoiceEntity getByVehicleRcId(long vehicleRcId);
}
