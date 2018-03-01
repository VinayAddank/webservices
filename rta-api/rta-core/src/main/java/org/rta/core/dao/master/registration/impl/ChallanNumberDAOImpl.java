package org.rta.core.dao.master.registration.impl;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.registration.ChallanNumberDAO;
import org.rta.core.entity.master.registration.ChallanNumberEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ChallanNumberDAOImpl extends BaseDAO<ChallanNumberEntity> implements ChallanNumberDAO {

    public ChallanNumberDAOImpl() {
        super(ChallanNumberEntity.class);
    }

    @Override
    public ChallanNumberEntity getBytreasuryCode(String treasuryCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("treasuryCode", treasuryCode));
        criteria.setLockMode(LockMode.UPGRADE_NOWAIT);
        return (ChallanNumberEntity) criteria.uniqueResult();
    }
}
