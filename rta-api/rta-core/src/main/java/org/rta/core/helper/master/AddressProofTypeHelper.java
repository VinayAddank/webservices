package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.AddressProofTypeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.AddressProofTypeModel;
import org.springframework.stereotype.Component;

/**
 *	@Author sohan.maurya created on Jul 12, 2016.
 */
@Component
public class AddressProofTypeHelper implements BaseConverter<AddressProofTypeEntity, AddressProofTypeModel> {

    public AddressProofTypeEntity convertModelToEntity(AddressProofTypeModel model) {
        if(model == null){
            return null;
        }
        AddressProofTypeEntity entity = new AddressProofTypeEntity();
        entity.setAddressProofId(model.getId());
        entity.setName(model.getName());
        entity.setCode(model.getCode());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedOn(model.getCreatedOn());
        entity.setStatus(model.getStatus());
        return entity;
    }


    @Override
    public AddressProofTypeModel convertToModel(AddressProofTypeEntity source) {
        if(source == null){
            return null;
        }
        AddressProofTypeModel model = new AddressProofTypeModel();
        model.setId(source.getAddressProofId());
        model.setName(source.getName());
        model.setCode(source.getCode());
        model.setCreatedBy(source.getCreatedBy());
        model.setCreatedOn(source.getCreatedOn());
        model.setStatus(source.getStatus());
        return model;
    }

    @Override
    public Collection<AddressProofTypeModel> convertToModelList(Collection<AddressProofTypeEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }


    @Override
    public AddressProofTypeEntity convertToEntity(AddressProofTypeModel source) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Collection<AddressProofTypeEntity> convertToEntityList(Collection<AddressProofTypeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
