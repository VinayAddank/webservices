package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.enums.PRType;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.application.ApplicationModel;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class RegistrationNumberController {
    private static final Logger log = Logger.getLogger(RegistrationNumberController.class);
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.claim.secret}")
    private String claimSecret;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private VehicleService vehicleService;

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/registnumber/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> registionNumber(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc) {
        log.info("::::::::registionNumber::::::::::");
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("registionNumber :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            saveUpdateResponse = vehicleService.updateStepSeven(vehicleRcId);
            saveUpdateResponse.setVehicleRcId(vehicleRcIdEnc);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	saveUpdateResponse.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        } catch(IllegalArgumentException ie){
        	saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        } catch (Exception e) {
            log.error("Exception while registionNumber :: " + e.getMessage());
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);

    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/{vehiclercid}/prtype/{prtype}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateNumberType(HttpServletRequest request,
            @PathVariable("vehiclercid") Long vehicleRcId, @PathVariable("prtype") String prTypeRequest) {
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        if (ObjectsUtil.isNull(userName)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (ObjectsUtil.isNull(vehicleRcId)) {
        	saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        PRType prType = PRType.getPRType(prTypeRequest);
        if (ObjectsUtil.isNull(prType)) {
        	saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Invalid prType!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        try {
            saveUpdateResponse = vehicleService.updatePRNumberType(userName, vehicleRcId, prType);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	saveUpdateResponse.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        } catch (Exception e) {
            log.error("Exception while updating prType " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);

    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/{vehiclercid}/prtype", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPrType(HttpServletRequest request, @PathVariable("vehiclercid") Long vehicleRcId) {
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(userName)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (ObjectsUtil.isNull(vehicleRcId)) {
        	errorModel.setMessage("Invalid vehicleRcId!");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        ApplicationModel applicationModel = null;
        try {
            applicationModel = new ApplicationModel();
            applicationModel.setVehicleRcId(vehicleRcId);
            applicationModel.setPrType(vehicleService.getPRType(vehicleRcId));
        } catch (VehicleRcNotFoundException e) {
            log.error("VehicleRcId : " + vehicleRcId + " not found ");
            errorModel.setMessage("Vehicle RC Not Found !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception while getting prType " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Some Error Occured !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(applicationModel);

    }
    
}
