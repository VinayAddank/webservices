package org.rta.core.dao.payment.cms;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.payment.cms.VehicleQuarterlyEntity;

public interface VehicleQuarterlyDAO extends GenericDAO<VehicleQuarterlyEntity> {
	public List<String> getAllCodes();

}
