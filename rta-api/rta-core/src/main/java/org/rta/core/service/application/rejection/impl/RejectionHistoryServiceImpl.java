package org.rta.core.service.application.rejection.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.application.rejection.RejectionHistoryDAO;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.enums.UserType;
import org.rta.core.helper.application.rejection.RejectionHistoryHelper;
import org.rta.core.model.application.rejection.RejectionHistoryModel;
import org.rta.core.service.application.rejection.RejectionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rejectionHistoryService")
public class RejectionHistoryServiceImpl implements RejectionHistoryService {

    @Autowired
    private RejectionHistoryDAO rejectionHistoryDAO;

    @Autowired
    private RejectionHistoryHelper rejectionHistoryHelper;

//    @Transactional
//    @Override
//    public RejectionHistoryModel get(Long attachmentDetailsId, UserType userType) {
//
//        return rejectionHistoryHelper
//                .convertToModel(rejectionHistoryDAO.get(new AttachmentDetailsEntity(attachmentDetailsId), userType));
//    }

    @Transactional
    @Override
    public List<RejectionHistoryModel> getAll(Long attachmentDetailsId) {

        return (List<RejectionHistoryModel>) rejectionHistoryHelper
                .convertToModelList(rejectionHistoryDAO.getAll(new AttachmentDetailsEntity(attachmentDetailsId)));
    }

    @Transactional
    @Override
    public Long save(RejectionHistoryModel model) {

        return (Long) rejectionHistoryDAO.save(rejectionHistoryHelper.convertToEntity(model));
    }

}
