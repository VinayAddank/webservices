package org.rta.core.dao.master.registration.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.master.registration.SpecialNumberDAO;
import org.rta.core.entity.master.registration.SpecialNumberEntity;
import org.springframework.stereotype.Repository;

@Repository
public class SpecialNumberDAOImpl extends BaseDAO<SpecialNumberEntity> implements SpecialNumberDAO {

    public SpecialNumberDAOImpl() {
        super(SpecialNumberEntity.class);
    }

    public List<Integer> getAllSpecialNo(){
    	 Criteria criteria = getSession().createCriteria(getPersistentClass());
         criteria.setProjection(Projections.property("number"));
         return (List<Integer>) criteria.list();
    }
    

    public List<SpecialNumberEntity> getAllSpecialNoEntity(){
   	     Criteria criteria = getSession().createCriteria(getPersistentClass());
   	     criteria.addOrder(Order.asc("number"));
   	     return criteria.list();
   }

	@Override
	public SpecialNumberEntity checkSpecialNumber(String specialNumber) {
		 Criteria criteria = getSession().createCriteria(getPersistentClass());
		 criteria.add(Restrictions.eq("number", specialNumber));
		 return (SpecialNumberEntity) criteria.uniqueResult();
	}
}