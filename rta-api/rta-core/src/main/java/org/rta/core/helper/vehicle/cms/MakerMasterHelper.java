package org.rta.core.helper.vehicle.cms;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.vehicle.cms.MakerMasterEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.vehicle.cms.MakerMasterModel;
import org.springframework.stereotype.Component;

/**
 *	@Author sohan.maurya created on Sep 9, 2016.
 */
@Component
public class MakerMasterHelper implements BaseConverter<MakerMasterEntity, MakerMasterModel> {

    @Override
    public MakerMasterModel convertToModel(MakerMasterEntity source) {
        if (source == null) {
            return null;
        }
        MakerMasterModel makerMasterModel = new MakerMasterModel();
        makerMasterModel.setMakerId(source.getMakerId());
        makerMasterModel.setMakerName(source.getMakerName());
        return makerMasterModel;
    }

    @Override
    public MakerMasterEntity convertToEntity(MakerMasterModel source) {
        if (source == null) {
            return null;
        }
        MakerMasterEntity makerMasterEntity = new MakerMasterEntity();
        makerMasterEntity.setMakerId(source.getMakerId());
        makerMasterEntity.setMakerName(source.getMakerName());
        return makerMasterEntity;
    }

    @Override
    public Collection<MakerMasterModel> convertToModelList(Collection<MakerMasterEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<MakerMasterEntity> convertToEntityList(Collection<MakerMasterModel> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
    }

}
