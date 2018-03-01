package org.rta.core.helper.application.rejection;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.docs.AttachmentDetailsEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.application.rejection.RejectionHistoryModel;
import org.rta.core.model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * this is conversion between RejectionHistoryModel and RejectionHistoryEntity
 */

@Component
public class RejectionHistoryHelper implements BaseConverter<RejectionHistoryEntity, RejectionHistoryModel> {

	@Autowired
	private VehicleDAO vehicleDAO;
	
    @Override
    public RejectionHistoryModel convertToModel(RejectionHistoryEntity source) {

        if (source == null) {
            return null;
        }

        RejectionHistoryModel model = new RejectionHistoryModel();
        model.setRejectionHistId(source.getRejectionHistId());
        model.setStatus(Status.getStatus(source.getStatus()));
        model.setComment(source.getComment());
        model.setTitle(source.getTitle());
        model.setCreatedBy(source.getCreatedBy());
        model.setCreatedOn(source.getCreatedOn());
        model.setModifiedBy(source.getModifiedBy());
        model.setModifiedOn(source.getModifiedOn());
        UserModel user = new UserModel();
        user.setUserId(source.getUserId().getUserId());
        user.setFirstName(source.getUserId().getUserName());
        model.setUser(user);
        model.setVehicleRCId(source.getVehicleRCId().getVehicleRcId());
        if (source.getAttachmentDetailsId() != null)
            model.setAttachmentDetailsId(source.getAttachmentDetailsId().getAttachmentDlId());
        
        return model;
    }

    @Override
    public RejectionHistoryEntity convertToEntity(RejectionHistoryModel source) {

        if (source == null) {
            return null;
        }

        RejectionHistoryEntity entity = new RejectionHistoryEntity();
        entity.setRejectionHistId(source.getRejectionHistId());
        entity.setStatus(source.getStatus().getValue());
        entity.setComment(source.getComment());
        entity.setTitle(source.getTitle());
        entity.setCreatedBy(source.getCreatedBy());
        entity.setCreatedOn(source.getCreatedOn());
        entity.setModifiedBy(source.getModifiedBy());
        entity.setModifiedOn(source.getModifiedOn());
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(source.getUser().getUserId());
        entity.setUserId(userEntity);
        entity.setVehicleRCId(vehicleDAO.get(source.getVehicleRCId()));
        if (source.getAttachmentDetailsId() != null)
            entity.setAttachmentDetailsId(new AttachmentDetailsEntity(source.getAttachmentDetailsId()));

        return entity;
    }

    @Override
    public Collection<RejectionHistoryModel> convertToModelList(Collection<RejectionHistoryEntity> source) {

        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<RejectionHistoryEntity> convertToEntityList(Collection<RejectionHistoryModel> source) {

        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }


}
