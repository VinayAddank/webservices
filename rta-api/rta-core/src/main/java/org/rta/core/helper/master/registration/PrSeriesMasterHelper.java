package org.rta.core.helper.master.registration;

import java.util.Collection;

import org.rta.core.entity.master.registration.PrSeriesMasterEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.registration.PrSeriesMasterModel;
import org.springframework.stereotype.Component;

@Component
public class PrSeriesMasterHelper implements BaseConverter<PrSeriesMasterEntity, PrSeriesMasterModel> {

    @Override
    public PrSeriesMasterModel convertToModel(PrSeriesMasterEntity source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PrSeriesMasterEntity convertToEntity(PrSeriesMasterModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PrSeriesMasterModel> convertToModelList(Collection<PrSeriesMasterEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<PrSeriesMasterEntity> convertToEntityList(Collection<PrSeriesMasterModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
