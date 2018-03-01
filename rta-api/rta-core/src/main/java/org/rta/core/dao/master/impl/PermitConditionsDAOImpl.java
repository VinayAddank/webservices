package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PermitConditionsDAO;
import org.rta.core.entity.master.PermitConditionsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PermitConditionsDAOImpl extends BaseDAO<PermitConditionsEntity> implements PermitConditionsDAO {

    public PermitConditionsDAOImpl() {
        super(PermitConditionsEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermitConditionsEntity> getPermitConditions(String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", permitType));
        criteria.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        return (List<PermitConditionsEntity>) criteria.list();
    }

    @Override
    public PermitConditionsEntity getPermitCondition(String code, String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.add(Restrictions.eq("permitType", permitType));
        criteria.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        return (PermitConditionsEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PermitConditionsEntity> getTempPermitConditions(String primaryPermit, String tempPermitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", primaryPermit).ignoreCase());
        criteria.add(Restrictions.eq("tempPermitTypeCode", tempPermitType).ignoreCase());
        return (List<PermitConditionsEntity>) criteria.list();
    }
    
    @Override
    public PermitConditionsEntity getPermitCondition(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.setMaxResults(1);
        return (PermitConditionsEntity) criteria.uniqueResult();
    }
}
