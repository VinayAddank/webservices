package org.rta.core.service.user;

import java.util.List;

import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.user.UserModel;
import org.rta.core.model.user.UserSignupModel;

public interface UserService {

    public UserModel findByUserId(Long id);

    public UserModel findByUserName(String userName);

    public List<UserModel> findAllUsers(String userType);
    
    public List<UserType> getUserRoles(String userDept); 
    
    public SaveUpdateResponse changePswd(Long id, String oldPswd, String newPswd);
    
    public SaveUpdateResponse changeRequest(UserModel model, String requesttype, String userName);
    
    public SaveUpdateResponse registerUser(UserModel userModel, String userName);

    UserSignupModel registerUser(UserSignupModel userModel);

    boolean isUserExists(String aadharNumber, UserType userType);

    public List<String> bcryptPasswordForUsers(String userType, String userName);
    
}
