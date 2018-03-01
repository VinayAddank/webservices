package org.rta.core.dao.finance;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinancerMasterEntity;

public interface FinancerDAO extends GenericDAO<FinancerMasterEntity>{

    public FinancerMasterEntity getByUserId(Long userId);
}
