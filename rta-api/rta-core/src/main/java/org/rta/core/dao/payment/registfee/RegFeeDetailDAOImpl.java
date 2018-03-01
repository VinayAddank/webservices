package org.rta.core.dao.payment.registfee;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

@Repository
public class RegFeeDetailDAOImpl extends BaseDAO<RegFeeDetailsEntity> implements RegFeeDetailDAO {

	public RegFeeDetailDAOImpl() {
		super(RegFeeDetailsEntity.class);
	}

	@Override
	public RegFeeDetailsEntity getByVehicleRcId(long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
		return (RegFeeDetailsEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegFeeDetailsEntity> getByVehicleRCEntities(List<VehicleRCEntity> vehicleRCEntities) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.in("vehicleRc", vehicleRCEntities));
		return criteria.list();
	}

}
