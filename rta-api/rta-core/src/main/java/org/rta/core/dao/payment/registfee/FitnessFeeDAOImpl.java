package org.rta.core.dao.payment.registfee;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class FitnessFeeDAOImpl extends BaseDAO<FitnessFeeDetailsEntity> implements FitnessFeeDAO {

    public FitnessFeeDAOImpl() {
        super(FitnessFeeDetailsEntity.class);
    }

	@Override
	public FitnessFeeDetailsEntity getByVehicleRcId(long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        return (FitnessFeeDetailsEntity) criteria.uniqueResult();
	}

}
