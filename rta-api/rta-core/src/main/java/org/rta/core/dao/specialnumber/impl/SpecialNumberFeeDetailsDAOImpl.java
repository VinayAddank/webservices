package org.rta.core.dao.specialnumber.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.specialnumber.SpecialNumberFeeDetailsDAO;
import org.rta.core.entity.specialnumber.SpecialNumberFeeDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialNumberFeeDetailsDAOImpl extends BaseDAO<SpecialNumberFeeDetailsEntity> implements SpecialNumberFeeDetailsDAO {

	public SpecialNumberFeeDetailsDAOImpl() {
		super(SpecialNumberFeeDetailsEntity.class);
	}

	

}
