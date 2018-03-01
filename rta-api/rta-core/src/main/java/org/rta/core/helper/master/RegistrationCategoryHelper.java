package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.RegistrationCategoryEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCategoryHelper
        implements BaseConverter<RegistrationCategoryEntity, RegistrationCategoryModel> {

    @Override
    public Collection<RegistrationCategoryModel> convertToModelList(Collection<RegistrationCategoryEntity> source) {
        if(source == null){
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public RegistrationCategoryModel convertToModel(RegistrationCategoryEntity source) {
        if(source == null){
            return null;
        }
        RegistrationCategoryModel rcm = new RegistrationCategoryModel();
        rcm.setRegistrationCategoryId(source.getRegistrationCategoryId());
        rcm.setCode(source.getCode());
        rcm.setName(source.getName());
        rcm.setStatus(source.getStatus());
        rcm.setCreatedBy(source.getCreatedBy());
        rcm.setCreatedOn(source.getCreatedOn());
        rcm.setUpdatedBy(source.getModifiedBy());
        rcm.setUpdatedOn(source.getModifiedOn());
        return rcm;
    }

    @Override
    public RegistrationCategoryEntity convertToEntity(RegistrationCategoryModel source) {
        RegistrationCategoryEntity target = new RegistrationCategoryEntity();
        target.setCode(source.getCode());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedOn(source.getCreatedOn());
        target.setName(source.getName());
        target.setRegistrationCategoryId(source.getRegistrationCategoryId());
        target.setStatus(source.getStatus());
        target.setModifiedBy(source.getUpdatedBy());
        target.setModifiedOn(source.getUpdatedOn());
        return target;
    }

    @Override
    public Collection<RegistrationCategoryEntity> convertToEntityList(Collection<RegistrationCategoryModel> source) {
        if(source == null){
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }
}
