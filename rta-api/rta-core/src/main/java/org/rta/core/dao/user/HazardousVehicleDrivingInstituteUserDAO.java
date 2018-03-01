package org.rta.core.dao.user;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.HazardousVehicleDrivingInstituteUserEntity;

public interface HazardousVehicleDrivingInstituteUserDAO extends GenericDAO<HazardousVehicleDrivingInstituteUserEntity>  {

    HazardousVehicleDrivingInstituteUserEntity getInstituteByUserId(Long userId);

}
