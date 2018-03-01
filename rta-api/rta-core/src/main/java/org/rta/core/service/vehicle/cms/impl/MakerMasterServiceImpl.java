package org.rta.core.service.vehicle.cms.impl;

import java.util.List;

import org.rta.core.dao.vehicle.cms.MakerMaterDAO;
import org.rta.core.helper.vehicle.cms.MakerMasterHelper;
import org.rta.core.model.vehicle.cms.MakerMasterModel;
import org.rta.core.service.vehicle.cms.MakerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
@Service("makerMasterService")
public class MakerMasterServiceImpl implements MakerMasterService {

    @Autowired
    private MakerMaterDAO makerMaterDAO;

    @Autowired
    private MakerMasterHelper makerMasterHelper;

    @Transactional
    @Override
    public List<MakerMasterModel> getMakersMaster() {

        return (List<MakerMasterModel>) makerMasterHelper.convertToModelList(makerMaterDAO.getAll());
    }

}
