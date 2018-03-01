/**
 * 
 */
package org.rta.registration.service.activiti.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.model.activiti.ActivitiResponseModel;
import org.rta.core.model.activiti.ProcessUser;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.user.UserService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.activiti.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author arun.verma
 *
 */
@Service
public class ActivitiServiceImpl implements ActivitiService{

	private static final Logger logger = Logger.getLogger(ActivitiServiceImpl.class);
    
    @Autowired
    private UserService userService;
    
    @Value("${url.citizen.activiti}")
    private String activitiBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public List<ProcessUser> fillUsersToActiviti() {
        List<UserModel> list = userService.findAllUsers(null);
        List<ProcessUser> users = new ArrayList<>();
        list.forEach(um->{
            if(!(ObjectsUtil.isNull(um) || ObjectsUtil.isNull(um.getUserName()) || ObjectsUtil.isNull(um.getUserRole())) && um.getUserRole().size() > 0){
                ProcessUser user = new ProcessUser();
                user.setFirstName(um.getFirstName());
                user.setLastName(um.getLastName());
                user.setPassword(um.getUserName()+"@123");
                user.setUserId(um.getUserName());
                user.setGroupName(um.getUserRole().get(0));
                user.setGroupId(um.getUserRole().get(0));
                user.setGroupType(um.getUserRole().get(0));
                users.add(user);
            }
        });
        logger.info("User list fetched from registration.....");
        String path = activitiBaseUrl + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ProcessUser>> httpEntity = new HttpEntity<List<ProcessUser>>(users, headers);
        ResponseEntity<ActivitiResponseModel> response = restTemplate.exchange(path, HttpMethod.PUT, httpEntity, ActivitiResponseModel.class);
        return users;
    }
    
    @Override
    public List<ProcessUser> fillUsersToActiviti(List<UserModel> usersList) {
        List<ProcessUser> users = new ArrayList<>();
        usersList.forEach(um->{
            if(!(ObjectsUtil.isNull(um) || ObjectsUtil.isNull(um.getUserName()) || ObjectsUtil.isNull(um.getUserRole())) && um.getUserRole().size() > 0){
                ProcessUser user = new ProcessUser();
                user.setFirstName(um.getFirstName());
                user.setLastName(um.getLastName());
                user.setPassword(um.getUserName()+"@123");
                user.setUserId(um.getUserName());
                user.setGroupName(um.getUserRole().get(0));
                user.setGroupId(um.getUserRole().get(0));
                user.setGroupType(um.getUserRole().get(0));
                users.add(user);
            }
        });
        logger.info("Got new user list .....");
        String path = activitiBaseUrl + "/users";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ProcessUser>> httpEntity = new HttpEntity<List<ProcessUser>>(users, headers);
        ResponseEntity<ActivitiResponseModel> response = restTemplate.exchange(path, HttpMethod.PUT, httpEntity, ActivitiResponseModel.class);
        return users;
    }

}
