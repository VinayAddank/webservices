/**
 * 
 */
package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PermitRouteDAO;
import org.rta.core.entity.master.PermitRouteConditionsMasterEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class PermitRouteDAOImpl extends BaseDAO<PermitRouteConditionsMasterEntity> implements PermitRouteDAO{

    public PermitRouteDAOImpl() {
        super(PermitRouteConditionsMasterEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermitRouteConditionsMasterEntity> getPermitRoute(String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", permitType));
        criteria.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        return (List<PermitRouteConditionsMasterEntity>) criteria.list();
    }
    
    @Override
    public PermitRouteConditionsMasterEntity getPermitRoute(String code, String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.add(Restrictions.eq("permitType", permitType));
        criteria.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        return (PermitRouteConditionsMasterEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PermitRouteConditionsMasterEntity> getTempPermitRoute(String primaryPermitType, String tempPermitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", primaryPermitType).ignoreCase());
        criteria.add(Restrictions.eq("tempPermitTypeCode", tempPermitType).ignoreCase());
        return (List<PermitRouteConditionsMasterEntity>) criteria.list();
    }
    
    @Override
    public PermitRouteConditionsMasterEntity getPermitRoute(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.setMaxResults(1);
        return (PermitRouteConditionsMasterEntity) criteria.uniqueResult();
    }
}