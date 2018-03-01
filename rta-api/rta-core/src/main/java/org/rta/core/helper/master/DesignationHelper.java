package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.DesignationEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.DesignationModel;
import org.springframework.stereotype.Component;

@Component
public class DesignationHelper implements BaseConverter<DesignationEntity, DesignationModel> {

    @Override
    public Collection<DesignationModel> convertToModelList(Collection<DesignationEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public DesignationModel convertToModel(DesignationEntity source) {
        DesignationModel dm = new DesignationModel();
        dm.setId(source.getDesignationId());
        dm.setCode(source.getCode());
        dm.setName(source.getName());
        dm.setStatus(source.getStatus());
        dm.setCreatedBy(source.getCreatedBy());
        dm.setCreatedOn(source.getCreatedOn());
        return dm;
    }

    @Override
    public DesignationEntity convertToEntity(DesignationModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DesignationEntity> convertToEntityList(Collection<DesignationModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
