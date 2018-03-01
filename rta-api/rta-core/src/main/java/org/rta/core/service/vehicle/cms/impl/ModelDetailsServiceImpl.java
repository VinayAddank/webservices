package org.rta.core.service.vehicle.cms.impl;

import java.util.List;

import org.rta.core.dao.vehicle.cms.ModelDetailsDAO;
import org.rta.core.helper.vehicle.cms.ModelDetailsHelper;
import org.rta.core.model.vehicle.cms.ModelDetailsModel;
import org.rta.core.service.vehicle.cms.ModelDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@Service("modelDetailsService")
public class ModelDetailsServiceImpl implements ModelDetailsService {

    @Autowired
    private ModelDetailsDAO modelDetailsDAO;

    @Autowired
    private ModelDetailsHelper modelDetailsHelper;

    @Transactional
    @Override
    public List<ModelDetailsModel> getModelByMakerId(Integer makerId) {

        return (List<ModelDetailsModel>) modelDetailsHelper
                .convertToModelList(modelDetailsDAO.getModelByMakerId(makerId));
    }

}
