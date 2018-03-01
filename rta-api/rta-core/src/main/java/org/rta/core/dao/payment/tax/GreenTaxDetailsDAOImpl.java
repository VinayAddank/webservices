package org.rta.core.dao.payment.tax;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.tax.GreenTaxDetailsEntity;
import org.springframework.stereotype.Repository;

@Repository
public class GreenTaxDetailsDAOImpl extends BaseDAO<GreenTaxDetailsEntity> implements GreenTaxDetailsDAO {

    public GreenTaxDetailsDAOImpl() {
        super(GreenTaxDetailsEntity.class);
    }

    @Override
    public GreenTaxDetailsEntity getByVehicleRcId(long vehicleRcId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRc.vehicleRcId", vehicleRcId));
        return (GreenTaxDetailsEntity) criteria.uniqueResult();
    }


}
