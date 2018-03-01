package org.rta.core.dao.vehicle.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.DuplicateRegistrationDAO;
import org.rta.core.entity.vehicle.DuplicateRegistrationEntity;
import org.springframework.stereotype.Repository;

@Repository("duplicateRegistrationDAO")
public class DuplicateRegistrationDAOImpl extends BaseDAO<DuplicateRegistrationEntity> implements DuplicateRegistrationDAO{

	public DuplicateRegistrationDAOImpl() {
		super(DuplicateRegistrationEntity.class);
	}

}
