package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.payment.TaxFeeDetailModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.taxfee.TaxFeeService;
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
public class TaxFeeController {
	
	private static final Logger log = Logger.getLogger(TaxFeeController.class);
    
	@Value("${jwt.header}")
    private String tokenHeader;
    
	@Value("${jwt.claim.secret}")
    private String claimSecret;
    
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
    
	@Autowired
    CommunicationService communicationService;
    
	@Autowired
    private TaxFeeService taxFeeService;
    
	@Autowired
    private TaxDetailService taxDetailService;
    
	@Autowired
    private TransactionDetailService transactionDetailService;
    
    
    @RequestMapping(path = "/taxregfeecal/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> taxRegFeeCal(HttpServletRequest request,@PathVariable("vehiclercid") String vehicleRcIdEnc) {
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
            log.debug("::taxRegFeeCal:: " + vehicleRcId);
        } catch (NumberFormatException ex) {
            log.error("taxRegFeeCal :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (userName == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        TaxFeeDetailModel taxFeeDetailModel = new TaxFeeDetailModel();
        try{
        taxFeeDetailModel = taxFeeService.getTaxFee(vehicleRcId, userName);
        } catch (Exception e) {
            log.error("Exception while saving Tax and RegFee Details :: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        }
        if (ObjectsUtil.isNull(taxFeeDetailModel)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(taxFeeDetailModel);

    }


    
    @RequestMapping(path = "/taxregfeecal/{regtype}/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> taxRegFeeView(HttpServletRequest request,@PathVariable("regtype") int regType,
            @PathVariable("vehiclercid") String vehicleRcIdEnc ) {
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
            log.debug(":::taxRegFeeView:: " + vehicleRcId);
        } catch (NumberFormatException ex) {
            log.error("taxRegFeeView :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (userName == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        TaxFeeDetailModel taxFeeDetailModel = new TaxFeeDetailModel();
        try{
        taxFeeDetailModel = taxFeeService.viewTaxFee(vehicleRcId, userName, token);
        } catch (Exception e) {
            log.error("Exception while View Tax and RegFee Details :: " + e.getMessage());
            e.printStackTrace();
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        }
        if (ObjectsUtil.isNull(taxFeeDetailModel)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(taxFeeDetailModel);

    }

    
    @PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_DEALER')")
    @RequestMapping(path = "/calSecondvehicletax/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> calSecondVehicleTax(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
            log.debug("::::calSecondVehicleTax:::: " + vehicleRcId);
        } catch (NumberFormatException ex) {
            log.error("calSecondVehicleTax :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (userName == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TaxDetailModel taxDetailModel = new TaxDetailModel();
        try {
            int status[] = transactionDetailService.isSBIVerificationForSecondVehicle(vehicleRcId);
            taxDetailModel = taxDetailService.secondVehicleTaxCal(vehicleRcId, userName);
            taxDetailModel.setVehicleRcId(vehicleRcIdEnc);
            taxDetailModel.setSbiVerifyStatus(status[0]);
            taxDetailModel.setStatus(status[0]);
            if(status.length>1) {
            	if(status[1]==1) {
            		 taxDetailModel.setIsSBIVerification(true);
            	}else if(status[1]==2){
            		taxDetailModel.setIsPayUVerification(true);
            	}
            }
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Exception while saving calSecondVehicleTax :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(taxDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(taxDetailModel);
    }

    @RequestMapping(path = "/getLifeTaxDetails/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getLifeTax(@PathVariable("vehiclercid") String vehicleRcId) {
		Boolean isLifeTaxPaid = taxDetailService.getLifeTax(Long.parseLong(vehicleRcId));
		return ResponseEntity.ok(isLifeTaxPaid);
	}
}
