package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.LateFeeDetailsHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LateFeeHistoryDAOImpl extends BaseDAO<LateFeeDetailsHistoryEntity> implements LateFeeHistoryDAO{

	public LateFeeHistoryDAOImpl() {
		super(LateFeeDetailsHistoryEntity.class);
	}

}
