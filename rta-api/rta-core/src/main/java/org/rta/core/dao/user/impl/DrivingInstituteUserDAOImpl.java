package org.rta.core.dao.user.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.user.DrivingInstituteUserDAO;
import org.rta.core.entity.user.DrivingInstituteUserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DrivingInstituteUserDAOImpl extends BaseDAO<DrivingInstituteUserEntity> implements DrivingInstituteUserDAO {

    public DrivingInstituteUserDAOImpl() {
        super(DrivingInstituteUserEntity.class);
    }

}
