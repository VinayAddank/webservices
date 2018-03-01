/**
 * 
 */
package org.rta.core.dao.customer.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.customer.PAddressNotMatchedDAO;
import org.rta.core.entity.applicant.PAddressNotMatchedEntity;
import org.springframework.stereotype.Repository;

/**
 * @author arun.verma
 *
 */
@Repository
public class PAddressNotMatchedDAOImpl extends BaseDAO<PAddressNotMatchedEntity> implements PAddressNotMatchedDAO{

    public PAddressNotMatchedDAOImpl() {
        super(PAddressNotMatchedEntity.class);
    }

    @Override
    public PAddressNotMatchedEntity getPAddressNotMatched(String aadharNo) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("aadharNo", aadharNo));
        criteria.addOrder(Order.desc("createdOn"));
        criteria.setMaxResults(1);
        return (PAddressNotMatchedEntity) criteria.uniqueResult();
    }
    
    @Override
    public PAddressNotMatchedEntity getPAddressNotMatchedByVehId(Long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        return (PAddressNotMatchedEntity) criteria.uniqueResult();
    }
}
