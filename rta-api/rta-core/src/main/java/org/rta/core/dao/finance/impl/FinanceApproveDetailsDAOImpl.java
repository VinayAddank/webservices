package org.rta.core.dao.finance.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceApproveDetailsDAO;
import org.rta.core.entity.finance.FinanceApproveDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceApproveDetailsDAOImpl extends BaseDAO<FinanceApproveDetailsEntity> implements FinanceApproveDetailsDAO{

	public FinanceApproveDetailsDAOImpl() {
		super(FinanceApproveDetailsEntity.class);
		// TODO Auto-generated constructor stub
	}

}
