package org.rta.core.dao.payment.tax;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.VehicleCurrentTaxEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleCurrentTaxDAOImpl extends BaseDAO<VehicleCurrentTaxEntity> implements VehicleCurrentTaxDAO{

	public VehicleCurrentTaxDAOImpl() {
		super(VehicleCurrentTaxEntity.class);
	}

	@Override
	public VehicleCurrentTaxEntity getEntityByVehicleRcId(long vehicleRcId) {
		Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        return (VehicleCurrentTaxEntity) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
    @Override
    public List<VehicleCurrentTaxEntity> getByVehicleRcEntities(List<VehicleRCEntity> vehicleRCEntities) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRc", vehicleRCEntities));
        return criteria.list();
    }
}
