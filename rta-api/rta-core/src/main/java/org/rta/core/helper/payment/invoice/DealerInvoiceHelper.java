package org.rta.core.helper.payment.invoice;

import java.util.Collection;

import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.payment.invoice.DealerInvoiceModel;
import org.springframework.stereotype.Component;

@Component
public class DealerInvoiceHelper implements BaseConverter<DealerInvoiceEntity, DealerInvoiceModel> {

    @Override
    public DealerInvoiceModel convertToModel(DealerInvoiceEntity source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DealerInvoiceEntity convertToEntity(DealerInvoiceModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DealerInvoiceModel> convertToModelList(Collection<DealerInvoiceEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DealerInvoiceEntity> convertToEntityList(Collection<DealerInvoiceModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
