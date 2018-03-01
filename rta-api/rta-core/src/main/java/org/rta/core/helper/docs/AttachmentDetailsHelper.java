package org.rta.core.helper.docs;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.helper.BaseConverter;
import org.rta.core.helper.master.DocTypesHelper;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.rta.core.model.master.DocTypesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDetailsHelper implements BaseConverter<AttachmentDetailsEntity, AttachmentDetailsModel> {

    @Autowired
    private DocTypesHelper docTypesHelper;

    @Override
    public AttachmentDetailsModel convertToModel(AttachmentDetailsEntity source) {

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
        model.setVehicleRcId(source.getVehicle().getVehicleRcId().toString());
        if(source.getUserRole() != null){
        	 model.setUserType(UserType.getUserType(source.getUserRole()).getLabel());
        }
        return model;
    }

    @Override
    public AttachmentDetailsEntity convertToEntity(AttachmentDetailsModel source) {
        if (source == null) {
            return null;
        }

        AttachmentDetailsEntity entity = new AttachmentDetailsEntity();
        entity.setAttachmentDlId(source.getAttachmentDlId());
        entity.setDocTypes(new DocTypesEntity(source.getId()));
        entity.setAttachmentTitle(source.getAttachmentTitle());
        entity.setFileName(source.getFileName());
        entity.setSource(source.getSource());
        entity.setAttachmentFrom(source.getAttachmentFrom().getValue());
        // entity.setStatus(Status.getStatus(source.getStatus()).getValue());
        // entity.setVehicle(new VehicleRCEntity(Long.parseLong(source.getVehicleRcId()), null));
        entity.setCreatedBy(source.getCreatedBy());
        entity.setCreatedOn(source.getCreatedOn());
        entity.setModifiedBy(source.getModifiedBy());
        entity.setModifiedOn(source.getModifiedOn());

        return entity;
    }

    @Override
    public Collection<AttachmentDetailsModel> convertToModelList(Collection<AttachmentDetailsEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<AttachmentDetailsEntity> convertToEntityList(Collection<AttachmentDetailsModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }

}
