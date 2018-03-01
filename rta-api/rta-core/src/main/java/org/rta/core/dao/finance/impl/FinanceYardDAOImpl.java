package org.rta.core.dao.finance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceYardDAO;
import org.rta.core.entity.finance.FinanceBranchEntity;
import org.rta.core.entity.finance.FinanceYardEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceYardDAOImpl extends BaseDAO<FinanceYardEntity> implements FinanceYardDAO{
	
	public FinanceYardDAOImpl(){
		super(FinanceYardEntity.class);
	}
	
	
	public List<FinanceYardEntity> getAllYardsForHead(Long headId,boolean considerStatus, Integer activeStatus){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("headFinancer.userId", headId));
		if (considerStatus)
			criteria = criteria.add(Restrictions.eq("activeStatus", activeStatus));
		List<FinanceYardEntity> branches = (List<FinanceYardEntity>) criteria.list();
		return branches;
	}
	
	public List<FinanceYardEntity> getAllYardsForBranch(Long branch,boolean considerStatus, Integer activeStatus){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("finnaceBranch.id", branch));
		if (considerStatus)
			criteria = criteria.add(Restrictions.eq("activeStatus", activeStatus));
		List<FinanceYardEntity> branches = (List<FinanceYardEntity>) criteria.list();
		return branches;
	}
}
