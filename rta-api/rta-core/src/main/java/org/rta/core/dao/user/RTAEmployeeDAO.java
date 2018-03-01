package org.rta.core.dao.user;

import java.util.List;

import org.rta.core.dao.base.GenericDAO;
import org.rta.core.entity.user.RTAEmployeeEntity;
import org.rta.core.enums.UserType;

public interface RTAEmployeeDAO extends GenericDAO<RTAEmployeeEntity> {

    public abstract RTAEmployeeEntity getByUserId(Long userId);
    
    public abstract List<RTAEmployeeEntity> getRtaEmployeeDetails(Long userId, String employeeName);

    public RTAEmployeeEntity getRTAEmp(String rtaOfficeCode, UserType userType, Boolean status);
    
    
}
