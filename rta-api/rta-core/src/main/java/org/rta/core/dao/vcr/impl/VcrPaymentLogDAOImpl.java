package org.rta.core.dao.vcr.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vcr.VcrPaymentLogDAO;
import org.rta.core.entity.vcr.VcrPaymentLogEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VcrPaymentLogDAOImpl extends BaseDAO<VcrPaymentLogEntity> implements VcrPaymentLogDAO {

	public VcrPaymentLogDAOImpl() {
		super(VcrPaymentLogEntity.class);
	}

}
