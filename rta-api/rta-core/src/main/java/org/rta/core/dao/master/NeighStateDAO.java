package org.rta.core.dao.master;

import java.util.Collection;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.NeighStateEntity;
import org.rta.core.entity.master.StateEntity;

public interface NeighStateDAO extends GenericDAO<NeighStateEntity> {

	Collection<StateEntity> getAllNeighbouring(Long stateId);
	
}
