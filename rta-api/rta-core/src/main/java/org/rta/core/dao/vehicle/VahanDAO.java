package org.rta.core.dao.vehicle;

import java.util.List;
import java.util.Set;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.VahanEntity;

public interface VahanDAO extends GenericDAO<VahanEntity> {

    public abstract VahanEntity getByChassisNumber(String chassisNumber);
    
    public List<VahanEntity> getVahanEntitiesByChassisNumber(Set<String> chassisNumbers);

}
