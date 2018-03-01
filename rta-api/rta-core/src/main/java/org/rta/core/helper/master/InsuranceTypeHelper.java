package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.InsuranceTypeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.InsuranceTypeModel;
import org.springframework.stereotype.Component;

/**
 * @Author sohan.maurya created on Jul 12, 2016.
 */
@Component
public class InsuranceTypeHelper implements BaseConverter<InsuranceTypeEntity, InsuranceTypeModel> {

    @Override
    public InsuranceTypeEntity convertToEntity(InsuranceTypeModel model) {
        if(model == null){
            return null;
        }
        InsuranceTypeEntity entity = new InsuranceTypeEntity();
        entity.setInsuranceTypeId(model.getId());
        entity.setName(model.getName());
        entity.setCode(model.getCode());
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedOn(model.getCreatedOn());
        entity.setStatus(model.getStatus());
        return entity;
    }



    @Override
    public InsuranceTypeModel convertToModel(InsuranceTypeEntity source) {
        if(source == null){
            return null;
        }
        InsuranceTypeModel model = new InsuranceTypeModel();
        model.setId(source.getInsuranceTypeId());
        model.setName(source.getName());
        model.setCode(source.getCode());
        model.setCreatedBy(source.getCreatedBy());
        model.setCreatedOn(source.getCreatedOn());
        model.setStatus(source.getStatus());

        return model;
    }

    @Override
    public Collection<InsuranceTypeModel> convertToModelList(Collection<InsuranceTypeEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<InsuranceTypeEntity> convertToEntityList(Collection<InsuranceTypeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
