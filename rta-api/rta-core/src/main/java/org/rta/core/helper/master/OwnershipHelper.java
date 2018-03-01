package org.rta.core.helper.master;

import java.util.Collection;
import java.util.stream.Collectors;
import org.rta.core.entity.master.OwnershipTypeEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.master.OwnershipModel;
import org.springframework.stereotype.Component;

@Component
public class OwnershipHelper  implements BaseConverter<OwnershipTypeEntity, OwnershipModel> {

	@Override
	public OwnershipModel convertToModel(OwnershipTypeEntity source) {
	    if(source == null){
	        return null;
	    }
		OwnershipModel ownerModel=new OwnershipModel();
		ownerModel.setOwnershipCode(source.getCode());
		ownerModel.setName(source.getName());
		ownerModel.setStatus(source.getStatus());
		ownerModel.setCreatedBy(source.getCreatedBy());
		ownerModel.setCreatedOn(source.getCreatedOn());
		return ownerModel;
	}

	@Override
	public Collection<OwnershipModel> convertToModelList(Collection<OwnershipTypeEntity> source) {
	    if(source == null){
            return null;
        }
		return source.stream().map(s->convertToModel(s)).collect(Collectors.toList());
	
	}

    @Override
    public OwnershipTypeEntity convertToEntity(OwnershipModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<OwnershipTypeEntity> convertToEntityList(Collection<OwnershipModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
