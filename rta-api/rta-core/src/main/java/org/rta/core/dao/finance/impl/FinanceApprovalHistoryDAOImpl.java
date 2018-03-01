package org.rta.core.dao.finance.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceApprovalHistoryDAO;
import org.rta.core.entity.finance.FinanceApprovalHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceApprovalHistoryDAOImpl extends BaseDAO<FinanceApprovalHistoryEntity>implements FinanceApprovalHistoryDAO {
	public FinanceApprovalHistoryDAOImpl() {
		super(FinanceApprovalHistoryEntity.class);
		// TODO Auto-generated constructor stub
	}
}
