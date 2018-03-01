package org.rta.core.helper.docs;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.docs.UserAttachmentDetailsEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.Status;
import org.rta.core.helper.BaseConverter;
import org.rta.core.helper.master.DocTypesHelper;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.rta.core.model.master.DocTypesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAttachmentDetailsHelper implements BaseConverter<UserAttachmentDetailsEntity, AttachmentDetailsModel> {

    @Autowired
    private DocTypesHelper docTypesHelper;

    @Autowired
    private UserDAO userDAO;



    @Override
    public AttachmentDetailsModel convertToModel(UserAttachmentDetailsEntity source) {

        if (source == null) {
            return null;
        }
        DocTypesModel docType = docTypesHelper.convertToModel(source.getDocTypes());

        AttachmentDetailsModel model = new AttachmentDetailsModel();
        model.setId(docType.getId());
        model.setIsMandatory(docType.getIsMandatory());
        model.setName(docType.getName());
        model.setAttachmentDlId(source.getAttachmentDlId());
        model.setAttachmentTitle(source.getAttachmentTitle());
        model.setFileName(source.getFileName());
        model.setSource(source.getSource());
        model.setAttachmentFrom(AttachmentFrom.getAttachmentFrom(source.getAttachmentFrom()));
        model.setStatus(Status.getLabel(source.getStatus()));
        model.setUserName(userDAO.findByUserId(source.getUserId().getUserId()).getUserName());

        return model;
    }

    @Override
    public UserAttachmentDetailsEntity convertToEntity(AttachmentDetailsModel source) {
        if (source == null) {
            return null;
        }

        UserAttachmentDetailsEntity entity = new UserAttachmentDetailsEntity();
        entity.setAttachmentDlId(source.getAttachmentDlId());
        entity.setDocTypes(new DocTypesEntity(source.getId()));
        entity.setAttachmentTitle(source.getAttachmentTitle());
        entity.setFileName(source.getFileName());
        entity.setSource(source.getSource());
        entity.setAttachmentFrom(source.getAttachmentFrom().getValue());

        return entity;
    }

    @Override
    public Collection<AttachmentDetailsModel> convertToModelList(Collection<UserAttachmentDetailsEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<UserAttachmentDetailsEntity> convertToEntityList(Collection<AttachmentDetailsModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }

}
