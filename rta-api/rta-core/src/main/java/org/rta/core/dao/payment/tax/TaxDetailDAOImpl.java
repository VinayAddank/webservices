package org.rta.core.dao.payment.tax;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.TaxDetailEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TaxDetailDAOImpl extends BaseDAO<TaxDetailEntity> implements TaxDetailDAO {

    public TaxDetailDAOImpl() {
        super(TaxDetailEntity.class);
    }

    @Override
    public TaxDetailEntity getByVehicleRcId(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId.vehicleRcId", vehicleRcId));
        return (TaxDetailEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TaxDetailEntity> getByVehicleRcEntities(List<VehicleRCEntity> vehicleRCEntities) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("vehicleRcId", vehicleRCEntities));
        return criteria.list();
    }
}
