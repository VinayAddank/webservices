package org.rta.registration.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleSubClassType;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustomerModel;
import org.rta.core.model.secondVechicle.SecondVehicleDetailsModel;
import org.rta.core.model.vehicle.RCListModel;
import org.rta.core.service.customer.CustomerService;
import org.rta.core.service.secondvehicle.SecondVechicleInfoService;
import org.rta.core.service.secondvehicle.SecondVehicleRejectionService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CmsServiceController {

    private static final Logger log = Logger.getLogger(CmsServiceController.class);

    public static String TEST_XML_STRING = "";
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private SecondVehicleRejectionService secondVehicleRejectionService;

    @Autowired
    private SecondVechicleInfoService secondVechicleInfoService;

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CCO','ROLE_AO','ROLE_RTO')")
    @RequestMapping(value = "/secondvehicle/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> secondVehicleCheck(@PathVariable("vehiclercid") Long vehicleRcId) {
        log.info("Getting second vechile details and rejection history.");
        SecondVehicleDetailsModel secondVehicle = secondVehicleRejectionService.get(vehicleRcId);
        if (null != secondVehicle) {
            return ResponseEntity.ok(secondVehicle);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @RequestMapping(path = "/secondVechicle", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> secondVechicleInfo(@RequestHeader("Authorization") String token,
            @RequestParam(value = "strFName", required = true) String strFName,
            @RequestParam(value = "strFHName", required = true) String strFHName,
            @RequestParam(value = "strLName", required = true) String strLName,
            @RequestParam(value = "strDOB", required = true) String strDOB) throws InvalidEngineNumberException,
            FileNotFoundException, UnsupportedEncodingException, JAXBException, JSONException {
        if (ObjectsUtil.isNull(strFName) || ObjectsUtil.isNull(strFHName) || ObjectsUtil.isNull(strLName)
                || ObjectsUtil.isNull(strDOB)) {
            throw new IllegalArgumentException("bad request");
        }
        String newdataSetModel =
                secondVechicleInfoService.GetsecondvehiclecheckforotsiDealer(strFName, strFHName, strLName, strDOB);
        TEST_XML_STRING = newdataSetModel;
        JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
        String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
        if (ObjectsUtil.isNull(jsonPrettyPrintString)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(jsonPrettyPrintString);

    }

    @RequestMapping(path = "/secondVechicle/{vehicleRcId}/{vehicleclass}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> secondVechicleInfo(@RequestHeader("Authorization") String token,
            @PathVariable("vehicleRcId") Long vehicleRcId, @PathVariable("vehicleclass") String vehicleClass)
            throws InvalidEngineNumberException, FileNotFoundException, UnsupportedEncodingException, JAXBException,
            JSONException {
        if (ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("vehicleRcId can't be null.");
        }

/*        // This will be not null if subclass is among(MMCN,MCYN,OBPN,MCRN),In that case skip second vehicle search
        if (VehicleSubClassType.getVehicleSubClass(vehicleClass) == null) {
*/ 
        // This will be not null if subclass is among(MMCN,MCYN,OBPN,MCRN),In that case DO second vehicle search
               if (VehicleSubClassType.getVehicleSubClass(vehicleClass) != null) {
            CustomerModel customer = customerService.getCustomerIndvDetail(vehicleRcId);
            if (customer != null) {
                String newdataSetModel =
                        secondVechicleInfoService.GetsecondvehiclecheckforotsiDealer(customer.getFirstName(),
                                customer.getFatherName(), customer.getLastName(), customer.getDateOfBirth());
                TEST_XML_STRING = newdataSetModel;
                JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
                String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
                if (ObjectsUtil.isNull(jsonPrettyPrintString)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
                return ResponseEntity.status(HttpStatus.OK).body(jsonPrettyPrintString);
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @PreAuthorize("hasRole('ROLE_CCO')")
    @RequestMapping(value = "/secondvehicle/{vehiclercid}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> secondVehicle(@PathVariable("vehiclercid") Long vehicleRcId,
            @RequestBody SecondVehicleDetailsModel secondVehicleModel) {
        if (ObjectsUtil.isNull(secondVehicleModel)) {
            log.info("Wrong SecondVehicleDetailsModel.");
            throw new IllegalArgumentException("Bad request.");
        }
        String token = request.getHeader(tokenHeader);
        log.info("SAVING second vechile details and rejection history.");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        UserType userType = null;
        try {
			userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		} catch (IllegalArgumentException e) {
			userType = UserType.ROLE_CCO;
		}
        try {
            secondVehicleRejectionService.saveDetails(secondVehicleModel, vehicleRcId, username, userId, userType.getValue());
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	throw new IllegalArgumentException("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !" + vehicleRcId);    
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Second vehicle rejection entry already exists for:" + vehicleRcId);
        }
    }

    @RequestMapping(path = "/secondVechiclePaySkip/{vehicleRcId}/{isNocApplicable}", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> secondVehiclePaymentSkip(@RequestHeader("Authorization") String token, 
            @PathVariable("vehicleRcId") Long vehicleRcId, 
            @PathVariable("isNocApplicable") Boolean isNocApplicable) 
                    throws InvalidEngineNumberException, FileNotFoundException, UnsupportedEncodingException, JAXBException, JSONException {
        if (ObjectsUtil.isNull(vehicleRcId)) {
            SaveUpdateResponse saveResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
             saveResponse.setMessage("VehicleRcId cannot be null !!!");
             return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(saveResponse);
        }
        SaveUpdateResponse saveResponse = secondVehicleRejectionService.secondVehiclePaymentSkip(vehicleRcId, isNocApplicable);
        return ResponseEntity.status(HttpStatus.OK).body(saveResponse);
    }
    
    /**
     * Lock the RC for transaction. Called from CFST system to prevent parallel transaction on a single RC.
     * @param token
     * @param rcList
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CFST','ROLE_ADMIN')")
    @RequestMapping(path = "/vehicle/lock/rc", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> lockRCTxn(@RequestHeader("Authorization") String token, @RequestBody RCListModel rcList){
    	SaveUpdateResponse res = new SaveUpdateResponse();
		try {
			String username = jwtTokenUtil.getUsernameFromToken(token);
			res = customerService.lockRC(username, rcList);
		} catch (VehicleRcNotFoundException e) {
			res.setStatus(SaveUpdateResponse.FAILURE);
			res.setMessage(e.getMessage());
			res.setCode(HttpStatus.NOT_FOUND.value());
		}
		return ResponseEntity.ok(res);
    }
    
    /**
     * Unlock the locked RC for Transaction. Called from CFST system.
     * 
     * @param token
     * @param rcList list of RCs
     * @param dateTime it's optional parameter. If provided will unlock the RCs which are locked before that else will unlock the
     * RCs locked before 24 hours.
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CFST','ROLE_ADMIN')")
    @RequestMapping(path = "/vehicle/un-lock/rc", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> unLockRCTxn(@RequestHeader("Authorization") String token, @RequestBody RCListModel rcList,
    		@RequestParam(value = "unlock_till", required = false) Long dateTime){
    	SaveUpdateResponse res = new SaveUpdateResponse();
		try {
			String username = jwtTokenUtil.getUsernameFromToken(token);
			Long daysDiff = 2592000L;
			if(ObjectsUtil.isNull(dateTime)){
				dateTime = DateUtil.addReduceDays(new Date().getTime(), -1)/1000;
			} else if(DateUtil.toCurrentUTCTimeStamp().compareTo(dateTime) < 0 || DateUtil.toCurrentUTCTimeStamp().compareTo(dateTime) > daysDiff){
				res.setStatus(SaveUpdateResponse.FAILURE);
				res.setMessage("Days diff can not be greater than 10 days from current days.");
				return ResponseEntity.ok(res);
			}
			res = customerService.unLockRC(username, rcList, dateTime);
		} catch (VehicleRcNotFoundException e) {
			res.setStatus(SaveUpdateResponse.FAILURE);
			res.setMessage(e.getMessage());
			res.setCode(HttpStatus.NOT_FOUND.value());
		}
		return ResponseEntity.ok(res);
    }
}
