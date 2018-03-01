package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.NeighDistrictEntity;

public interface NeighDistrictDAO extends GenericDAO<NeighDistrictEntity> {

	List<DistrictEntity> getAllNeighbouring(Long districtId);

}
