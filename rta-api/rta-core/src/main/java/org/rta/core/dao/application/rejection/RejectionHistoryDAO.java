package org.rta.core.dao.application.rejection;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;


public interface RejectionHistoryDAO extends GenericDAO<RejectionHistoryEntity> {

    public List<RejectionHistoryEntity> getAll(AttachmentDetailsEntity attachmentDetailsId);
    
    public List<RejectionHistoryEntity> getRejectionHistory(Long vehicleRcId/*, Integer iteration*/);

    public List<RejectionHistoryEntity> get(AttachmentDetailsEntity attachmentDetailsId, UserType userType);
    
    public Long countApplicationsWithUserStatus(Long userId, Status status);

    public List<RejectionHistoryEntity> getApplicationWithUserStatus(Long userId, Status status, String query, Long from,
            Long to, Integer perPageRecords, Integer pageNumber);
}
