package org.rta.core.dao.docs.impl;

import org.hibernate.SQLQuery;
import org.rta.core.dao.base.BaseDAO;
import org.rta.core.dao.docs.AttachmentDetailsHistoryDAO;
import org.rta.core.entity.docs.AttachmentDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;
import org.springframework.stereotype.Repository;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */

@Repository("attachmentDetailsHistoryDAO")
public class AttachmentDetailsHistoryDAOImpl extends BaseDAO<AttachmentDetailsHistoryEntity>
        implements AttachmentDetailsHistoryDAO {

    public AttachmentDetailsHistoryDAOImpl() {
        super(AttachmentDetailsHistoryEntity.class);
    }

    @Override
    public String saveData(Long attachmentId, String modifiedBy, Long modifiedOn, ServiceType serviceType) {

        SQLQuery query = (SQLQuery) getSession()
                        .createSQLQuery("SELECT attachment_details_history_function(:attachmentId, :modifiedBy, :modifiedOn, :serviceCode )")
                        .setParameter("attachmentId", attachmentId).setParameter("modifiedBy", modifiedBy)
                        .setParameter("modifiedOn", modifiedOn).setParameter("serviceCode", serviceType.getCode());

        return (String) query.list().get(0);
    }
}
