package org.rta.core.dao.license.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.LicenceIdentitiesDetailsDAO;
import org.rta.core.entity.licence.LicenceIdentitiesDetailsEntity;

public class LicenceIdentitiesDetailsDAOImpl extends BaseDAO<LicenceIdentitiesDetailsEntity> implements LicenceIdentitiesDetailsDAO{

	public LicenceIdentitiesDetailsDAOImpl() {
		super(LicenceIdentitiesDetailsEntity.class);
	}

}
