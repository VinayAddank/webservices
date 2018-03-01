package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.FitnessFeeHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FitnessFeeHistoryDAOImpl extends BaseDAO<FitnessFeeHistoryEntity> implements FitnessFeeHistoryDAO {

    public FitnessFeeHistoryDAOImpl() {
        super(FitnessFeeHistoryEntity.class);
    }


}
