package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.LateFeeDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class LateFeeDAOImpl extends BaseDAO<LateFeeDetailsEntity> implements LateFeeDAO{

	public LateFeeDAOImpl() {
		super(LateFeeDetailsEntity.class);
	}

}
