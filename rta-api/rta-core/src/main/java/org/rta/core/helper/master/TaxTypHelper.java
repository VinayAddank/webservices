package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.TaxTypeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.TaxTypeModel;
import org.springframework.stereotype.Component;

@Component
public class TaxTypHelper implements BaseConverter<TaxTypeEntity, TaxTypeModel> {
    @Override
    public TaxTypeModel convertToModel(TaxTypeEntity source) {
        if (source == null) {
            return null;
        }
        TaxTypeModel taxModel = new TaxTypeModel();
        taxModel.setId(source.getTaxTypeId());
        taxModel.setTaxTypeCode(source.getCode());
        taxModel.setName(source.getName());
        taxModel.setPercentage(source.getPercentage());
        taxModel.setStatus(source.getStatus());
        taxModel.setCreatedBy(source.getCreatedBy());
        taxModel.setCreatedOn(source.getCreatedOn());
        return taxModel;
    }

    @Override
    public Collection<TaxTypeModel> convertToModelList(Collection<TaxTypeEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());

    }

    @Override
    public TaxTypeEntity convertToEntity(TaxTypeModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<TaxTypeEntity> convertToEntityList(Collection<TaxTypeModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
