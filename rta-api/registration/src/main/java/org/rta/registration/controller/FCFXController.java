/**
 * 
 */
package org.rta.registration.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rta.core.enums.ServiceValidation;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.CFXModel;
import org.rta.core.model.certificate.CFXNoticeModel;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.fcfx.FCFXService;
import org.rta.core.service.fcfx.exception.FitnessExpiredException;
import org.rta.core.service.fcfx.exception.FitnessNotFoundException;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arun.verma
 *
 */

@RestController
public class FCFXController {

    private static final Log log = LogFactory.getLog(FCFXController.class);

    @Autowired
    private FCFXService fcfxService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    /**
     * Create application In citizen with pr number and aadhar number of mvi
     * 
     * @param pr_number
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_MVI')")
    @RequestMapping(value = "/fcfx/app/prno/{pr_number}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createAppInCitizen(@PathVariable("pr_number") String prNo, @RequestHeader("Authorization") String token) {
        log.info("Creating FCFX application for prNo : " + prNo);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        try{
            ResponseModel<?> res = fcfxService.createAppInCitizen(prNo, userId, token);
            return ResponseEntity.ok(res);
        } catch(IllegalArgumentException ex){
            log.error("Exception While creating application...: " + ex.getMessage());
            ResponseModel<?> res = new ResponseModel<>();
            res.setStatus(ResponseModel.FAILED);
            res.setStatusCode(HttpStatus.FORBIDDEN.value());
            res.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        } catch (VehicleRcNotFoundException e) {
            log.error("Exception While creating application...: " + e.getMessage());
            ResponseModel<?> res = new ResponseModel<>();
            res.setStatus(ResponseModel.FAILED);
            res.setStatusCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
        } catch (FitnessNotFoundException e) {
            log.error("Exception While creating application...: " + e.getMessage());
            ResponseModel<?> res = new ResponseModel<>();
            res.setStatus(ResponseModel.FAILED);
            res.setStatusCode(HttpStatus.EXPECTATION_FAILED.value());
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
        }catch (FitnessExpiredException e) {
            log.error("Exception While creating application...: " + e.getMessage());
            ResponseModel<?> res = new ResponseModel<>();
            res.setStatus(ResponseModel.FAILED);
            res.setStatusCode(ServiceValidation.FITNESS_EXPIRED.getCode());
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/fcfx", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> save(@RequestBody CFXModel cfxModel, @RequestHeader("Authorization") String token) {
        try {
            fcfxService.saveCFXNoteDetails(cfxModel, null);
        } catch (UserNotFoundException e) {
            SaveUpdateResponse res = new SaveUpdateResponse();
            res.setStatus(ResponseModel.FAILED);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        } catch (VehicleRcNotFoundException e) {
            SaveUpdateResponse res = new SaveUpdateResponse();
            res.setStatus(ResponseModel.FAILED);
            res.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
        SaveUpdateResponse res = new SaveUpdateResponse();
        res.setStatus(ResponseModel.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/fcfx/pr/{pr_number}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> get(@PathVariable("pr_number") String prNumber, @RequestHeader("Authorization") String token) {
        CFXNoticeModel response = null;
        try {
            response = fcfxService.getCFXNoteDetails(prNumber);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (VehicleRcNotFoundException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
