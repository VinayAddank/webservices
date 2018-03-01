package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.DocTypesEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.DocTypesModel;
import org.springframework.stereotype.Component;

@Component
public class DocTypesHelper implements BaseConverter<DocTypesEntity, DocTypesModel> {

    @Override
    public Collection<DocTypesModel> convertToModelList(Collection<DocTypesEntity> source) {
        if(source == null){
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public DocTypesModel convertToModel(DocTypesEntity source) {
        if(source == null){
            return null;
        }
        DocTypesModel model = new DocTypesModel();
        model.setId(source.getDocTypeId());
        model.setName(source.getName());
        model.setIsMandatory(source.getIsMandatory());
        model.setRoleName(source.getRoleName());
        model.setModifiedBy(source.getModifiedBy());
        model.setModifiedOn(source.getModifiedOn());
        model.setCreatedBy(source.getCreatedBy());
        model.setCreatedOn(source.getCreatedOn());
        return model;
    }

    @Override
    public DocTypesEntity convertToEntity(DocTypesModel source) {
        if (source == null) {
            return null;
        }
        DocTypesEntity entity = new DocTypesEntity();
        entity.setDocTypeId(source.getId());
        entity.setName(source.getName());
        entity.setIsMandatory(source.getIsMandatory());
        entity.setRoleName(source.getRoleName());
        entity.setModifiedBy(source.getModifiedBy());
        entity.setModifiedOn(source.getModifiedOn());
        entity.setCreatedBy(source.getCreatedBy());
        entity.setCreatedOn(source.getCreatedOn());
        return entity;
    }

    @Override
    public Collection<DocTypesEntity> convertToEntityList(Collection<DocTypesModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }
}
