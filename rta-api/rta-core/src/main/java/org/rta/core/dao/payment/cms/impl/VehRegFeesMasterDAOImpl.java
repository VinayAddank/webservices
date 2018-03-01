package org.rta.core.dao.payment.cms.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.VehRegFeesMasterDAO;
import org.rta.core.entity.payment.cms.VehRegFeesMasterEntity;
import org.rta.core.enums.MasterStatus;
import org.springframework.stereotype.Repository;

@Repository
public class VehRegFeesMasterDAOImpl extends BaseDAO<VehRegFeesMasterEntity> implements VehRegFeesMasterDAO {

	public VehRegFeesMasterDAOImpl() {
		super(VehRegFeesMasterEntity.class);
	}

	public VehRegFeesMasterDAOImpl(Class<VehRegFeesMasterEntity> persistentClass) {
		super(persistentClass);
	}


    @Override
    public VehRegFeesMasterEntity getByWeghtCategoryNdVehicleclass(String weightCategory, String vehicleClass,
            String regFeesType) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehRegClassCode", vehicleClass));
        criteria.add(Restrictions.eq("vehRegCategory", weightCategory));
        criteria.add(Restrictions.eq("regFeesType", regFeesType));
        criteria.add(Restrictions.eq("status", MasterStatus.ACTIVE.getValue()));
        criteria.addOrder(Order.desc("regFeesAmount"));
        criteria.setMaxResults(1);
        return (VehRegFeesMasterEntity) criteria.uniqueResult();
    }

}
