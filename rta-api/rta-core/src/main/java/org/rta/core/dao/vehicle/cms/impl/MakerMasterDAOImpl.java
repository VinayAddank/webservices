package org.rta.core.dao.vehicle.cms.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vehicle.cms.MakerMaterDAO;
import org.rta.core.entity.vehicle.cms.MakerMasterEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
@Repository("makerMaterDAO")
public class MakerMasterDAOImpl extends BaseDAO<MakerMasterEntity> implements MakerMaterDAO {

    public MakerMasterDAOImpl() {
        super(MakerMasterEntity.class);
    }


}
