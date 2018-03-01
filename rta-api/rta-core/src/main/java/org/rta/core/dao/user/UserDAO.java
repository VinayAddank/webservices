package org.rta.core.dao.user;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;

public interface UserDAO extends GenericDAO<UserEntity> {

    UserEntity findByUserId(Long id);

    UserEntity findByUserName(String name);

    UserEntity findByAadhar(String aadharNumber);

    List<UserEntity> findUsersByAadhar(String aadharNumber, UserType userType);
    
    public UserEntity findByUserNameNdUserType(String userName , UserType userType);
}
