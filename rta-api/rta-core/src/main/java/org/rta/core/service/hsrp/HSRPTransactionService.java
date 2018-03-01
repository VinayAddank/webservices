package org.rta.core.service.hsrp;

import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.model.hsrp.HSRPTransactionModel;

public interface HSRPTransactionService {

    public HSRPTransactionModel createHSRPRequest(DealerInvoiceEntity dealerInvoiceEntity, String userName);
}
