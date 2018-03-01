/**
 * 
 */
package org.rta.registration.service.activiti;

import java.util.List;

import org.rta.core.model.activiti.ProcessUser;
import org.rta.core.model.user.UserModel;

/**
 * @author arun.verma
 *
 */
public interface ActivitiService {
    
    public List<ProcessUser> fillUsersToActiviti();

    List<ProcessUser> fillUsersToActiviti(List<UserModel> usersList);
}
