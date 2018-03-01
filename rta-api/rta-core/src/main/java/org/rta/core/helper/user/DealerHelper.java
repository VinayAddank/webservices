package org.rta.core.helper.user;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.user.DealerEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.user.DealerModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Component;

@Component
public class DealerHelper implements BaseConverter<DealerEntity, DealerModel> {

    @Override
    public DealerModel convertToModel(DealerEntity source) {
        if (source == null) {
            return null;
        }
        DealerModel dm = new DealerModel();
        dm.setShowRoomName(source.getName());
        dm.setFax(source.getFax());
        dm.setParentId(source.getParentId());

        UserEntity ue = source.getUser();
        dm.setUserId(ue.getUserId());
        dm.setUserName(ue.getUserName());
        dm.setUserType(ue.getUserType().getLabel());
        dm.setFirstName(ue.getFirstName());
        dm.setMiddleName(ue.getMiddleName());
        dm.setLastName(ue.getLastName());
        dm.setEmail(ue.getEmail());
        dm.setMobile(ue.getMobile());
        dm.setStatus(ue.getStatus());
        dm.setPhone(ue.getPhone());
        dm.setCreatedOn(ue.getCreatedOn());
        dm.setCreatedBy(ue.getCreatedBy());
        dm.setModifiedBy(ue.getModifiedBy());
        dm.setModifiedOn(ue.getModifiedOn());
        dm.setLastLogin(ue.getLastLogin());
        dm.setEligibleToPay(source.getEligibleToPay());

        return dm;
    }

    @Override
    public Collection<DealerModel> convertToModelList(Collection<DealerEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public DealerEntity convertToEntity(DealerModel source) {
    	
    	if (ObjectsUtil.isNull(source)) {
            return null;
        }
    	DealerEntity target = new DealerEntity();
    	target.setName(source.getShowRoomName());
    	target.setFax(source.getFax());
        target.setDealerSign(source.getUserSignature());
        return target;
    }

    @Override
    public Collection<DealerEntity> convertToEntityList(Collection<DealerModel> source) {
        // TODO Auto-generated method stub
        return null;
    }
    
	public UserEntity getUserEntity(DealerModel source){
		
		if (ObjectsUtil.isNull(source)) {
            return null;
        }
		UserEntity ue = new UserEntity();
        ue.setUserName(source.getUserName().toUpperCase());
	    ue.setUserType(UserType.getUserType(source.getUserType()));
	    ue.setFirstName(source.getFirstName());
	    ue.setMiddleName(source.getMiddleName());
	    ue.setLastName(source.getLastName());
	    ue.setEmail(source.getEmail());
	    ue.setMobile(source.getMobile());
	    ue.setPhone(source.getPhone());
	    ue.setPassword(source.getPassword());
	    return ue;
	}
}
