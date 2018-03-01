package org.rta.core.dao.certificate;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.certificate.PermitVehicleClassMappingEntity;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
public interface PermitVehicleClassMappingDAO extends GenericDAO<PermitVehicleClassMappingEntity> {

    public List<PermitVehicleClassMappingEntity> getPermitTypeList(String vehicleClassDescCode);
}
