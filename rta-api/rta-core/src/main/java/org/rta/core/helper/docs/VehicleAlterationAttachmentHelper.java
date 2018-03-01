package org.rta.core.helper.docs;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.docs.VehicleAlterationAttachmentEntity;
import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.AttachmentFrom;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleAlterationAttachmentHelper implements BaseConverter<VehicleAlterationAttachmentEntity, AttachmentDetailsModel> {

	@Autowired
	private VehicleDAO vehicleDAO;
	
    @Override
    public AttachmentDetailsModel convertToModel(VehicleAlterationAttachmentEntity source) {

        if (source == null) {
            return null;
        }
        DocTypesEntity docTypesEntity = source.getDocTypes();
        AttachmentDetailsModel model = new AttachmentDetailsModel();
        model.setId(docTypesEntity.getDocTypeId());
        model.setIsMandatory(docTypesEntity.getIsMandatory());
        model.setName(docTypesEntity.getName());
        model.setAttachmentDlId(source.getAttachmentDlId());
        model.setAttachmentTitle(source.getAttachmentTitle());
        model.setFileName(source.getFileName());
        model.setSource(source.getSource());
        model.setAttachmentFrom(AttachmentFrom.getAttachmentFrom(source.getAttachmentFrom()));
        model.setStatus(Status.getLabel(source.getStatus()));
        model.setVehicleRcId(source.getVehicle().getVehicleRcId().toString());
        model.setUserType(UserType.ROLE_BODY_BUILDER.getLabel());
        return model;
    }

    @Override
    public VehicleAlterationAttachmentEntity convertToEntity(AttachmentDetailsModel source) {
        if (source == null) {
            return null;
        }

        VehicleAlterationAttachmentEntity entity = new VehicleAlterationAttachmentEntity();
        entity.setAttachmentDlId(source.getAttachmentDlId());
        entity.setDocTypes(new DocTypesEntity(source.getId()));
        entity.setAttachmentTitle(source.getAttachmentTitle());
        entity.setFileName(source.getFileName());
        entity.setSource(source.getSource());
        entity.setAttachmentFrom(source.getAttachmentFrom().getValue());
        entity.setVehicle(vehicleDAO.get(Long.parseLong(source.getVehicleRcId())));
        return entity;
    }

    @Override
    public Collection<AttachmentDetailsModel> convertToModelList(Collection<VehicleAlterationAttachmentEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<VehicleAlterationAttachmentEntity> convertToEntityList(Collection<AttachmentDetailsModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }

}
