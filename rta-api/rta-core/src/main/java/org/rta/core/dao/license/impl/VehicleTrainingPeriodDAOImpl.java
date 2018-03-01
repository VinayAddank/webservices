package org.rta.core.dao.license.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.license.VehicleTrainingPeriodDAO;
import org.rta.core.entity.licence.VehicleTrainingPeriodEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleTrainingPeriodDAOImpl extends BaseDAO<VehicleTrainingPeriodEntity>
		implements VehicleTrainingPeriodDAO {

	public VehicleTrainingPeriodDAOImpl() {
		super(VehicleTrainingPeriodEntity.class);

	}

}
