package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PermitTypeDAO;
import org.rta.core.entity.master.PermitTypeEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
@Repository("permitTypeDAO")
public class PermitTypeDAOImpl extends BaseDAO<PermitTypeEntity> implements PermitTypeDAO {

    public PermitTypeDAOImpl() {
        super(PermitTypeEntity.class);
    }

    @Override
    public PermitTypeEntity getPermitType(Integer permitTypeId) {
        return (PermitTypeEntity) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("permitTypeId", permitTypeId)).uniqueResult();
    }

    @Override
    public PermitTypeEntity getPermitTypeByCode(String permitType) {
        return (PermitTypeEntity) getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("code", permitType)).uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PermitTypeEntity> getPermitTypeByCodes(List<String> permitTypeList, boolean isTemp) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("code", permitTypeList));
        criteria.add(Restrictions.eq("isTempPermit", isTemp));
        return criteria.list();
    }
}
