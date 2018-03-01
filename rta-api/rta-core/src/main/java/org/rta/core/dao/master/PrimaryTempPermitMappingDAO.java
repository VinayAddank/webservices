package org.rta.core.dao.master;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.master.PrimaryTempPermitMappingEntity;

public interface PrimaryTempPermitMappingDAO extends GenericDAO<PrimaryTempPermitMappingEntity>{

    List<PrimaryTempPermitMappingEntity> getList(String primaryPermitType);
}
