package org.rta.core.helper.master.registration;

import java.util.Collection;

import org.rta.core.entity.master.registration.SpecialNumberEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.registration.SpecialNumberModel;
import org.springframework.stereotype.Component;

@Component
public class SpecialNumberHelper implements BaseConverter<SpecialNumberEntity, SpecialNumberModel> {

    @Override
    public SpecialNumberModel convertToModel(SpecialNumberEntity source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SpecialNumberEntity convertToEntity(SpecialNumberModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<SpecialNumberModel> convertToModelList(Collection<SpecialNumberEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<SpecialNumberEntity> convertToEntityList(Collection<SpecialNumberModel> source) {
        // TODO Auto-generated method stub
        return null;
    }


}
