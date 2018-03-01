package org.rta.core.dao.vehicle.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.StoppageRevocationDAO;
import org.rta.core.entity.vehicle.StoppageRevocationEntity;

public class StoppageRevocationDAOImpl extends BaseDAO<StoppageRevocationEntity> implements  StoppageRevocationDAO{

	public StoppageRevocationDAOImpl() {
		super(StoppageRevocationEntity.class);
	}

}
