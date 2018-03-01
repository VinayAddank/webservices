package org.rta.core.dao.license.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LearnersPermitDtlHistoryDAO;
import org.rta.core.entity.licence.LearnersPermitDtlHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LearnersPermitDtlHistoryDAOImpl extends BaseDAO<LearnersPermitDtlHistoryEntity>
		implements LearnersPermitDtlHistoryDAO {

	public LearnersPermitDtlHistoryDAOImpl() {
		super(LearnersPermitDtlHistoryEntity.class);
	}

}
