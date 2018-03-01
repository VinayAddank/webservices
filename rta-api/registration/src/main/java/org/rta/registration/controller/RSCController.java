/**
 * 
 */
package org.rta.registration.controller;

import org.apache.log4j.Logger;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.rsc.RSCService;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arun.verma
 *
 */

@RestController
public class RSCController {

	private static final Logger log = Logger.getLogger(RSCController.class);

    @Autowired
    private RSCService rSCService;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    /**
     * Create application In citizen with pr number and aadhar number of cco
     * 
     * @param pr_number
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_CCO')")
    @RequestMapping(value = "/rsc/app/prno/{pr_number}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> createAppInCitizen(@PathVariable("pr_number") String prNo, @RequestHeader("Authorization") String token) {
        log.info("Creating RSC application for prNo : " + prNo);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        try{
            ResponseModel<?> res = rSCService.createAppInCitizen(prNo, userId, token);
            return ResponseEntity.ok(res);
        } catch(IllegalArgumentException ex){
            log.error("Exception While creating application...: " + ex.getMessage());
            ResponseModel<?> res = new ResponseModel<>();
            res.setStatus(ResponseModel.FAILED);
            res.setStatusCode(HttpStatus.FORBIDDEN.value());
            res.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(res);
        }
    }
}
