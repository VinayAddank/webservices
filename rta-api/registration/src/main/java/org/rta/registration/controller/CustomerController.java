package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.customer.CustomerDetailsRequestModel;
import org.rta.core.model.user.AddressChangeModel;
import org.rta.core.service.customer.CustomerService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    private static final Logger log = Logger.getLogger(CustomerController.class);

    private final CustomerService customerService;

    private final JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Value("${rta.step.aadhar}")
    private Integer currentStep;

    @Value("${jwt.header}")
    private String tokenHeader;

	@Autowired
	private CmsLiveSyncingService cmsLiveSyncingService;

    @Autowired
    public CustomerController(final CustomerService customerService, final JwtTokenUtil jwtTokenUtil) {
        this.customerService = customerService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    // @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "/{currentStep}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<?> saveCustomerDetails(@RequestHeader("Authorization") String token,
            @Valid @RequestBody CustomerDetailsRequestModel requestModel) {
        Long vehicleRCId = null;

        log.debug(requestModel);

        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(requestModel)) {
            errorModel.setMessage(" Customer Details Required !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (ObjectsUtil.isNull(userId) || StringsUtil.isNullOrEmpty(userName)) {
            errorModel.setMessage("User is Required !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        try {
            log.info("****** CUSTOMER VehicleRcId :" + requestModel.getVehicleRcId());
            vehicleRCId = customerService.update(userId, requestModel, userName, currentStep);
            if (ObjectsUtil.isNull(vehicleRCId)) {
                errorModel.setMessage("Vehicle Rc Not Found !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (IllegalArgumentException e) {
            errorModel.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception occured while saving customer => " + e.getMessage());
            errorModel.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SaveUpdateResponse(SaveUpdateResponse.SUCCESS,
                "saved successfully", encrypt(vehicleRCId), currentStep));
    }

    // @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/customerInfoStepOne", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> customerInfoStepOne(@RequestHeader("Authorization") String token,
            @RequestParam("vehiclercid") String vehileRcId) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (StringsUtil.isNullOrEmpty(vehileRcId)) {
            errorModel.setMessage("VehileRcId null or empty !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        CustomerDetailsRequestModel customerDetailsRequestModel = null;
        try {
            customerDetailsRequestModel = customerService.getCustomerInfoStepOne(vehileRcId);
            if (null == customerDetailsRequestModel) {
                return ResponseEntity.noContent().build();
            }
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch(IllegalArgumentException ie){
            log.error("customerInfoStepOne IllegalArgumentException :: " + ie.getMessage());
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception ex) {
            log.error("customerInfoStepOne Exception :: " + ex.getMessage());
            log.log(Level.ERROR, ex.getMessage(), ex);
            errorModel.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        log.info("::::::::::customerInfoStepOne:::::start::::::");
        return ResponseEntity.status(HttpStatus.OK).body(customerDetailsRequestModel);
    }

    private String encrypt(Long str) {
        return RtaCryptoUtil.generateSecureToken(String.valueOf(str), claimSecret);
    }

    /**
     * Update Customer Details Service but may be change this for withEffectDate
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(path = "/details", method = RequestMethod.PUT,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateCustomerDetails(@Valid @RequestBody AddressChangeModel model, HttpServletRequest request) {
        log.info("::::::::::updateCustomerDetails:::::start::::::");
        SaveUpdateResponse response = null;
        boolean flag = false;
        try {
            response = customerService.updateCustomerDetails(model, jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
            flag = true;
        } catch (DataMismatchException dme) {
        	response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, dme.getMessage(), null);
        }
        if(flag){
        	try{
        		String result = cmsLiveSyncingService.cmsLiveSyncingService(model.getVehicleRcId(), null, model.getServiceType().getCode());
            	log.info("::: Syncing data with CMS status :::: " + result);
        	}catch (Exception e) {
        		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
			}
        }
        log.info("::::::::::updateCustomerDetails:::::start::::::");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
