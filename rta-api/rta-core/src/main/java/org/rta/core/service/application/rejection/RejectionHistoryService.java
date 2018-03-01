package org.rta.core.service.application.rejection;

import java.util.List;

import org.rta.core.enums.UserType;
import org.rta.core.model.application.rejection.RejectionHistoryModel;

public interface RejectionHistoryService {

//    public RejectionHistoryModel get(Long attachmentDetailsId, UserType userType);

    public List<RejectionHistoryModel> getAll(Long attachmentDetailsId);

    public Long save(RejectionHistoryModel model);
}
