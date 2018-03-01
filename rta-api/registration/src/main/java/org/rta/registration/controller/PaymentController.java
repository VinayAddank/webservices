package org.rta.registration.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.payment.tax.ApplicationTaxModel;
import org.rta.core.model.payment.tax.TaxModel;
import org.rta.core.model.user.DealerModel;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.core.service.hsrp.HSRPTransactionService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.service.payment.invoice.DealerInvoiceService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.service.user.DealerService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.PaymentService;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.registration.service.vcr.VcrService;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class PaymentController {
    private static final Logger log = Logger.getLogger(PaymentController.class);
    @Value("${jwt.header}")
    private String tokenHeader;
    @Value("${jwt.claim.secret}")
    private String claimSecret;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private TaxDetailService taxDetailService;
    @Autowired
    private DealerInvoiceService dealerInvoiceService;
    @Autowired
    private TransactionDetailService transactionDetailService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    CommunicationService communicationService;
    @Autowired
    private HSRPDetailService hsrpDetailService;
    @Autowired
    private DealerService dealerService;
    @Autowired
    private HSRPTransactionService hsrpTransactionService;
    @Autowired
    private VcrService vcrService;
    @Autowired
	private VehicleDAO vehicleDAO;
    
    @Autowired
    private CmsLiveSyncingService cmsLiveSyncingService;

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/payfee/{regtype}/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> payFee(HttpServletRequest request, @PathVariable("regtype") int regType,
            @PathVariable("vehiclercid") String vehicleRcIdEnc) {
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("::::paytax :: Invalid VehicleRcId ::::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (userName == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try{
            if (!transactionDetailService.isAppActive(vehicleRcId)) {
                log.error("saving Transaction Details :: The application is not active");
                errorModel.setMessage("The application is not active !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        try {
            transactionDetailService.checkTRSeriesStatus(vehicleRcId);
        } catch (NotFoundException e1) {
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put("trGenerated", Boolean.FALSE);
            errorModel.setMessage(e1.getMessage());
            errorModel.setAttributes(attr);
            return ResponseEntity.status(HttpStatus.OK).body(errorModel);
        }
        TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
        try {
            DealerModel dealerModel = dealerService.findByUserIdForPay(userId);
            if (!dealerModel.getEligibleToPay()) {
                log.error("::::payfee:::getEligibleToPay():::::::::");
                errorModel.setMessage("Not Authorized for payment !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            
            if(transactionDetailService.checkPaymentDone(vehicleRcId , PaymentType.PAYTAX.getId())){
            	log.error("::Payment has been already done :: ");
                return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
            }
            
            DealerInvoiceEntity dealerInvoiceEntity = dealerInvoiceService.createInvoice(vehicleRcId, userName);
            String challanNo = transactionDetailService.getChallanNumberForFreshRegist(vehicleRcId,PaymentType.PAYTAX.getId());
            transactionDetailModel = transactionDetailService.createPaymentRequest(dealerInvoiceEntity, userName , challanNo,PaymentGatewayType.SBI);
            HSRPTransactionModel hsrpTransactionModel =
                    hsrpTransactionService.createHSRPRequest(dealerInvoiceEntity, userName);
            transactionDetailModel.setVehicleRcId(vehicleRcIdEnc);
            transactionDetailModel =
                    paymentService.encryptedSBIParameter(transactionDetailModel, hsrpTransactionModel);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Exception while saving Transaction Details :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailModel);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/gatewayresponse", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> gatewayresponse(HttpServletRequest request,
            @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
        log.debug("::::::::gatewayresponse::::::::::");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted =
                RtaCryptoUtil.parseSecureToken(transactionDetailModel.getVehicleRcId(), claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("gatewayresponse :: Invalid VehicleRcId ::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            if (!transactionDetailService.isAppActive(vehicleRcId)) {
                log.error("saving Transaction Details :: The application is not active");
                errorModel.setMessage("The application is not active !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        try {        	
            transactionDetailModel = paymentService.decryptSBIResponse(transactionDetailModel);
            log.debug("response status " + transactionDetailModel.getStatus());
        	if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                saveUpdateResponse =
                        transactionDetailService.gatewayResponse(transactionDetailModel, userName, vehicleRcId, false,
                                userType);
                log.debug(":::::::SBI HSRP STATUS:::::::::::::" + saveUpdateResponse.getStatus());
                if (saveUpdateResponse.getStatus().equalsIgnoreCase(SaveUpdateResponse.SUCCESS)) {
                    Map<String,Object> attr = saveUpdateResponse.getAttributes();
                    if (ObjectsUtil.isNotNull(attr) && (ObjectsUtil.isNotNull(attr.get("trGenerated")) ? (Boolean) attr.get("trGenerated") : Boolean.TRUE)) {
                        try {
                            hsrpDetailService.saveHSRPDetail(vehicleRcId, userName);
                        } catch (Exception e1) {
                            log.error("::::::::::Internal Server Error RTA:::::::::::");
                            errorModel.setMessage(e1.getMessage());
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
                        }
                    } else {
                        log.error("tr not generated");
                    }
                }
                saveUpdateResponse.setVehicleRcId(transactionDetailModel.getVehicleRcId());
            } else {
                saveUpdateResponse.setStatus("FAILURE");
                saveUpdateResponse.setMessage("interrupted checksum data");
            }
            
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        		e.printStackTrace();
            	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
                errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Exception while saving gatewayresponse Transaction Details :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/paysecondvehicletax/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> paySecondVehicleTax(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("paysecondvehicletax :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (userName == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
        try {
            int status[] = transactionDetailService.isSBIVerificationForSecondVehicle(vehicleRcId);
            DealerInvoiceEntity dealerInvoiceEntity =
                    dealerInvoiceService.secondVehicleTaxInvoice(vehicleRcId, userName);
            String challanNo = transactionDetailService.getChallanNumberForFreshRegist(vehicleRcId,PaymentType.SECONDVEHICLETAXPAY.getId());
            transactionDetailModel =
                    transactionDetailService.secondVehiclePaymentRequest(dealerInvoiceEntity, userName,challanNo,PaymentGatewayType.SBI);
            transactionDetailModel.setVehicleRcId(vehicleRcIdEnc);
            transactionDetailModel = paymentService.secondVehicleEncryptedRequest(transactionDetailModel);
            transactionDetailModel.setSbiVerifyStatus(status[0]);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
        	e.printStackTrace();
            log.error("Exception while saving paysecondvehicletax Transaction Details :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailModel);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/secondvehiclegateresp", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> secondVehicleGatewayResponse(HttpServletRequest request,
            @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
        log.debug("::::::::secondVehicleGatewayResponse::::::::::");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted =
                RtaCryptoUtil.parseSecureToken(transactionDetailModel.getVehicleRcId(), claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("secondVehicleGatewayResponse :: Invalid VehicleRcId :::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            transactionDetailModel = paymentService.decryptSecondVehicleSBIResponse(transactionDetailModel);
            log.debug("response status " + transactionDetailModel.getStatus());
            if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                saveUpdateResponse =
                        transactionDetailService.gatewayResponse(transactionDetailModel, userName, vehicleRcId, true,
                                userType);
                saveUpdateResponse.setVehicleRcId(transactionDetailModel.getVehicleRcId());
            } else {
                saveUpdateResponse.setStatus("FAILURE");
                saveUpdateResponse.setMessage("interrupted checksum data");
            }
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception while saving secondVehicleGatewayResponse  :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/payverification/{regtype}/{vehiclercid}/{paytype}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> payVerification(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc, @PathVariable("paytype") Integer paytype,
            @PathVariable("regtype") int regType) {
        log.debug("::::::::payVerification:::::" + paytype);
        SaveUpdateResponse errorModel = new SaveUpdateResponse();
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("::::payVerification :: Invalid VehicleRcId ::::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (userName == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(transactionDetailService.checkPaymentDone(vehicleRcId , paytype)){
        	log.error("::Second vehicle Payment has been already done :: ");
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).build();
        }
        
        try{
            if (!transactionDetailService.isAppActive(vehicleRcId)) {
                log.error("Exception while saving Transaction Details :: The application is not active");
                errorModel.setMessage("The application is not active !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        // STOP payment if TR Series unavailable
        try {
            transactionDetailService.checkTRSeriesStatus(vehicleRcId);
        } catch (NotFoundException e1) {
            Map<String, Object> attr = new HashMap<String, Object>();
            attr.put("trGenerated", Boolean.FALSE);
            errorModel.setMessage(e1.getMessage());
            errorModel.setAttributes(attr);
            // HTTP status is 200 due to front end issue
            return ResponseEntity.status(HttpStatus.OK).body(errorModel);
        }
        TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
        try {
            DealerModel dealerModel = dealerService.findByUserIdForPay(userId);
            if (!dealerModel.getEligibleToPay()) {
                log.error("::::Not Authorized to pay !!!::::::::");
                errorModel.setMessage("Not Authorized to pay !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            transactionDetailModel =
                    transactionDetailService.paymentVerificationReq(vehicleRcId, userName, paytype,PaymentGatewayType.SBI);
            if (ObjectsUtil.isNull(transactionDetailModel)) {
                TransactionDetailModel transactionDetail = new TransactionDetailModel();
                transactionDetail.setStatus(SaveUpdateResponse.FAILURE);
                transactionDetail.setMessage("Last_Transaction_Failed");
                transactionDetail.setVehicleRcId(vehicleRcIdDecrypted);
                return ResponseEntity.status(HttpStatus.OK).body(transactionDetail);
            }
            transactionDetailModel = paymentService.payVerification(vehicleRcId, transactionDetailModel, paytype);
            transactionDetailModel.setVehicleRcId(vehicleRcIdDecrypted);
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception while saving Transaction Details :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailModel);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/verificationgatewayresp", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> verificationGatewayResp(HttpServletRequest request,
            @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
        log.debug("::::::::verificationGatewayResp:::::::::: " + transactionDetailModel.getPayType());
        String vehicleRcIdDecrypted =
                RtaCryptoUtil.parseSecureToken(transactionDetailModel.getVehicleRcId(), claimSecret);
        Long vehicleRcId = null;
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("verificationGatewayResp :: Invalid VehicleRcId ::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            transactionDetailModel = paymentService.decryptVerificationSBIResponse(transactionDetailModel);
            log.debug("response status " + transactionDetailModel.getStatus());
            if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                if (transactionDetailModel.getPayType() == PaymentType.PAYTAX.getId())
                    saveUpdateResponse = transactionDetailService.gatewayResponse(transactionDetailModel, userName,
                            vehicleRcId, false, userType);
                if (transactionDetailModel.getPayType() == PaymentType.SECONDVEHICLETAXPAY.getId())
                    saveUpdateResponse = transactionDetailService.gatewayResponse(transactionDetailModel, userName,
                            vehicleRcId, true, userType);
                log.debug(":::::::SBI HSRP STATUS:::::::::::::" + saveUpdateResponse.getStatus());
                if (saveUpdateResponse.getStatus().equalsIgnoreCase(SaveUpdateResponse.SUCCESS)) {
                    Map<String,Object> attr = saveUpdateResponse.getAttributes();
                    if (ObjectsUtil.isNotNull(attr) && (ObjectsUtil.isNotNull(attr.get("trGenerated")) ? (Boolean) attr.get("trGenerated") : Boolean.TRUE)) {
                        try {
                            hsrpDetailService.saveHSRPDetail(vehicleRcId, userName);
                        } catch (Exception e1) {
                            log.error("::::::::::Internal Server Error RTA:::::::::::" + e1.getMessage());
                            log.debug(e1);
                            errorModel.setMessage(e1.getMessage());
                            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
                        }
                    }
                }
                saveUpdateResponse.setVehicleRcId(transactionDetailModel.getVehicleRcId());
            } else {
                saveUpdateResponse.setStatus("FAILURE");
                saveUpdateResponse.setMessage("interrupted checksum data");
            }
        } catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	log.error("OPTIMISTIC ERROR :: " + e.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception while saving gatewayresponse Transaction Details :: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }



    @RequestMapping(path = "/adminsbiverification", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> payVerification(HttpServletRequest request,
            @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
        log.info(":::::::::::adminsbiverification::::::::::::");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        try {
            transactionDetailModel = paymentService.adminSBIVerification(transactionDetailModel);
        } catch (Exception e) {
            log.error("Exception : " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionDetailModel);
    }

    @RequestMapping(path = "/adminsbiverificationgatewayresp", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> adminSBIverificationgatewayresp(HttpServletRequest request,
            @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
        log.debug("::::::::verificationGatewayResp:::::::::: " + transactionDetailModel.getPayType());
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            transactionDetailModel = paymentService.adminDecryptVerificationSBI(transactionDetailModel);
            log.debug("response status " + transactionDetailModel.getStatus());
            if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")) {
                saveUpdateResponse = new SaveUpdateResponse(
                        transactionDetailModel.getSbiResponseMap().get("Status") + "|"
                                + transactionDetailModel.getSbiResponseMap().get("sbi_ref_no"),
                        transactionDetailModel.getSbiResponseMap().get("Status_desc"), null, null);
            } else {
                saveUpdateResponse.setStatus("FAILURE");
                saveUpdateResponse.setMessage("interrupted checksum data");
            }
        } catch (Exception e) {
            log.error("Exception while saving gatewayresponse Transaction Details :: " + e.getMessage());
            log.debug(e);
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        if (ObjectsUtil.isNull(transactionDetailModel)) {
            saveUpdateResponse.setStatus(SaveUpdateResponse.FAILURE);
            saveUpdateResponse.setMessage("Not Found !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(saveUpdateResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }


    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/verifypendingTxn/{regtype}/{vehiclercid}/{paytype}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> verifyPendingTxn(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc, @PathVariable("paytype") Integer paytype,
            @PathVariable("regtype") int regType1) {
        log.info("::::::::verifyPendingTxn::::::::" + paytype);
        SaveUpdateResponse errorModel = new SaveUpdateResponse();
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("::::verifyPendingTxn :: Invalid VehicleRcId ::::::" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
        Long userId = jwtTokenUtil.getUserIdFromToken(token);
        if (userName == null || userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try{
            if (!transactionDetailService.isAppActive(vehicleRcId)) {
                log.error("Exception while saving Transaction Details verifyPendingTxn :: The application is not active");
                errorModel.setMessage("The application is not active !");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
        try {
            saveUpdateResponse = paymentService.verifyPendingTxn(vehicleRcId, paytype, userName, userType);
            saveUpdateResponse.setVehicleRcId(vehicleRcIdDecrypted);
        } catch (Exception e) {
            log.error("Exception while saving Transaction Details verifyPendingTxn:: " + e.getMessage());
            log.debug(e);
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(saveUpdateResponse)) {
            errorModel.setMessage("Not Found !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
    }

    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN','ROLE_ONLINE_FINANCER')")
    @RequestMapping(value = "/alltaxdetails/{trorprnumber}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllTaxDetails(@RequestHeader("Authorization") String token, @PathVariable("trorprnumber") String trOrPrNumber ) {
    	log.info(":::::getAllTaxDetails::::start::::");
        ApplicationTaxModel applicationTaxModel = null;
        try {
			applicationTaxModel = taxDetailService.getAllTaxDetails(trOrPrNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (ObjectsUtil.isNull(applicationTaxModel)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info(":::::getAllTaxDetails::::end::::");
        return ResponseEntity.ok(applicationTaxModel);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/challanno", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getChallanNo(@RequestHeader("Authorization") String token) {
    	log.info(":::::getChallanNo::::::::");
        String challanNo;
        challanNo = transactionDetailService.getChallanNumber();
        return ResponseEntity.ok(challanNo);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/paymentparameter", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPaymentParameter(@RequestHeader("Authorization") String token, @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
    	log.info(":::::getPaymentParameter::::start::::");
    	try {
    		transactionDetailModel.setCitizenFlag(true);
			transactionDetailModel = paymentService.getPaymentParameter(transactionDetailModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
        log.info(":::::getPaymentParameter::::end::::");
        return ResponseEntity.ok(transactionDetailModel);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/paymentverifyparameter", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPaymentVerifyParameter(@RequestHeader("Authorization") String token, @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
    	log.info(":::::getPaymentVerifyParameter::::start::::");
    	transactionDetailModel.setCitizenFlag(true);
    	transactionDetailModel = paymentService.getVerificationEncrytData(transactionDetailModel);
        log.info("::::getPaymentVerifyParameter::::end::::");
        return ResponseEntity.ok(transactionDetailModel);
    }
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/payment/decryptVerification", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPaymentDecryptVerification(@RequestHeader("Authorization") String token, @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
    	log.info(":::::getPaymentDecryptVerification::::start::::");
    	transactionDetailModel.setCitizenFlag(true);
    	transactionDetailModel = paymentService.decryptPaymentResponse(transactionDetailModel);
        log.info("::::getPaymentDecryptVerification::::end::::");
        return ResponseEntity.ok(transactionDetailModel);
    }
    
    
    @PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/sync/paytax", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> syncPayTaxData(@RequestHeader("Authorization") String token, @RequestBody TaxModel taxModel) {
    	log.info("::registration:::syncPayTaxData::::start::::");
    	SaveUpdateResponse saveUpdateResponse = taxDetailService.syncPayTaxData(taxModel);
    	/*if(saveUpdateResponse.getStatus().equals("SUCCESS")){
    	vcrService.updateVcrDetails(taxModel.getPrNumber(), taxModel.getTransactionNo(),taxModel.getChallanNo());
    	}
    	log.info("::registration:::syncPayTaxData:::end:::: " + saveUpdateResponse.getStatus());*/
    	try{
    		String result = cmsLiveSyncingService.cmsLiveSyncingService(null ,taxModel.getPrNumber(), ServiceType.PAY_TAX.getCode());
        	log.info("::: Syncing data with CMS status :::: " + result);
    	}catch (Exception e) {
    		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
		}
    	
        return ResponseEntity.ok(saveUpdateResponse);
    }
    
	@RequestMapping(value = "/tax/details/vehiclerc/{vehicleRcId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> syncPayTaxData(@RequestHeader("Authorization") String token,
			@PathVariable("vehicleRcId") Long vehicleRcId) {
		return ResponseEntity.ok(taxDetailService.getCurrentTaxDetails(vehicleRcId));
	}
        
}
