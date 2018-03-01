package org.rta.core.dao.payment.cms.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.payment.cms.RTAVehicleTaxDAO;
import org.rta.core.entity.payment.cms.RTAVehicleTaxEntity;
import org.rta.core.enums.MasterStatus;
import org.springframework.stereotype.Repository;

@Repository
public class RTAVehicleTaxDAOImpl extends BaseDAO<RTAVehicleTaxEntity> implements RTAVehicleTaxDAO {

    public RTAVehicleTaxDAOImpl() {
        super(RTAVehicleTaxEntity.class);
    }


    public RTAVehicleTaxEntity getVehicleWgtCatgFrmWgt(Long wgt, String vehicleCode) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.lt("frULW", (double) wgt));
        criteria.add(Restrictions.ge("toULW", (double) wgt));
        criteria.add(Restrictions.eq("vehicleCode", vehicleCode));
        criteria.add(Restrictions.eq("status", MasterStatus.ACTIVE.getValue()));
        return (RTAVehicleTaxEntity) criteria.uniqueResult();
    }
}
