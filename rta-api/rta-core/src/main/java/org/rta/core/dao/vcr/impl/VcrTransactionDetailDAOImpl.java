package org.rta.core.dao.vcr.impl;


import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.vcr.VcrTransactionDetailDAO;
import org.rta.core.entity.vcr.VcrTransactionDetailEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VcrTransactionDetailDAOImpl extends BaseDAO<VcrTransactionDetailEntity> implements VcrTransactionDetailDAO {

	public VcrTransactionDetailDAOImpl() {
		super(VcrTransactionDetailEntity.class);
		
	}
	
	 @Override
	    public VcrTransactionDetailEntity getVcrDetailsByVcrNumber(String prNumber) {
	        Criteria criteria = getSession().createCriteria(getPersistentClass());
	        criteria.add(Restrictions.eq("prNumber", prNumber)).addOrder( Order.desc("paymentTime")).setMaxResults(1);
	        return (VcrTransactionDetailEntity) criteria.uniqueResult();
	    }

}
