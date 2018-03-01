package org.rta.core.dao.payment.cms.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.SBIDDOMasterDAO;
import org.rta.core.entity.payment.cms.SBIDDOMasterEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SBIDDOMasterDAOImpl extends BaseDAO<SBIDDOMasterEntity> implements SBIDDOMasterDAO {

    public SBIDDOMasterDAOImpl() {
        super(SBIDDOMasterEntity.class);
    }

    @Override
    public SBIDDOMasterEntity getByDistrictCode(String districtCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("districtCode", districtCode));
        return (SBIDDOMasterEntity) criteria.uniqueResult();
    }

}
