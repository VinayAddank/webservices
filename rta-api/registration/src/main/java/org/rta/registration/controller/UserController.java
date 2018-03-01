package org.rta.registration.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.activiti.ProcessUser;
import org.rta.core.model.user.DealerModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.model.user.UserSignupModel;
import org.rta.core.service.user.DealerService;
import org.rta.core.service.user.UserService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.RtaXmlUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.activiti.ActivitiService;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	
    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private DealerService dealerService;
    
    @Autowired
    private ActivitiService activitiService;


    @RequestMapping(value = "/user", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserFromToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        UserModel user = userService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    @RequestMapping(value = "/user/details/{userid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserDetails( @PathVariable(value="userid") Long userId ) {
        if(ObjectsUtil.isNull(userId)){
        	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    	UserModel user = userService.findByUserId(userId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/users", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "usertype", required = false) String userType, HttpServletRequest request) {
        if (RtaXmlUtil.xmlResponseFormat(request)) {
        	String str = RtaXmlUtil.marshal(UserModel.class, userService.findAllUsers(userType), "users");
            if (str != null) {
                return ResponseEntity.ok(str);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        }
        return ResponseEntity.ok(userService.findAllUsers(userType));
    }
    
    @RequestMapping(value = "/user/roles", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserRoles(@RequestParam(name="userdept", required=false) String userDept) {
    	
        return ResponseEntity.ok(userService.getUserRoles(userDept));
    }

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/users/{name}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUser(@PathVariable("name") String name) {
        UserModel user = userService.findByUserName(name);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else if(user.getUserType() == UserType.ROLE_DEALER){
            return ResponseEntity.ok(dealerService.findByUserName(name));
        }
        return ResponseEntity.ok(user);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/changerequest", method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> changeRequest(@Valid @RequestBody UserModel userModel, 
    		@RequestParam(name = "requesttype", required = true) String requestType, HttpServletRequest request) {
    	if (ObjectsUtil.isNull(userModel)|| StringsUtil.isNullOrEmpty(requestType)) {
    		throw new IllegalArgumentException("bad request");
    	}
 	  	SaveUpdateResponse saveUpdateModel = null;
        try {
             String userName = jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader));
             saveUpdateModel = userService.changeRequest(userModel, requestType, userName);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
         }
         logger.info(" User Details Succsssfully Updated ");
         return ResponseEntity.ok(saveUpdateModel);
     }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/register/rta",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserModel userModel, HttpServletRequest request) {
        logger.info(userModel);

        if (ObjectsUtil.isNull(userModel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        SaveUpdateResponse saveUpdateModel = null;
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (StringsUtil.isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("bad request");
        }
        try {
        	saveUpdateModel = userService.registerUser(userModel, userName);
    	} catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        	} catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }
        logger.info(" User Details Saved Succsssfully  ");
        return ResponseEntity.ok(saveUpdateModel);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/user/register/other",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody DealerModel model, HttpServletRequest request) {

        if (ObjectsUtil.isNull(model)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        SaveUpdateResponse saveUpdateModel = null;
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (StringsUtil.isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("bad request");
        }
        try {
        	saveUpdateModel = dealerService.saveOrUpdate(model, userName);
    	} catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        	} catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }
        logger.info(" User Details Saved Succsssfully  ");
        return ResponseEntity.ok(saveUpdateModel);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CITIZEN')")
    @RequestMapping(path = "/user/new",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignupModel userModel, HttpServletRequest request) {

        if (ObjectsUtil.isNull(userModel)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (StringsUtil.isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("bad request");
        }
        UserSignupModel userSignupModel;
        try {
            userSignupModel = userService.registerUser(userModel);
            
            // add users in activiti also
            List<UserModel> userModelList = new ArrayList<>();
            UserModel user = userService.findByUserId(userSignupModel.getUserId());
            userModelList.add(user);
            activitiService.fillUsersToActiviti(userModelList);
            return ResponseEntity.ok(userSignupModel);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving User Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping(path = "/user/changepassword", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> changePswd(@RequestHeader("Authorization") String token, @RequestBody String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        HashMap<String, String> node;
        try {
            node = mapper.readValue(body, HashMap.class);
        } catch (JsonParseException e) {
        	errorModel.setMessage("Invalid Json Data.");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (JsonMappingException e) {
        	errorModel.setMessage("Invalid Json Mapping Data.");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (IOException e) {
        	errorModel.setMessage(e.getMessage());
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String oldPswd = node.get("oldPswd").toString();
        String newPswd = node.get("newPswd").toString();
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (ObjectsUtil.isNull(userId) || ObjectsUtil.isNull(userName)) {
        	errorModel.setMessage("Invalid User !!!");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        SaveUpdateResponse response = null;
        try{
        	response = userService.changePswd(userId, oldPswd, newPswd);
        } catch(IllegalArgumentException ie){
        	errorModel.setMessage(ie.getMessage());
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/rta/user", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public UserModel getRTAUserByToken(@RequestHeader("Authorization") String token){
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if(null == userName) return null;
        logger.info(".... Stark check for username: "+userName);
        UserModel user = userService.findByUserName(userName);
        user.setUserName(userName);
        return user;
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/rta/user/citizenactiviti/user", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> fillAllUsersToCitizenActiviti(){
        logger.info(".. send users to activiti ...");
        List<ProcessUser> list = activitiService.fillUsersToActiviti();
        return ResponseEntity.ok(list);
    }
    
    @PreAuthorize("hasRole('ROLE_CITIZEN')")
    @RequestMapping(path = "/users/aadhar", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> isUserExists(@RequestHeader("Authorization") String token, @RequestParam(name = "uid") String aadharNumber, @RequestParam(name = "usertype") String userType){
        boolean isUserExists = userService.isUserExists(aadharNumber, UserType.getUserType(userType));
        return ResponseEntity.ok(isUserExists);
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(path = "/bcrypt/password", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> changealluserpwdtohash(@RequestParam(name = "usertype", required = false) String userType,
            @RequestParam(name = "username", required = false) String userName) {
        logger.info("Updating user passwords to related bcrypt hash.....");
        List<String> failedFor = userService.bcryptPasswordForUsers(userType, userName);

        return ResponseEntity.ok(failedFor);
    }
}
