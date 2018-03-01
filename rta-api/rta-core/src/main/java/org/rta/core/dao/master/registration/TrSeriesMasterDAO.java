package org.rta.core.dao.master.registration;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.registration.TrSeriesMasterEntity;
import org.rta.core.entity.user.AddressEntity;

public interface TrSeriesMasterDAO extends GenericDAO<TrSeriesMasterEntity> {

	public TrSeriesMasterEntity getByDistrict(AddressEntity addressEntity);
	
	public List<TrSeriesMasterEntity> getFutureTrSeries(AddressEntity addressEntity);
	
}
