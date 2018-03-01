package org.rta.core.service.vehicle.cms;

import java.util.List;

import org.rta.core.model.vehicle.cms.ModelDetailsModel;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
public interface ModelDetailsService {

    public List<ModelDetailsModel> getModelByMakerId(Integer makerId);

}
