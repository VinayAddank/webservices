package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.StateEntity;

public interface StateDAO extends GenericDAO<StateEntity>{
    
    public StateEntity getStateByName(String name);
    
    public StateEntity getStateByCode(String code);

    public StateEntity getStateByNameOrCode(String nameOrCode);
    
    public List<StateEntity> getStatesById(List<Long> ids);
    
}
