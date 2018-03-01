package org.rta.core.helper.vehicle.cms;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.vehicle.cms.ModelDetailsEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.vehicle.cms.ModelDetailsModel;
import org.springframework.stereotype.Component;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
@Component
public class ModelDetailsHelper implements BaseConverter<ModelDetailsEntity, ModelDetailsModel> {

    @Override
    public ModelDetailsModel convertToModel(ModelDetailsEntity source) {
        if (source == null) {
            return null;
        }
        ModelDetailsModel modelDetailsModel = new ModelDetailsModel();
        modelDetailsModel.setMakerMasterId(source.getMakerMasterId().getMakerId());
        modelDetailsModel.setModelDetailsId(source.getModelDetailsId());
        modelDetailsModel.setModelName(source.getModelName());
        return modelDetailsModel;
    }

    @Override
    public ModelDetailsEntity convertToEntity(ModelDetailsModel source) {

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<ModelDetailsModel> convertToModelList(Collection<ModelDetailsEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<ModelDetailsEntity> convertToEntityList(Collection<ModelDetailsModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
