package org.rta.core.dao.vehicle.cms.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDAO;
import org.rta.core.entity.vehicle.cms.VehicleClassEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleClassDAOImpl extends BaseDAO<VehicleClassEntity> implements VehicleClassDAO {

	
	public VehicleClassDAOImpl(){
		super(VehicleClassEntity.class);
	}
	public VehicleClassDAOImpl(Class<VehicleClassEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}
	

}
