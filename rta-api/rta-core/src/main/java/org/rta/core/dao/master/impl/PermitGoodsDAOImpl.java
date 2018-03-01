/**
 * 
 */
package org.rta.core.dao.master.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.PermitGoodsDAO;
import org.rta.core.entity.master.PermitGoodsMasterEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class PermitGoodsDAOImpl extends BaseDAO<PermitGoodsMasterEntity> implements PermitGoodsDAO{

    public PermitGoodsDAOImpl() {
        super(PermitGoodsMasterEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermitGoodsMasterEntity> getPermitGoods(String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", permitType));
        
        Disjunction or = Restrictions.disjunction();
        or.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        or.add(Restrictions.eq("vehicleClassCode", ""));
        criteria.add(Restrictions.eq("tempPermitTypeCode", ""));
        criteria.add(or);
        criteria.addOrder(Order.asc("code"));
        return (List<PermitGoodsMasterEntity>) criteria.list();
    }
    
    @Override
    public PermitGoodsMasterEntity getPermitGood(String code, String cov, String permitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.add(Restrictions.eq("permitType", permitType));
        
        Disjunction or = Restrictions.disjunction();
        or.add(Restrictions.eq("vehicleClassCode", cov).ignoreCase());
        or.add(Restrictions.eq("vehicleClassCode", ""));
        criteria.add(or);
        criteria.add(Restrictions.eq("tempPermitTypeCode", ""));
        
        criteria.setMaxResults(1);
        return (PermitGoodsMasterEntity) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<PermitGoodsMasterEntity> getTempPermitGoods(String primaryPermitType, String tempPermitType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("permitType", primaryPermitType).ignoreCase());
        criteria.add(Restrictions.eq("tempPermitTypeCode", tempPermitType).ignoreCase());
        criteria.addOrder(Order.asc("code"));
        return (List<PermitGoodsMasterEntity>) criteria.list();
    }
    
    @Override
    public PermitGoodsMasterEntity getPermitGood(String code) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("code", code).ignoreCase());
        criteria.setMaxResults(1);
        return (PermitGoodsMasterEntity) criteria.uniqueResult();
    }
}
