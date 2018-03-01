package org.rta.core.dao.payment.cms.impl;

import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.SBIDistributionDAO;
import org.rta.core.entity.payment.cms.SBIDistributionEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SBIDistributionDAOImpl extends BaseDAO<SBIDistributionEntity> implements SBIDistributionDAO {

    public SBIDistributionDAOImpl() {
        super(SBIDistributionEntity.class);
    }

}
