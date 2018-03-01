package org.rta.core.dao.master.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.DesignationDAO;
import org.rta.core.entity.master.DesignationEntity;
import org.springframework.stereotype.Repository;

@Repository
public class DesignationDAOImpl extends BaseDAO<DesignationEntity> implements DesignationDAO {

    public DesignationDAOImpl() {
        super(DesignationEntity.class);
    }

}
