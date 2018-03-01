package org.rta.core.dao.docs;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.docs.UserAttachmentDetailsEntity;

public interface UserAttachmentDetailsDAO extends GenericDAO<UserAttachmentDetailsEntity> {

    /**
     * Get all attachments details basis of user Id
     * 
     * @param userName
     * @return
     */
    public List<UserAttachmentDetailsEntity> getAllAttachmentDetails(String userName);

    /**
     * Get attachments details for individual documents
     * 
     * @param userName
     * @param docId
     * @return
     */
    public UserAttachmentDetailsEntity getAttachmentDetails(String userName, Integer docId);
    
}
