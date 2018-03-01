package org.rta.core.dao.docs;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.AttachmentDetailsHistoryEntity;
import org.rta.core.enums.ServiceType;

/**
 *	@Author sohan.maurya created on Jan 16, 2017.
 */


public interface AttachmentDetailsHistoryDAO extends GenericDAO<AttachmentDetailsHistoryEntity> {

    public String saveData(Long attachmentId, String modifiedBy, Long modifiedOn, ServiceType serviceType);

}
