package org.rta.core.dao.payment.cms.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.VehicleWgtDAO;
import org.rta.core.entity.payment.cms.VehicleWgtMasterEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleWgtDAOImpl extends BaseDAO<VehicleWgtMasterEntity> implements VehicleWgtDAO {

	public VehicleWgtDAOImpl() {
		super(VehicleWgtMasterEntity.class);
	}

	public VehicleWgtDAOImpl(Class<VehicleWgtMasterEntity> persistentClass) {
		super(persistentClass);
		// TODO Auto-generated constructor stub
	}

	public VehicleWgtMasterEntity getVehicleWgtCatgFrmWgt(Long wgt) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.lt("frmWeight", wgt));
        criteria.add(Restrictions.ge("toWeight", wgt));
		return (VehicleWgtMasterEntity) criteria.uniqueResult();
	}

}
