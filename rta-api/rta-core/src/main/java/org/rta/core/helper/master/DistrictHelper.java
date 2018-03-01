package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.DistrictModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DistrictHelper implements BaseConverter<DistrictEntity, DistrictModel> {

    @Autowired
    StateHelper stateHelper;

    @Override
    public Collection<DistrictModel> convertToModelList(Collection<DistrictEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public DistrictModel convertToModel(DistrictEntity source) {
        if(source == null){
            return null;
        }
        DistrictModel dm = new DistrictModel();
        dm.setId(source.getDistrictId());
        dm.setCode(source.getCode());
        dm.setStateModel(stateHelper.convertToModel(source.getStateEntity()));
        dm.setName(source.getName());
        dm.setStatus(source.getStatus());
        dm.setCreatedBy(source.getCreatedBy());
        dm.setCreatedOn(source.getCreatedOn());
        return dm;
    }

    @Override
    public DistrictEntity convertToEntity(DistrictModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<DistrictEntity> convertToEntityList(Collection<DistrictModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

    public Collection<DistrictModel> convertToModelListNoState(Collection<DistrictEntity> source) {
        return source.stream().map(s -> convertToModelNoState(s)).collect(Collectors.toList());
    }

    public DistrictModel convertToModelNoState(DistrictEntity source) {
        if(source == null){
            return null;
        }
        DistrictModel dm = new DistrictModel();
        dm.setId(source.getDistrictId());
        dm.setCode(source.getCode());
        dm.setName(source.getName());
        dm.setStatus(source.getStatus());
        dm.setCreatedBy(source.getCreatedBy());
        dm.setCreatedOn(source.getCreatedOn());
        return dm;
    }

}
