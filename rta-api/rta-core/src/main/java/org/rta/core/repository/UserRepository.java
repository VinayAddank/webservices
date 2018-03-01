package org.rta.core.repository;

import java.util.List;

import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.UserType;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUserId(Long id);

    UserEntity findByUserName(String userName);
    
    List<UserEntity> findByUserType(UserType userType);
}
