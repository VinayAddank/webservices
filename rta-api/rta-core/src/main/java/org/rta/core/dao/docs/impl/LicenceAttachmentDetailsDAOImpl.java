package org.rta.core.dao.docs.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.LicenceAttachmentDetailsDAO;
import org.rta.core.entity.docs.LicenceAttachmentDetailsEntity;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 3, 2017.
 */

@Repository("licenceAttachmentDetailsDAO")
public class LicenceAttachmentDetailsDAOImpl extends BaseDAO<LicenceAttachmentDetailsEntity> implements  LicenceAttachmentDetailsDAO{

    private Criteria criteria = null;

    public LicenceAttachmentDetailsDAOImpl() {
        super(LicenceAttachmentDetailsEntity.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LicenceAttachmentDetailsEntity> getAllAttachmentDetails(Long licenceHoderId) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("licenseHolderId.licenceHolderId", licenceHoderId));
        return criteria.list();
    }

    @Override
    public LicenceAttachmentDetailsEntity getAttachmentDetails(Long licenceHoderId, Integer docId) {
        criteria = getSession().createCriteria(getPersistentClass());
        criteria.add(Restrictions.eq("licenseHolderId.licenceHolderId", licenceHoderId))
                .add(Restrictions.eq("docTypes.docTypeId", docId));
        return (LicenceAttachmentDetailsEntity) criteria.uniqueResult();
    }

}
