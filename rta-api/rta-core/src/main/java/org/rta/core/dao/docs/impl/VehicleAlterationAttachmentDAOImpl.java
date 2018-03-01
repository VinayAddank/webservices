package org.rta.core.dao.docs.impl;
/**
 *	@Author sohan.maurya created on Jan 4, 2017.
 */

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.VehicleAlterationAttachmentDAO;
import org.rta.core.entity.docs.VehicleAlterationAttachmentEntity;
import org.springframework.stereotype.Repository;

@Repository("vehicleAlterationAttachmentDAO")
public class VehicleAlterationAttachmentDAOImpl extends BaseDAO<VehicleAlterationAttachmentEntity> implements VehicleAlterationAttachmentDAO{

    private Criteria criteria = null;

    public VehicleAlterationAttachmentDAOImpl() {
        super(VehicleAlterationAttachmentEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VehicleAlterationAttachmentEntity> getAllVehicleAlterationAttachmentDetails(Long vehicleRcId) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicle.vehicleRcId", vehicleRcId));
        return criteria.list();
    }

    @Override
    public VehicleAlterationAttachmentEntity getVehicleAlterationAttachmentDetails(Long vehicleRcId, Integer docId) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("vehicle.vehicleRcId", vehicleRcId)).add(Restrictions.eq("docTypes.docTypeId", docId));
        return (VehicleAlterationAttachmentEntity) criteria.uniqueResult();
    }

}
