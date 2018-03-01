package org.rta.registration.controller;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Date;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.hibernate.service.spi.ServiceException;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.AadhaarDetailsRequestModel;
import org.rta.core.model.AadhaarTCSDetailsRequestModel;
import org.rta.core.model.AadharModel;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.user.AddressModel;
import org.rta.core.service.aadhar.AadharInfoService;
import org.rta.core.service.aadhar.AadharTCSInfoService;
import org.rta.core.service.customer.CustomerService;
import org.rta.core.service.user.AddressService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * AadharController provides details for aadhar
 * 
 * @author RaviTeja
 *
 */
@RestController
public class AadharController {

    private static final Logger logger = Logger.getLogger(AadharController.class);
    
    private static String EKYC_SOAP_ADDRESS = "http://125.16.9.139:83/APONLINEKUASERVICEPROD2.1/APONLINEKYCSERVICE2.1.asmx";

    @Autowired
    private AadharTCSInfoService aadharTCSInfoService;
    @Autowired
    private AadharInfoService aadharInfoService;
    @Autowired
    private AddressService addressService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Value("${rta.aadhar.ecentric.agencyname}")
    private String agencyName4ECentric;
    @Value("${rta.aadhar.ecentric.agencycode}")
    private String agencyCode4ECentric;
    @Value("${rta.aadhar.ecentric.ekycoption}")
    private String eKYCOption4ECentric;
    
    @Value("${rta.aadhar.tcs.departement}")
    private String departement;
    @Value("${rta.aadhar.tcs.scheme}")
    private String scheme;
    @Value("${rta.aadhar.tcs.version}")
    private String version4TCS;
    @Value("${rta.aadhar.tcs.biometrictype}")
    private String biometricType4TCS;
    @Value("${rta.aadhar.tcs.certificateexpiry}")
    private String certificateExpiry4TCS;
    @Value("${rta.aadhar.tcs.service}")
    private String service4TCS;
    @Value("${rta.aadhar.tcs.endpoint}")
    private String endpointUrl;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private VehicleService vehicleService;

    @RequestMapping(path = "/PIDBlock", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> PIDBlock(@RequestHeader("Authorization") String token,
            @Valid @RequestBody AadhaarDetailsRequestModel requestModel)
            throws InvalidEngineNumberException, FileNotFoundException, UnsupportedEncodingException {
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        
        String udc = "15072016H52140502450";
        String deviceName = "HAMSTERPRO20";
        AadharModel aadharModel = null;
        SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(requestModel)) {
            logger.error("AadhaarDetailsRequestModel IS NULL");
            errorResponse.setMessage("Aadhar Request Model is null !!!");
            errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        try {
            aadharModel = aadharInfoService.getAadhaarDemographicDataBySRDHSecuredeKYC(requestModel.getEncryptedPid(),
                    requestModel.getEncSessionKey(), requestModel.getEncHmac(), requestModel.getUid_num(), agencyName4ECentric,
                    agencyCode4ECentric, eKYCOption4ECentric, requestModel.getTimeStamp(), udc, deviceName);
             logger.debug("Aadhaar Model IS :"+ aadharModel);
            if (ObjectsUtil.isNull(aadharModel)) {
                logger.error("Aadhar data received : " + aadharModel);
                errorResponse.setMessage("Data not received from Aadhar !!!");
                errorResponse.setCode(HttpStatus.NOT_FOUND.value());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
            } else {
                logger.info("Going to save aadhaar model ");
                aadharInfoService.saveOrUpdate(aadharModel, userName);
                aadharModel = aadharInfoService.setInsideApAndMandal(aadharModel);
                logger.info("Aadhar saved successfully...");
            }
        } catch (Exception ex) {
            logger.error("************* Exception occured in aadhaar controller ********************");
            logger.debug(ex);
            errorResponse.setMessage("Some error occuered with Aadhar step !!!");
            errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(aadharModel);
    }

    @RequestMapping(path = "/PIDBlockTCS", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> PIDBlockTCS(@RequestHeader("Authorization") String token,
            @Valid @RequestBody AadhaarTCSDetailsRequestModel requestModel){
        SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        AadharModel aadharModel = null;
        try {
            aadharModel = aadharTCS(token, requestModel);
        } catch (ServiceException e) {
            logger.error("ServiceException from Aadhar!!!");
            errorResponse.setMessage("ServiceException from Aadhar!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (RemoteException e) {
            logger.error("Remote error occured while accessing Aadhar Service !!!");
            errorResponse.setMessage("Remote error occured while accessing Aadhar Service !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException while calling Aadhar!!!");
            errorResponse.setMessage("Some error occured while Aadhar Service!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding found while calling Aadhar service !!!");
            errorResponse.setMessage("UnsupportedEncoding found while calling Aadhar service !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (NotFoundException e) {
            logger.error("Not recieved any data from Aadhar !!!");
            errorResponse.setMessage("Not recieved any data from Aadhar !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (javax.xml.rpc.ServiceException e) {
            logger.error("RPC call error Aadhar Service!!!");
            errorResponse.setMessage("RPC call error Aadhar Service!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch(Exception ex){
            logger.error("Exception :: " + ex.getMessage());
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(aadharModel);
    }
    
    private AadharModel aadharTCS(String token, AadhaarTCSDetailsRequestModel requestModel) throws NotFoundException, ServiceException, RemoteException, FileNotFoundException, UnsupportedEncodingException, javax.xml.rpc.ServiceException{
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        AadharModel aadharModel = null;
        String ip = "NA"; //fix value
        //String fdc = "2140502450"; //fix value
        String deviceName = "computer"; //fix value
        
        String dealerPinCode = requestModel.getPincode();
        if(StringsUtil.isNullOrEmpty(dealerPinCode)) {
            Long userId = jwtTokenUtil.getUserIdFromToken(token);
            AddressModel am = addressService.findByUserId(userId);
            if(null != am) dealerPinCode = am.getPostOffice(); else dealerPinCode = "";
        }
        
        String srt = DateUtil.getDate("MM/dd/yyyy hh:mm:ss a", "IST", new Date()); //"9/16/2016 12:52:15 AM"
        logger.info("For Aadhar: Server time stamp:"+srt+ " and Client request time:"+requestModel.getCrt());
       
        URL endpoint = getEndPointURL(endpointUrl);
        if(ObjectsUtil.isNull(endpoint)) {
            endpoint = getEndPointURL(EKYC_SOAP_ADDRESS);
            if(ObjectsUtil.isNull(endpoint)) {
                throw new RuntimeException("Endpoint not found !!!");
            }
        }
        logger.debug(" ****** GENERATED  ENDPOINT url:"+endpoint.toString());
        aadharModel = aadharTCSInfoService.AADHAAR_EKYC_FINGER_AUTHENTICATION(endpoint, requestModel.getUid_num(),
                requestModel.getTid(), requestModel.getUdc(), requestModel.getRdsId(), ip, srt,
                requestModel.getCrt(), requestModel.getEncSessionKey(), requestModel.getEncryptedPid(),
                requestModel.getEncHmac(), certificateExpiry4TCS, biometricType4TCS, dealerPinCode, version4TCS, scheme, departement, service4TCS,
                requestModel.getDpId(), requestModel.getRdsVer(), requestModel.getConsentme(), userName, deviceName, requestModel.getAttemptType(), 
                requestModel.getDc(),requestModel.getMi(), requestModel.getMc());
        if (ObjectsUtil.isNull(aadharModel)) {
            logger.error("********Received null model from TCS  Aadhaar");
            throw new NotFoundException("No Data Received From Aadhar !!!");
        } else if ("FAILED".equalsIgnoreCase(aadharModel.getAuth_status())) {
            return aadharModel;
        } else {
            logger.info(" **** Saving aadhaar model ****");
            aadharInfoService.saveOrUpdate(aadharModel, userName);
            logger.info(" **** Aadhar data saved successfully ****");
            aadharModel = aadharInfoService.setInsideApAndMandal(aadharModel);
        }
        return aadharModel;
    }
    
    private URL getEndPointURL(String url) {
        logger.info(" ****** Provided ekyc url:"+url);
        URL endpoint = null;
         try {
             endpoint = new URL(url);
         } catch (java.net.MalformedURLException e) {
             logger.info(" ****** ERROR can not create ENDPOINT URL from provided ekyc url:"+url+". Error:"+e.getMessage());
         }
         return endpoint;
     }
    
    @RequestMapping(value = "aadhar/updateaddress/vehicle/{vehicleRcId}", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> updatePermanentAdrsWithAadhar(@RequestHeader("Authorization") String token, @PathVariable("vehicleRcId") Long vehicleRcId,
            @Valid @RequestBody AadhaarTCSDetailsRequestModel requestModel){
        logger.info("Aadhar punched for vehicleRcId : " + vehicleRcId + " aadhar no : " + requestModel.getUid_num());
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if(ObjectsUtil.isNull(vehicleRcId)){
            errorResponse.setMessage("Invalid VehicleRcId !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        if(!vehicleService.isMatchedSavedAadhar(requestModel.getUid_num(), Long.valueOf(vehicleRcId))){
            errorResponse.setMessage("Aadhar Number Not Matched !!!");
            errorResponse.setCode(HttpStatus.CONFLICT.value());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        AadharModel aadharModel = null;
        try {
            aadharModel = aadharTCS(token, requestModel);
        } catch (ServiceException e) {
            logger.error("ServiceException from Aadhar!!!");
            errorResponse.setMessage("ServiceException from Aadhar!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (RemoteException e) {
            logger.error("Remote error occured while accessing Aadhar Service !!!");
            errorResponse.setMessage("Remote error occured while accessing Aadhar Service !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException while calling Aadhar!!!");
            errorResponse.setMessage("Some error occured while Aadhar Service!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncoding found while calling Aadhar service !!!");
            errorResponse.setMessage("UnsupportedEncoding found while calling Aadhar service !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (NotFoundException e) {
            logger.error("Not recieved any data from Aadhar !!!");
            errorResponse.setMessage("Not recieved any data from Aadhar !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch (javax.xml.rpc.ServiceException e) {
            logger.error("RPC call error Aadhar Service!!!");
            errorResponse.setMessage("RPC call error Aadhar Service!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } catch(Exception ex){
            logger.error("Exception :: " + ex.getMessage());
            errorResponse.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        }
        SaveUpdateResponse response = new SaveUpdateResponse();
        if ("FAILED".equalsIgnoreCase(aadharModel.getAuth_status())) {
            errorResponse.setMessage(aadharModel.getAuth_err_code());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
        } else {
            try {
                response = customerService.updatePermanentAdrsWithAadhar(userId, userName, vehicleRcId, aadharModel);
            }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
                logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
                errorResponse.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
            }
        }
        logger.info("Response = > " + response);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "aadhar/details/{aadharnumber}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAadharDetails(@PathVariable("aadharnumber") Long aadharNumber) {
        logger.info("aadhar punched for aadharNumber : " + aadharNumber);
        if (ObjectsUtil.isNull(aadharNumber)) {
            ResponseEntity.badRequest().build();
        }
        AadharModel aadharModel = null;
        try{
            aadharModel = aadharTCSInfoService.getAadharDetails(aadharNumber);
        } catch (NotFoundException nfe) {
            logger.error("There is not recards for aadhar number = > " + aadharNumber);
            throw new IllegalArgumentException("There is not recards for aadhar number = > " + aadharNumber);
        }

        logger.info("Successfully send Aadhar Details = > " + aadharNumber);
        return ResponseEntity.ok(aadharModel);
    }
    
    /**
     * Move Aadhar pending application to RTA end. This method will be used if users aadhar is not getting authenticated and 
     * L3 team will complain to move the application to RTA end.
     * 
     * @param token
     * @param vehicleRcId
     * @param aadharNo
     * @return
     */
    @RequestMapping(value = "aadharpending/movetorta/vehicle/{vehicleRcId}/aadhar/{aadhar_no}", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> moveAadharPendingToRTA(@RequestHeader("Authorization") String token, @PathVariable("vehicleRcId") Long vehicleRcId,
    		@PathVariable("aadhar_no") String aadharNo){
    	SaveUpdateResponse res = customerService.moveAadharPendingToRTA(vehicleRcId, aadharNo);
    	return ResponseEntity.ok(res);
    }
}
