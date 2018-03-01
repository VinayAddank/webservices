package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.NeighDistrictMappingEntity;

public interface NeighDistrictMappingDAO extends GenericDAO<NeighDistrictMappingEntity>{
	
	public List<DistrictEntity> getAllNeighbouring(Long districtId);
    
}
