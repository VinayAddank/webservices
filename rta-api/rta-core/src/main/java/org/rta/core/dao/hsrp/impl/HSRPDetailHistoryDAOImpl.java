package org.rta.core.dao.hsrp.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.hsrp.HSRPDetailHistoryDAO;
import org.rta.core.entity.hsrp.HSRPDetailHistoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public class HSRPDetailHistoryDAOImpl extends BaseDAO<HSRPDetailHistoryEntity> implements HSRPDetailHistoryDAO {

    public HSRPDetailHistoryDAOImpl() {
        super(HSRPDetailHistoryEntity.class);
    }

}
