package org.rta.core.dao.payment.registfee;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.entity.payment.registfee.PermitFeeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class PermitFeeDAOImpl extends BaseDAO<PermitFeeEntity> implements PermitFeeDAO {

    public PermitFeeDAOImpl() {
        super(PermitFeeEntity.class);
    }

    @Override
    public PermitFeeEntity getByVehicleRcIdNdStatus(long vehicleRcId, Integer status) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.eq("status", status));
        return (PermitFeeEntity) criteria.uniqueResult();
    }
}
