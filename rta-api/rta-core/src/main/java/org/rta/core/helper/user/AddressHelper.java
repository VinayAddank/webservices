package org.rta.core.helper.user;

import java.util.Collection;

import org.rta.core.entity.user.AddressEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.user.AddressModel;
import org.springframework.stereotype.Component;

@Component
public class AddressHelper implements BaseConverter<AddressEntity, AddressModel> {

    @Override
    public AddressModel convertToModel(AddressEntity source) {
        if (source == null) {
            return null;
        }
        AddressModel am = new AddressModel();
        am.setCity(source.getCity());
        am.setCountry(source.getCountry());
        am.setDistrict(source.getDistrict());
        am.setDoorNo(source.getDoorNo());
        am.setMandal(source.getMandal().getMandalId());
        am.setPostOffice(source.getPinCode());
        am.setState(source.getState());
        am.setStatus(source.getStatus());
        am.setStreet(source.getStreet());
        am.setType(source.getType());
        am.setUserType(source.getUserType());
        return am;
    }

    @Override
    public AddressEntity convertToEntity(AddressModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<AddressModel> convertToModelList(Collection<AddressEntity> source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<AddressEntity> convertToEntityList(Collection<AddressModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
