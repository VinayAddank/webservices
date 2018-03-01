package org.rta.core.helper.master;

import java.util.Collection;

import org.rta.core.entity.master.TaxMasterEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.TaxMasterModel;
import org.springframework.stereotype.Component;

@Component
public class TaxMasterHelper implements BaseConverter<TaxMasterEntity, TaxMasterModel> {

    @Override
    public TaxMasterModel convertToModel(TaxMasterEntity source) {
        TaxMasterModel tm = new TaxMasterModel();
        tm.setTaxPercent(source.getTaxPercent());
        return tm;
    }

    @Override
    public TaxMasterEntity convertToEntity(TaxMasterModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<TaxMasterModel> convertToModelList(Collection<TaxMasterEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<TaxMasterEntity> convertToEntityList(Collection<TaxMasterModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
