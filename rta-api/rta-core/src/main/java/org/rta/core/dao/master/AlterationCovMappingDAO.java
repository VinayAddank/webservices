package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.AlterationCovMappingEntity;

public interface AlterationCovMappingDAO extends GenericDAO<AlterationCovMappingEntity>{
	
	List<String> getMappedAlterationCovListByCode(String code,boolean isTransportVehicle);

}
