package org.rta.core.dao.payment.registfee;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.CessFeeDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CessFeeDetailDAOImpl extends BaseDAO<CessFeeDetailsEntity> implements CessFeeDetailDAO {

	public CessFeeDetailDAOImpl() {
		super(CessFeeDetailsEntity.class);
	}

	@Override
	public CessFeeDetailsEntity getByVehicleRcId(long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		return (CessFeeDetailsEntity) criteria.uniqueResult();
	}


}
