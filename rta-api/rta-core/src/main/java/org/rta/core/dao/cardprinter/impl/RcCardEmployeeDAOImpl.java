package org.rta.core.dao.cardprinter.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.cardprinter.RcCardEmployeeDAO;
import org.rta.core.entity.cardprinter.RcCardEmployeeEntity;
import org.rta.core.entity.office.RtaOfficeEntity;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Sep 22, 2016.
 */

@Repository("rcCardEmployeeDAO")
public class RcCardEmployeeDAOImpl extends BaseDAO<RcCardEmployeeEntity> implements RcCardEmployeeDAO {

    public RcCardEmployeeDAOImpl() {
        super(RcCardEmployeeEntity.class);
    }

    @Override
    public String getRtaOfficeCode(Long userId) {
        String rtaOfficeCode = null;
        Criteria criteria = getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("userId.userId", userId))
                .setProjection(Projections.property("rtaOfficeId"));
        RtaOfficeEntity rtaOfficeEntity = (RtaOfficeEntity) criteria.uniqueResult();
        if (!ObjectsUtil.isNull(rtaOfficeEntity)) {
            rtaOfficeCode = rtaOfficeEntity.getCode();
        }
        return rtaOfficeCode;
    }

}
