package org.rta.core.dao.vehicle.cms;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.vehicle.cms.ModelDetailsEntity;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
public interface ModelDetailsDAO extends GenericDAO<ModelDetailsEntity> {

    public List<ModelDetailsEntity> getModelByMakerId(Integer makerId);
}
