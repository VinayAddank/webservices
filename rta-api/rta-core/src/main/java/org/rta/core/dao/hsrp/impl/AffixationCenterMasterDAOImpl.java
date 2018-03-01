package org.rta.core.dao.hsrp.impl;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.hsrp.AffixationCenterMasterDAO;
import org.rta.core.entity.hsrp.AffixationCenterMasterEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */

@Repository("affixationCenterMasterDAO")
public class AffixationCenterMasterDAOImpl extends BaseDAO<AffixationCenterMasterEntity>
        implements AffixationCenterMasterDAO {

    public AffixationCenterMasterDAOImpl() {
        super(AffixationCenterMasterEntity.class);
    }

    @Override
    public String getAffixationCenterMaster(Integer mandalCode,int districtId) {
        String affixationCenterCode =
                (String) getSession().createCriteria(getPersistentClass())
                        .add(Restrictions.eq("mandalCode", mandalCode))
                        .add(Restrictions.eq("districtId", districtId))
                        .setProjection(Projections.property("affixationCenterCode")).uniqueResult();
        return affixationCenterCode;
    }

}
