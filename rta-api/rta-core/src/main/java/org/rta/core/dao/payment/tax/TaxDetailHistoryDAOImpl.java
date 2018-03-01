package org.rta.core.dao.payment.tax;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.TaxDetailHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TaxDetailHistoryDAOImpl extends BaseDAO<TaxDetailHistoryEntity> implements TaxDetailHistoryDAO {

    public TaxDetailHistoryDAOImpl() {
        super(TaxDetailHistoryEntity.class);
    }

}
