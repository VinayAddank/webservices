package org.rta.core.dao.finance.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceBranchEmployeeDAO;
import org.rta.core.entity.finance.FinanceBranchEmployeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceBranchEmployeeDAOImpl extends BaseDAO<FinanceBranchEmployeeEntity> implements FinanceBranchEmployeeDAO  {
		public FinanceBranchEmployeeDAOImpl(){
			super(FinanceBranchEmployeeEntity.class);
		}
}
