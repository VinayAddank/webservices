package org.rta.core.dao.user.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.DrivingInstituteUserInfoDAO;
import org.rta.core.entity.user.DrivingInstituteUserInfoEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DrivingInstituteUserInfoDAOImpl extends BaseDAO<DrivingInstituteUserInfoEntity> implements DrivingInstituteUserInfoDAO {

    public DrivingInstituteUserInfoDAOImpl() {
        super(DrivingInstituteUserInfoEntity.class);
    }

}
