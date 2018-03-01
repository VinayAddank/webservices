package org.rta.core.dao.payment.invoice;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.enums.PaymentType;
import org.springframework.stereotype.Repository;

@Repository
public class DealerInvoiceDAOImpl extends BaseDAO<DealerInvoiceEntity> implements DealerInvoiceDAO {

    public DealerInvoiceDAOImpl() {
        super(DealerInvoiceEntity.class);
    }

    @Override
    public DealerInvoiceEntity getByVehicleRcId(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (DealerInvoiceEntity) criteria.uniqueResult();
    }

    public DealerInvoiceEntity getByVehicleRcNdPaymentType(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("paymentType", PaymentType.PAYTAX.getId()));
        return (DealerInvoiceEntity) criteria.uniqueResult();
    }
}
