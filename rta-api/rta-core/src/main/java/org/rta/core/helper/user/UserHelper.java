package org.rta.core.helper.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.entity.user.UserEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.user.UserModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Component;

@Component
public class UserHelper implements BaseConverter<UserEntity, UserModel> {

    @Override
    public UserModel convertToModel(UserEntity source) {
        if (source == null) {
            return null;
        }
        UserModel um = new UserModel();
        um.setUserId(source.getUserId());
        um.setFirstName(source.getFirstName());
        um.setMiddleName(source.getMiddleName());
        um.setLastName(source.getLastName());
        um.setUserName(source.getUserName());
        um.setEmailId(source.getEmail());
        um.setMobile(source.getMobile());
        um.setPhone(source.getPhone());
        um.setStatus(source.getStatus());
        List<String> roles = new ArrayList<String>();
        if(!ObjectsUtil.isNull(source.getUserType())){
            roles.add(source.getUserType().toString());
        }
        if(!ObjectsUtil.isNull(source.getAadharNumber())){
        	um.setAadharNumber(source.getAadharNumber());
        }
        um.setUserRole(roles);
        um.setUserType(source.getUserType());
        return um;
    }

    @Override
    public Collection<UserModel> convertToModelList(Collection<UserEntity> source) {
        if (source == null) {
            return null;
        }
        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public UserEntity convertToEntity(UserModel source) {
    	if (ObjectsUtil.isNull(source)) {
            return null;
        }
    	UserEntity userEntity = new UserEntity();
    	userEntity.setUserId(source.getUserId());
    	userEntity.setFirstName(source.getFirstName());
    	userEntity.setMiddleName(source.getMiddleName());
        userEntity.setLastName(source.getLastName());
        userEntity.setUserName(source.getUserName().toUpperCase());
        userEntity.setEmail(source.getEmailId());
        userEntity.setMobile(source.getMobile());
        userEntity.setPhone(source.getPhone());
        userEntity.setStatus(source.getStatus());
        userEntity.setUserType(source.getUserType());
        return userEntity;
    }

    @Override
    public Collection<UserEntity> convertToEntityList(Collection<UserModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
