package org.rta.core.dao.finance;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.finance.FinancerSeizedVehiclesEntity;

public interface FinancerSeizedVehiclesDAO extends GenericDAO<FinancerSeizedVehiclesEntity> {

    public FinancerSeizedVehiclesEntity getSeizedVehicle(Long vehicleRcId);
    
}
