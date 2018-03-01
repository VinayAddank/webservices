package org.rta.core.dao.license.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LearnersPermitDtlDAO;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LearnersPermitDtlDAOImpl extends  BaseDAO<LearnersPermitDtlEntity> implements LearnersPermitDtlDAO{

	public LearnersPermitDtlDAOImpl() {
		super(LearnersPermitDtlEntity.class);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LearnersPermitDtlEntity> getLearnersPermitDtl(Long  holderId) {
		Criteria criteria=getSession().createCriteria(getPersistentClass())
				.add(Restrictions.eq("id.licenceHolderId", holderId));
		return  criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LearnersPermitDtlEntity> getLearnersPermitDetails(String unqueKey) {
		Criteria criteria=getSession().createCriteria(getPersistentClass());
		Criterion llrNumber = Restrictions.eq("llrNo", unqueKey);
		Criterion applicationId = Restrictions.eq("applicationId", unqueKey);
		LogicalExpression logicOr=Restrictions.or(llrNumber, applicationId);
		criteria.add(logicOr);
		return  criteria.list();
	}
	
}
