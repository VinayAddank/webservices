package org.rta.core.helper.secondvehicle;

import java.util.Collection;

import org.rta.core.entity.secondvehicle.SecondVehicleDetailsEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.secondVechicle.SecondVehicleDetailsModel;
import org.springframework.stereotype.Component;

@Component
public class SecondVehicleDetailsHelper
        implements BaseConverter<SecondVehicleDetailsEntity, SecondVehicleDetailsModel> {

    @Override
    public SecondVehicleDetailsEntity convertToEntity(SecondVehicleDetailsModel source) {
        SecondVehicleDetailsEntity secondVehicleDetail = new SecondVehicleDetailsEntity();
        secondVehicleDetail.setChassisNo(source.getChassisNo());
        secondVehicleDetail.setEngineNo(source.getEngineNo());
        secondVehicleDetail.setAddress(source.getAddress());
        secondVehicleDetail.setDateOfBirth(source.getDateOfBirth());
        secondVehicleDetail.setOwnerName(source.getOwnerName());
        secondVehicleDetail.setOwnerLastName(source.getOwnerLastName());
        secondVehicleDetail.setOwnerFatherName(source.getOwnerFatherName());
        secondVehicleDetail.setRegistrationNumber(source.getRegistrationNumber());
        secondVehicleDetail.setVehicleRcId(source.getVehicleRcId());
        secondVehicleDetail.setUserId(source.getUserId());
        secondVehicleDetail.setCreatedBy(source.getCreatedBy());
        secondVehicleDetail.setModifiedBy(source.getModifiedBy());
        secondVehicleDetail.setCreatedOn(source.getCreatedOn());
        secondVehicleDetail.setModifiedOn(source.getModifiedOn());
        secondVehicleDetail.setIteration(source.getIteration());
        secondVehicleDetail.setPaidTax(source.getPaidTax());
        secondVehicleDetail.setIsValidSecondVehicle(source.getIsValidSecondVehicle());

        return secondVehicleDetail;
    }

    @Override
    public SecondVehicleDetailsModel convertToModel(SecondVehicleDetailsEntity source) {
        if(source == null){
            return null;
        }
        SecondVehicleDetailsModel secondVehicleDetail = new SecondVehicleDetailsModel();
        secondVehicleDetail.setChassisNo(source.getChassisNo());
        secondVehicleDetail.setEngineNo(source.getEngineNo());
        secondVehicleDetail.setAddress(source.getAddress());
        secondVehicleDetail.setDateOfBirth(source.getDateOfBirth());
        secondVehicleDetail.setOwnerName(source.getOwnerName());
        secondVehicleDetail.setOwnerLastName(source.getOwnerLastName());
        secondVehicleDetail.setOwnerFatherName(source.getOwnerFatherName());
        secondVehicleDetail.setRegistrationNumber(source.getRegistrationNumber());
        secondVehicleDetail.setVehicleRcId(source.getVehicleRcId());
        secondVehicleDetail.setUserId(source.getUserId());
        secondVehicleDetail.setCreatedBy(source.getCreatedBy());
        secondVehicleDetail.setModifiedBy(source.getModifiedBy());
        secondVehicleDetail.setCreatedOn(source.getCreatedOn());
        secondVehicleDetail.setModifiedOn(source.getModifiedOn());
        secondVehicleDetail.setIteration(source.getIteration());
        secondVehicleDetail.setPaidTax(source.getPaidTax());
        secondVehicleDetail.setComment(source.getRejectionHistory().getComment());
        secondVehicleDetail.setIsValidSecondVehicle(source.getIsValidSecondVehicle());
        return secondVehicleDetail;
    }

    @Override
    public Collection<SecondVehicleDetailsModel> convertToModelList(Collection<SecondVehicleDetailsEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<SecondVehicleDetailsEntity> convertToEntityList(Collection<SecondVehicleDetailsModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
