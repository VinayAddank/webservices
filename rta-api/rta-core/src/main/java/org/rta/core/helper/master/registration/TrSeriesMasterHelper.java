package org.rta.core.helper.master.registration;

import java.util.Collection;

import org.rta.core.entity.master.registration.TrSeriesMasterEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.registration.TrSeriesMasterModel;
import org.springframework.stereotype.Component;

@Component
public class TrSeriesMasterHelper implements BaseConverter<TrSeriesMasterEntity, TrSeriesMasterModel> {

    @Override
    public TrSeriesMasterModel convertToModel(TrSeriesMasterEntity source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TrSeriesMasterEntity convertToEntity(TrSeriesMasterModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<TrSeriesMasterModel> convertToModelList(Collection<TrSeriesMasterEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<TrSeriesMasterEntity> convertToEntityList(Collection<TrSeriesMasterModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
