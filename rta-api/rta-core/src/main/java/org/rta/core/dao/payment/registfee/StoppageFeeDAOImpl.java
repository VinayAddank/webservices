package org.rta.core.dao.payment.registfee;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.StoppageFeeEntity;

public class StoppageFeeDAOImpl extends BaseDAO<StoppageFeeEntity> implements StoppageFeeDAO{

	public StoppageFeeDAOImpl() {
		super(StoppageFeeEntity.class);
	}

}
