package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinanceBranchEntity;

public interface FinanceBranchDAO extends GenericDAO<FinanceBranchEntity> {

	public List<FinanceBranchEntity> getAllBranchesForHead(Long headId,boolean considerStatus, Integer activeStatus);
}
