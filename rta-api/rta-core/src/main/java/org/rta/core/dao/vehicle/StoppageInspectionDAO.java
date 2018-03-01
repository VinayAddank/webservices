package org.rta.core.dao.vehicle;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.StoppageInspectionEntity;

public interface StoppageInspectionDAO extends GenericDAO<StoppageInspectionEntity>{

	public List<StoppageInspectionEntity> getStoppageInspections(String applicationNo);
	
}
