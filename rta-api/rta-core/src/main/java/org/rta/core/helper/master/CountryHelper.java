package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.master.CountryEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.CountryModel;
import org.springframework.stereotype.Component;

@Component
public class CountryHelper implements BaseConverter<CountryEntity, CountryModel> {

    @Override
    public Collection<CountryModel> convertToModelList(Collection<CountryEntity> source) {
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public CountryModel convertToModel(CountryEntity source) {
        CountryModel cm = new CountryModel();
        cm.setId(source.getCountryId());
        cm.setCode(source.getCode());
        cm.setName(source.getName());
        cm.setStatus(source.getStatus());
        cm.setCreatedBy(source.getCreatedBy());
        cm.setCreatedOn(source.getCreatedOn());
        return cm;
    }

    @Override
    public CountryEntity convertToEntity(CountryModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<CountryEntity> convertToEntityList(Collection<CountryModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
