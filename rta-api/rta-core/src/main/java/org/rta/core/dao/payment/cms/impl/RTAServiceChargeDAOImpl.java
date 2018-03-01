package org.rta.core.dao.payment.cms.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.RTAServiceChargeDAO;
import org.rta.core.entity.payment.cms.RTAServiceChargeEntity;
import org.rta.core.enums.MasterStatus;
import org.springframework.stereotype.Repository;


@Repository
public class RTAServiceChargeDAOImpl extends BaseDAO<RTAServiceChargeEntity> implements RTAServiceChargeDAO{

	public RTAServiceChargeDAOImpl(){
		super(RTAServiceChargeEntity.class);
	}
	public RTAServiceChargeDAOImpl(Class<RTAServiceChargeEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

    @Override
    public RTAServiceChargeEntity getByVehicleClass(String vehicleClassCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleCode", vehicleClassCode));
        criteria.add(Restrictions.eq("status", MasterStatus.ACTIVE.getValue()));
        criteria.addOrder(Order.desc("amount"));
        criteria.setMaxResults(1);
        return (RTAServiceChargeEntity) criteria.uniqueResult();
    }
	
	

}
