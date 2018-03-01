package org.rta.core.dao.certificate.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.certificate.PermitVehicleClassMappingDAO;
import org.rta.core.entity.certificate.PermitVehicleClassMappingEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Nov 10, 2016.
 */
@Repository("permitVehicleClassMappingDAO")
public class PermitVehicleClassMappingDAOImpl extends BaseDAO<PermitVehicleClassMappingEntity>
        implements PermitVehicleClassMappingDAO {

    public PermitVehicleClassMappingDAOImpl() {
        super(PermitVehicleClassMappingEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PermitVehicleClassMappingEntity> getPermitTypeList(String vehicleClassDescCode) {

        return getSession().createCriteria(getPersistentClass())
                .add(Restrictions.eq("vehicleClassCode", vehicleClassDescCode).ignoreCase())
                .list();
    }

}
