package org.rta.core.dao.office.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.office.RtaOfficeSerialNumberDAO;
import org.rta.core.entity.office.RTAOfficeSerialNumberEntity;
import org.rta.core.enums.SerialNumberType;
import org.springframework.stereotype.Repository;

@Repository
public class RtaOfficeSerialNumberDAOImpl extends BaseDAO<RTAOfficeSerialNumberEntity> implements RtaOfficeSerialNumberDAO {

    public RtaOfficeSerialNumberDAOImpl() {
        super(RTAOfficeSerialNumberEntity.class);
    }

    @Override
    public RTAOfficeSerialNumberEntity getSerialNumber(String rtaOfficeCode, Integer year, SerialNumberType type) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("rtaOfficeCode", rtaOfficeCode));
        criteria.add(Restrictions.eq("serialNumberType", type));
        criteria.add(Restrictions.eq("year", year));
        return (RTAOfficeSerialNumberEntity) criteria.uniqueResult();
    }

}
