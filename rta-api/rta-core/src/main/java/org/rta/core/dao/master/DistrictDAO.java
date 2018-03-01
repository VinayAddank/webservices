package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.DistrictEntity;

public interface DistrictDAO extends GenericDAO<DistrictEntity>{

    public DistrictEntity getDistrictByName(String name);
    
    public List<DistrictEntity> getDistrictsByStateCode(String name);
    
    public List<DistrictEntity> getDistrictsByStateId(Long stateId);
    
    public DistrictEntity getDistrictByCode(String code);
    
    public List<DistrictEntity> getDistrictsById(List<Long> ids);
    
}
