package org.rta.core.dao.finance.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceHistoryDAO;
import org.rta.core.entity.finance.FinanceHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceHistoryDAOImpl extends BaseDAO<FinanceHistoryEntity> implements FinanceHistoryDAO {

	public FinanceHistoryDAOImpl() {
		super(FinanceHistoryEntity.class);
		// TODO Auto-generated constructor stub
	}
	
	
}
