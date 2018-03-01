/**
 * 
 */
package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.exception.InsurancePolicyValidationException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.insurance.IibResponseModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.insurance.InsuranceDetailsService;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arun.verma
 *
 */
@RestController
public class InsuranceController {
    private static final Logger log = Logger.getLogger(InsuranceController.class);

    @Autowired
    InsuranceDetailsService insuranceDetailsService;

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    @PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER')")
    @RequestMapping(path = "/insurancedetails", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> saveUpdate(@Valid @RequestBody InsuranceDetailsModel insuranceDetailsModel) {
        log.info("saveInsuranceDetails :: Insurance Type Code => " + insuranceDetailsModel.getInsuranceTypeCode()
                + ", VehicleRc Id => " + insuranceDetailsModel.getVehicleRcId());

        SaveUpdateResponse saveUpdateModel = new SaveUpdateResponse();
        // set to InsuranceDtlId
        insuranceDetailsModel.setInsuranceDtlId(null);
        // decrypt VehicleRcId and set it again in model
        String vehicleRcIdDecrypted =
                RtaCryptoUtil.parseSecureToken(insuranceDetailsModel.getVehicleRcId(), claimSecret);
        try {
            insuranceDetailsModel.setVehicleRcId(String.valueOf(Long.parseLong(vehicleRcIdDecrypted)));
        } catch (NumberFormatException ex) {
            log.error("saveUpdate :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        }
        if(insuranceDetailsModel.getStartDate().compareTo(insuranceDetailsModel.getEndDate()) >= 0){
            log.error("saveUpdate :: policy start date should be less than policy end date =>" + insuranceDetailsModel.getStartDate() + " : " + insuranceDetailsModel.getEndDate());
            saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage("Policy start date should be less than policy end date!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        }
        
        try {
            String token = request.getHeader(tokenHeader);
            UserModel user = new UserModel();
            user.setUserName(jwtTokenUtil.getUsernameFromToken(token));
            user.setUserId(jwtTokenUtil.getUserIdFromToken(token));
            saveUpdateModel = insuranceDetailsService.saveUpdate(insuranceDetailsModel, user);
            String vehicleRcIdEncripted =
                    RtaCryptoUtil.generateSecureToken(String.valueOf(saveUpdateModel.getVehicleRcId()), claimSecret);
            saveUpdateModel.setVehicleRcId(vehicleRcIdEncripted);
            if(saveUpdateModel.getCode() == HttpStatus.IM_USED.value()){
                return ResponseEntity.status(HttpStatus.IM_USED).body(saveUpdateModel);
            }
            return ResponseEntity.ok(saveUpdateModel);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	saveUpdateModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        } catch(IllegalArgumentException ie){
        	saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        } catch (DataIntegrityViolationException ex) {
            log.error("Exception while saving/updating Insurance Details :: " + ex.getMessage());
            saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        } catch (InvalidDataAccessApiUsageException ex) {
            log.error("Exception while saving/updating Insurance Details :: " + ex.getMessage());
            saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage("Invalid Data Received(InvalidDataAccessApiUsageException) !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
        } catch (Exception ex) {
            log.debug(ex);
			log.error("Exception while saving Insurance Details :: " + ex.getMessage());
			saveUpdateModel.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateModel.setMessage("Invalid Data Received !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateModel);
		}
        
    }

    @RequestMapping(path = "/insurancedetails/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getInsuranceDetails(@PathVariable("vehiclercid") String vehicleRcIdEnc) {
        log.info("getInsuranceDetails :: vehicleRcIdEncripted => " + vehicleRcIdEnc);
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("getInsuranceDetails :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        InsuranceDetailsModel insuranceDetails = null;
        try{
        	insuranceDetails = insuranceDetailsService.getInsuranceDtlsByVehicleRcId(vehicleRcId);
        } catch(IllegalArgumentException ie){
        	errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        insuranceDetails.setVehicleRcId(vehicleRcIdEnc);
        return ResponseEntity.ok(insuranceDetails);
    }
    
    /**
     * @author Gautam.kumar
     * @description To validate insurance policy
     * @param regNo
     * @return
     */
    @RequestMapping(value = "/iib/insurance/details/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> isValidInsurancePolicy(@PathVariable("vehicleRcId") Long vehicleRcId) {
        log.info("isValidInsurancePolicy:: regNo => " + vehicleRcId);
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        InsuranceDetailsModel insuranceDetailsModel = null;
        try {
            insuranceDetailsModel = insuranceDetailsService.getIibInsuranceDetailsByVehicleRcId(vehicleRcId);
        } catch (InsurancePolicyValidationException e) {
            errorModel.setMessage(e.getErrMsg());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch(Exception ex){
            log.error("Exception while getting insurance details from iib .....");
            errorModel.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.ok(insuranceDetailsModel);
    }
}
