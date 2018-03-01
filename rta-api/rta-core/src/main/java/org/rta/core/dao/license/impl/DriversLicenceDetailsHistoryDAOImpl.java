package org.rta.core.dao.license.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.DriversLicenceDetailsHistoryDAO;
import org.rta.core.entity.licence.DriversLicenceDetailsHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DriversLicenceDetailsHistoryDAOImpl extends BaseDAO<DriversLicenceDetailsHistoryEntity>
		implements DriversLicenceDetailsHistoryDAO {

	public DriversLicenceDetailsHistoryDAOImpl() {
		super(DriversLicenceDetailsHistoryEntity.class);
	}

}