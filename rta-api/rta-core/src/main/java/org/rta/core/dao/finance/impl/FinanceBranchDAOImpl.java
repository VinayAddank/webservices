package org.rta.core.dao.finance.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.finance.FinanceBranchDAO;
import org.rta.core.entity.finance.FinanceBranchEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FinanceBranchDAOImpl extends BaseDAO<FinanceBranchEntity> implements FinanceBranchDAO{

	public FinanceBranchDAOImpl(){
		super(FinanceBranchEntity.class);
	}
	
	public List<FinanceBranchEntity> getAllBranchesForHead(Long headId,boolean considerStatus, Integer activeStatus){
		Criteria criteria = getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("headFinancer.userId", headId));
		if (considerStatus)
			criteria = criteria.add(Restrictions.eq("activeStatus", activeStatus));
		List<FinanceBranchEntity> branches = (List<FinanceBranchEntity>) criteria.list();
		return branches;
	}
	
}
