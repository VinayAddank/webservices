package org.rta.core.dao.docs;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.LicenceAttachmentDetailsEntity;

public interface LicenceAttachmentDetailsDAO extends GenericDAO<LicenceAttachmentDetailsEntity> {

    /**
     * Get all attachments details basis of licenceHolderId
     * 
     * @param licenceHolderId
     * @return
     */
    public List<LicenceAttachmentDetailsEntity> getAllAttachmentDetails(Long  licenceHolderId);

    /**
     * Get attachments details for individual documents
     * 
     * @param licenceHolderId
     * @param docId
     * @return
     */
    public LicenceAttachmentDetailsEntity getAttachmentDetails(Long  licenceHolderId, Integer docId);
    
}
