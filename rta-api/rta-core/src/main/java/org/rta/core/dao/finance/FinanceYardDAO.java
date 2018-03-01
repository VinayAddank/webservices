package org.rta.core.dao.finance;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;

import org.rta.core.entity.finance.FinanceYardEntity;

public interface FinanceYardDAO extends GenericDAO<FinanceYardEntity> {
	public List<FinanceYardEntity> getAllYardsForHead(Long headId,boolean considerStatus, Integer activeStatus);
	public List<FinanceYardEntity> getAllYardsForBranch(Long branch,boolean considerStatus, Integer activeStatus);
}
