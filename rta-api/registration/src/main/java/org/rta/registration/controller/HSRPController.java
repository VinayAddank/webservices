package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPDetailModel;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.scheduler.HSRPSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HSRPController {
    private static final Logger log = Logger.getLogger(PaymentController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.claim.secret}")
    private String claimSecret;
    
    @Autowired
	private HSRPSchedulerService hsrpSchedulerService;

    @Autowired
    private HSRPDetailService HSRPDetailService;


    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(path = "/hsrp/notify/payment/", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> confirmPaymentOfHSRP(HttpServletRequest request,
            @Valid @RequestBody HSRPDetailModel hsrpDetailModel) {
        log.info("::::::::confirmPaymentOfHSRP::::::::::");
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        if (ObjectsUtil.isNull(hsrpDetailModel)) {
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("HSRPDetail Required !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        try {
            saveUpdateResponse = HSRPDetailService.confirmPaymentOfHSRP(hsrpDetailModel);
        } catch(IllegalArgumentException ie){
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        } catch (Exception e) {
            log.error("Exception while saving confirmPaymentOfHSRP HSRPTransaction Details :: " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        if (ObjectsUtil.isNull(saveUpdateResponse)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }

    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(path = "/hsrp/provide/laser/", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updateHSRPLaserCodes(HttpServletRequest request,
            @Valid @RequestBody HSRPDetailModel hsrpDetailModel) {
        log.info("::::::::updateHSRPLaserCodes::::::::::");
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            saveUpdateResponse = HSRPDetailService.updateHSRPLaserCodes(hsrpDetailModel);
        } catch (IllegalArgumentException ie) {
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        } catch (Exception e) {
            log.error("Exception while saving updateHSRPLaserCodes HSRPTransaction Details :: " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        if (ObjectsUtil.isNull(saveUpdateResponse)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }


    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(path = "/hsrp/notify/affixation", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> notifyAffixation(HttpServletRequest request,
            @Valid @RequestBody HSRPDetailModel hsrpDetailModel) {
        log.info("::::::::notifyAffixation::::::::::");
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            saveUpdateResponse = HSRPDetailService.notifyAffixation(hsrpDetailModel);
        } catch (Exception e) {
            log.error("Exception while saving notifyAffixation HSRPTransaction Details :: " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        if (ObjectsUtil.isNull(saveUpdateResponse)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }

    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(path = "/hsrp/confirm/affixation", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> confirmAffixation(HttpServletRequest request,
            @Valid @RequestBody HSRPDetailModel hsrpDetailModel) {
        log.info("::::::::confirmAffixation::::::::::");
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            saveUpdateResponse = HSRPDetailService.confirmAffixation(hsrpDetailModel);
        } catch (Exception e) {
            log.error("Exception while saving confirmAffixation HSRPTransaction Details :: " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        if (ObjectsUtil.isNull(saveUpdateResponse)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }


    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(value = "/posttrhsrp", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void postTRDataToHSRP(HttpServletRequest request,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        log.info(":::::postTRDataToHSRP::::::::");
        hsrpSchedulerService.postTRDataToHSRP(from , to);
    }
    
    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(value = "/postprhsrp", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void postPRDataToHSRP(HttpServletRequest request,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        log.info(":::::postPRDataToHSRP::::::::");
        hsrpSchedulerService.postPRDataToHSRP(from , to);
    }
    
    
    @PreAuthorize("hasRole('ROLE_HSRP')")
    @RequestMapping(value = "/updatehsrpdetailtable", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public void updateHSRPDetailTable(HttpServletRequest request,
            @RequestParam(name = "from", required = false) Long from,
            @RequestParam(name = "to", required = false) Long to) {
        log.info(":::updateHSRPDetailTable::start:: from" + from + " to " + to);
        hsrpSchedulerService.updateHSRPDetailTable(from , to);
        log.debug(":::::updateHSRPDetailTable:::end:::::");
    }

}
