package org.rta.core.dao.docs.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.AttachmentDetailsDAO;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.springframework.stereotype.Repository;

@Repository("attachmentDetailsDAO")
public class AttachmentDetailsDAOImpl extends BaseDAO<AttachmentDetailsEntity> implements AttachmentDetailsDAO {

    private Criteria criteria = null;

    public AttachmentDetailsDAOImpl() {
        super(AttachmentDetailsEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AttachmentDetailsEntity> getAllAttachments(VehicleRCEntity vehicleId, UserType userType) {
        
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.createAlias("vehicle", "ve").add(Restrictions.eq("ve.chassisNumber", vehicleId.getChassisNumber()));
        if(userType == UserType.ROLE_BODY_BUILDER || userType == UserType.ROLE_DEALER || userType == UserType.ROLE_PUC ){
        	criteria.add(Restrictions.eq("userRole", userType.getValue()));
        }
        criteria.add(Restrictions.ne("status", Status.CANCELLED.getValue()));
        return criteria.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getDocTypesIds(Long vehicleRcId, UserType userType) {

        List<Integer> docIds = new ArrayList<Integer>();
        try {
            criteria = getSession().createCriteria(getPersistentClass())
                    .add(Restrictions.eq("vehicle.vehicleRcId", vehicleRcId))
                    .add(Restrictions.eq("userRole", userType.getValue()))
                    .add(Restrictions.ne("status", Status.CANCELLED.getValue()))
                    .setProjection(Projections.property("docTypes"));
            // .setProjection(Projections.groupProperty("docTypes"));

            List<DocTypesEntity> docTypes = criteria.list();
            docTypes.forEach((docType) -> {
                docIds.add(docType.getDocTypeId());
            });
        } catch (HibernateException he) {
        }
        return docIds;
    }
    
    @SuppressWarnings("unchecked")
	public List<AttachmentDetailsEntity> getAllAttachments(Long vehicleId, UserType userType) {
		List<AttachmentDetailsEntity> docTypes = null;
		try {
			docTypes = getSession()
					.createQuery("select a from AttachmentDetailsEntity a where a.vehicle.vehicleRcId =:vehicleId and "
						+ " a.status !=:status and " + "docTypes.roleName =:roleNm order by a.docTypes.docTypeId")
					.setParameter("vehicleId", vehicleId).setParameter("status", Status.CANCELLED.getValue()).setParameter("roleNm", userType.getLabel()).list();		

		} catch (HibernateException he) {
			he.printStackTrace();
		}
		return docTypes;
	}
    
    @SuppressWarnings("unchecked")
    public List<AttachmentDetailsEntity> getAllAttachmentsForvehicleRCId(Long vehicleId, UserType userType) {
		List<AttachmentDetailsEntity> docTypes = null;
		try {
			docTypes = getSession()
                    .createQuery(
                            "select a from AttachmentDetailsEntity a where a.vehicle.vehicleRcId =:vehicleId and a.status !=:status and userRole=:roleId order by a.docTypes.docTypeId")
                    .setParameter("vehicleId", vehicleId).setParameter("status", Status.CANCELLED.getValue()).setParameter("roleId", userType.getValue()).list();

		} catch (HibernateException he) {
			he.printStackTrace();
		}
		return docTypes;
	}


    @Override
    public Long getAttatchDltIdByDocId(Long vehicleRcId, Integer docId) {
        Long attachmentId = null;
        try{
            attachmentId = (Long) getSession()
                    .createQuery("select attachmentDlId from AttachmentDetailsEntity a where a.vehicle.vehicleRcId =:vehicleRcId and a.status !=:status and "
                                    + "docTypes.docTypeId =:docId")
                    .setParameter("vehicleRcId", vehicleRcId).setParameter("status", Status.CANCELLED.getValue()).setParameter("docId", docId).uniqueResult();
        } catch (HibernateException he) {

        }
        return attachmentId;
    }


    @Override

    public Boolean getIsAttatchDltIdByDocId(Long vehicleRcId, Integer docId) {
        Long attachmentId = null;
        try {
            attachmentId = (Long) getSession().createQuery(

                    "select attachmentDlId from AttachmentDetailsEntity a where a.vehicle.vehicleRcId =:vehicleRcId and a.status !=:status and "
                            + "docTypes.docTypeId =:docId")
                    .setParameter("vehicleRcId", vehicleRcId).setParameter("status", Status.CANCELLED.getValue()).setParameter("docId", docId).uniqueResult();
        } catch (HibernateException he) {
            he.printStackTrace();
        }
        return attachmentId != null ? true : false;
    }
    
    @Override

    public AttachmentDetailsEntity getAttatchDltIdByDocIdVehId(Long vehicleRcId, Integer docId) {
        Criteria criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("docTypes.docTypeId", docId));
        criteria.add(Restrictions.eq("vehicle.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.ne("status", Status.CANCELLED.getValue()));
        return (AttachmentDetailsEntity) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AttachmentDetailsEntity> getAttachmentDetails(List<VehicleRCEntity> vehicleRCEntities) {
        Criteria criteria =
                getSession().createCriteria(getPersistentClass()).add(Restrictions.ne("status", Status.CANCELLED.getValue())).add(Restrictions.in("vehicle", vehicleRCEntities));
        return criteria.list();
    }

    @Override
    public Integer deleteAttachment(Long vehicleRcId, Integer docTypeId) {

        Query query = getSession().createQuery(
                "delete from AttachmentDetailsEntity where vehicle.vehicleRcId= :vehicleRcId AND docTypes.docTypeId= :docTypeId");
        query.setLong("vehicleRcId", vehicleRcId);
        query.setInteger("docTypeId", docTypeId);
        return query.executeUpdate();

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AttachmentDetailsEntity> getDealerAndOwnerSign(Long vehicleRcId, List<Integer> docTypesIds) {

        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.in("docTypes.docTypeId", docTypesIds));
        criteria.add(Restrictions.eq("vehicle.vehicleRcId", vehicleRcId));
        criteria.add(Restrictions.ne("status", Status.CANCELLED.getValue()));
        return criteria.list();
    }


}


