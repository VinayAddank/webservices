package org.rta.core.dao.user;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.enums.UserType;

public interface AddressDAO extends GenericDAO<AddressEntity> {

    public AddressEntity findByUserId(Long id);
    public AddressEntity findByUserIdAndType(Long id,String type) ;

    public List<AddressEntity> getAllByUserType(UserType userType);
}
