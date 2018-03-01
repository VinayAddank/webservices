package org.rta.registration.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.StaleObjectStateException;
import org.rta.core.entity.payment.invoice.DealerInvoiceEntity;
import org.rta.core.enums.PaymentGatewayType;
import org.rta.core.enums.PaymentType;
import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.hsrp.HSRPTransactionModel;
import org.rta.core.model.payment.gateway.PayUResponse;
import org.rta.core.model.payment.gateway.PayUTransactionDetails;
import org.rta.core.model.payment.gateway.PayUVerifyResponseTransaction;
import org.rta.core.model.payment.gateway.TransactionDetailModel;
import org.rta.core.model.user.DealerModel;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.core.service.hsrp.HSRPTransactionService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.service.payment.invoice.DealerInvoiceService;
import org.rta.core.service.user.DealerService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.PaymentService;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class PayUPaymentController {

	private static final Log log = LogFactory.getLog(PayUPaymentController.class);

	@Value("${jwt.header}")
	private String tokenHeader;
	@Value("${jwt.claim.secret}")
	private String claimSecret;
	@Value("${payment.payu.authorization}")
	private String payUAuthroization;
	@Value("${payment.payu.verifyurl}")
	private String payUVerifyUrl;
	@Value("${payment.payu.verify.status.success}")
	private String payUVerifyStatusSuccess;
	@Value("${citizen.payment.payu.authorization}")
	private String payUAuthroization4citizen;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
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
	private RestTemplate restTemplate;

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(path = "/payfee/payu/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> payFee(HttpServletRequest request, @PathVariable("vehiclercid") String vehicleRcIdEnc) {
		log.debug(":::PAYU:::::payfee::::::::::");
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
		Long vehicleRcId = null;
		try {
			vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException nfe) {
			log.error("::::PAYU payfee :: Invalid VehicleRcId ::::::" + vehicleRcIdDecrypted, nfe);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		String token = request.getHeader(tokenHeader);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		if (userName == null || userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
		try {
			DealerModel dealerModel = dealerService.findByUserIdForPay(userId);
			if (!dealerModel.getEligibleToPay()) {
				log.debug("::payu::payfee:::getEligibleToPay():::::::::");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			DealerInvoiceEntity dealerInvoiceEntity = dealerInvoiceService.createInvoice(vehicleRcId, userName);
			String challanNo = transactionDetailService.getChallanNumberForFreshRegist(vehicleRcId,PaymentType.PAYTAX.getId());
			transactionDetailModel = transactionDetailService.createPaymentRequest(dealerInvoiceEntity, userName,
					challanNo, PaymentGatewayType.PAYU);
			HSRPTransactionModel hsrpTransactionModel = hsrpTransactionService.createHSRPRequest(dealerInvoiceEntity,
					userName);
			transactionDetailModel.setVehicleRcId(vehicleRcIdEnc);
			transactionDetailModel.setPgType(PaymentGatewayType.PAYU.getLabel());
			transactionDetailModel = paymentService.encryptedPayUParameter(transactionDetailModel, hsrpTransactionModel,
					dealerModel);

		} catch (Exception exc) {
			log.error("Exception while saving Transaction Details :: ", exc);
			throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
		}
		if (ObjectsUtil.isNull(transactionDetailModel)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(transactionDetailModel);
	}

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(path = "/gatewayresponse/payu", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> gatewayresponse(HttpServletRequest request,
			@Valid @RequestBody TransactionDetailModel transactionDetailModel) {
		log.info("::::::::gatewayresponse PayU::::::::::");
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(transactionDetailModel.getVehicleRcId(),
				claimSecret);
		Long vehicleRcId = null;
		try {
			vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException nfe) {
			log.error("gatewayresponse PayU :: Invalid VehicleRcId ::::" + vehicleRcIdDecrypted, nfe);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		String token = request.getHeader(tokenHeader);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		if (userName == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return doPayuResponseProcess(vehicleRcId, userName, transactionDetailModel, userType,PaymentType.PAYTAX.getId());
	}

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(path = "/payverification/payu/{vehiclercid}/{paytype}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> payVerification(HttpServletRequest request,
			@PathVariable("vehiclercid") String vehicleRcIdEnc, @PathVariable("paytype") Integer paytype) {

		log.info("::::::::payVerification::::::::::" + paytype);
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
		Long vehicleRcId = null;
		try {
			vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException nfe) {
			log.error("::::payVerification :: Invalid VehicleRcId ::::::" + vehicleRcIdDecrypted, nfe);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		String token = request.getHeader(tokenHeader);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		if (userName == null || userId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		TransactionDetailModel transactionDetailModel = new TransactionDetailModel();
		try {
			DealerModel dealerModel = dealerService.findByUserIdForPay(userId);
			if (!dealerModel.getEligibleToPay()) {
				log.info("::::paysecondvehicletax:::getEligibleToPay():::::::::");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			transactionDetailModel = transactionDetailService.paymentVerificationReq(vehicleRcId, userName, paytype,
					PaymentGatewayType.PAYU);
			if (ObjectsUtil.isNull(transactionDetailModel)) {
				TransactionDetailModel transactionDetail = new TransactionDetailModel();
				transactionDetail.setStatus(SaveUpdateResponse.FAILURE);
				transactionDetail.setMessage("Last_Transaction_Failed");
				transactionDetail.setVehicleRcId(vehicleRcIdDecrypted);
				return ResponseEntity.status(HttpStatus.OK).body(transactionDetail);
			}
			//transactionDetailModel = paymentService.payUVerificationReq(vehicleRcId, transactionDetailModel, paytype);
			transactionDetailModel.setVehicleRcId(vehicleRcIdDecrypted);

			Optional<PayUResponse> payUResponseOptional = doPayUVerifyProcess(transactionDetailModel);

			if (!isPayUResponseValid(payUResponseOptional, transactionDetailModel.getTransactionNo())) {

				log.info("PayU verification response validation is failed.");

				TransactionDetailModel transactionDetail = new TransactionDetailModel();
				transactionDetail.setStatus(SaveUpdateResponse.FAILURE);
				transactionDetail.setMessage("Last_Transaction_Failed");
				transactionDetail.setVehicleRcId(vehicleRcIdDecrypted);
				
				//status update in case of failure
				transactionDetailService.updatePayUFaliureResponse(transactionDetailModel.getTransactionNo(), vehicleRcId, userName,SaveUpdateResponse.FAILURE);
				
				return ResponseEntity.status(HttpStatus.OK).body(transactionDetail);
			}

			PayUTransactionDetails payUTransactionDetails = payUResponseOptional.get().getResult().get(0)
					.getPostBackParam();
			log.info(payUTransactionDetails);
			log.info("" + payUTransactionDetails.getStatus() + "," + payUTransactionDetails.getField9() + ","
					+ payUTransactionDetails.getError_Message());

			transactionDetailModel.setPayUTransactionDetails(payUTransactionDetails);
			transactionDetailModel.setPayType(paytype);
			return doPayuResponseProcess(vehicleRcId, userName, transactionDetailModel, userType,paytype);

		} catch (Exception exc) {
			log.error("Exception while saving Transaction Details :: ", exc);
			throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
		}
	}

	/***
	 * @author ankur.goyal
	 * @param request
	 * @param vehicleRcIdEnc
	 * @description PayU second vehicle payment
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(path = "/payu/paysecondvehicletax/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
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
			DealerModel dealerModel = dealerService.findByUserIdForPay(userId);
			if (!dealerModel.getEligibleToPay()) {
				log.info("::::payfee:::getEligibleToPay():::::::::");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			int status[] = transactionDetailService.isSBIVerificationForSecondVehicle(vehicleRcId);
			DealerInvoiceEntity dealerInvoiceEntity = dealerInvoiceService.secondVehicleTaxInvoice(vehicleRcId,
					userName);
			String challanNo = transactionDetailService.getChallanNumber();
			transactionDetailModel = transactionDetailService.secondVehiclePaymentRequest(dealerInvoiceEntity, userName,
					challanNo,PaymentGatewayType.PAYU);
			transactionDetailModel.setVehicleRcId(vehicleRcIdEnc);
			transactionDetailModel = paymentService.payuSecondVehicleEncryptedRequest(transactionDetailModel,dealerModel);
			transactionDetailModel.setSbiVerifyStatus(status[0]);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			log.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (IllegalArgumentException ie) {
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception while saving payu paysecondvehicletax Transaction Details :: " + e.getMessage());
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
	@RequestMapping(path = "/secondvehiclegateresp/payu", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> secondVehicleGatewayResponse(HttpServletRequest request,
			@Valid @RequestBody TransactionDetailModel transactionDetailModel) {
		log.debug("::::::::secondVehicleGatewayResponse::::::::::");
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(transactionDetailModel.getVehicleRcId(),
				claimSecret);
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
		if (userName == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return doPayuResponseProcess(vehicleRcId, userName, transactionDetailModel, userType,PaymentType.SECONDVEHICLETAXPAY.getId());
	}

	private boolean isPayUResponseValid(Optional<PayUResponse> payUResponseOptional, String transactionNo) {
		if (payUResponseOptional.isPresent()) {
			String status = payUResponseOptional.get().getStatus();
			log.info("=====response payU:" + status + "," + payUResponseOptional.get().getMessage());
			if (status.equalsIgnoreCase(payUVerifyStatusSuccess)) {
				List<PayUVerifyResponseTransaction> result = payUResponseOptional.get().getResult();
				if (result == null || result.isEmpty()) {
					return false;
				}
				PayUVerifyResponseTransaction pvrt = result.get(0);
				return transactionNo.equals(pvrt.getMerchantTransactionId());
			}
		}
		return false;
	}

	private ResponseEntity<?> doPayuResponseProcess(Long vehicleRcId, String userName,
			TransactionDetailModel transactionDetailModel, UserType userType,int paymentType) {

		SaveUpdateResponse saveUpdateResponse = new SaveUpdateResponse();
		try {
			transactionDetailModel = paymentService.decryptPayUResponse(transactionDetailModel);
			log.info("response status PayU " + transactionDetailModel.getStatus());

			// Checking the payu response checksum
			
			if (!paymentService.checkResponsePayUHash(transactionDetailModel)) {
				log.error("PayU response checksum validation is  failed for transactionNo:"
						+ transactionDetailModel.getTransactionNo());
				throw new IllegalArgumentException("PayU response checksum validation is failed.");
			}

			// Checking the PayU response status with success, pending and
			// failure
			if (transactionDetailModel.getStatus().equalsIgnoreCase("SUCCESS")
					|| transactionDetailModel.getStatus().equalsIgnoreCase("PENDING")
					|| transactionDetailModel.getStatus().equalsIgnoreCase("FAILURE")) {
				if(paymentType == PaymentType.PAYTAX.getId()) {
					saveUpdateResponse = transactionDetailService.gatewayPayUResponse(transactionDetailModel, userName,
							vehicleRcId, false, userType);
				}else if(paymentType == PaymentType.SECONDVEHICLETAXPAY.getId()) {
					saveUpdateResponse = transactionDetailService.gatewayPayUResponse(transactionDetailModel, userName,
							vehicleRcId, true, userType);
				}
				if (saveUpdateResponse.getStatus().equalsIgnoreCase(SaveUpdateResponse.SUCCESS)) {
					try {
						hsrpDetailService.saveHSRPDetail(vehicleRcId, userName);
					} catch (Exception exc) {
						log.error("::::::::::Internal Server Error RTA:::::::::::", exc);
					}
				}
				saveUpdateResponse.setVehicleRcId(transactionDetailModel.getVehicleRcId());
			} else {// TODO: Failure
				saveUpdateResponse.setStatus("FAILURE");
				saveUpdateResponse.setMessage("interrupted checksum data");
			}

		} catch (Exception exc) {
			log.error("Exception while saving gatewayresponse Transaction Details :: ", exc);
			throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
		}
		if (ObjectsUtil.isNull(transactionDetailModel)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(saveUpdateResponse);
	}

	private Optional<PayUResponse> doPayUVerifyProcess(TransactionDetailModel transactionDetailModel) {

		MultiValueMap<String, String> multiValueMap = paymentService.getPayUVerifyReqParams(transactionDetailModel);
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		if(transactionDetailModel.getCitizenFlag())
			requestHeaders.add("Authorization", payUAuthroization4citizen);
		else
			requestHeaders.add("Authorization", payUAuthroization);

		HttpEntity<MultiValueMap<String, String>> payuRequest = new HttpEntity<MultiValueMap<String, String>>(
				multiValueMap, requestHeaders);

		try {
			ResponseEntity<PayUResponse> response = restTemplate.postForEntity(payUVerifyUrl, payuRequest,
					PayUResponse.class);
			log.info("response from payu verify:" + response.getStatusCode());

			if (response.hasBody()) {
				return Optional.of(response.getBody());
			}
		} catch (RestClientException rce) {
			log.error("RestTemplate Exception while payu verification.", rce);
		}

		return Optional.empty();
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
    @RequestMapping(value = "/payu/paymentverifyparameter", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPayuUVerifyParameter(@RequestHeader("Authorization") String token, @Valid @RequestBody TransactionDetailModel transactionDetailModel) {
    	log.info(":::::getPaymentVerifyParameter::::start::::");
    		transactionDetailModel.setCitizenFlag(true);
    		Optional<PayUResponse> payUResponseOptional = doPayUVerifyProcess(transactionDetailModel);
    		
    		if (!isPayUResponseValid(payUResponseOptional, transactionDetailModel.getTransactionNo())) {
				log.info("PayU verification response validation is failed.");
				//status update in case of failure
				transactionDetailModel.setPayURespopnseStatus(true);
			}else{
	    		log.info(":::payUResponseOptional::verify:: " + payUResponseOptional);
				PayUTransactionDetails payUTransactionDetails = payUResponseOptional.get().getResult().get(0).getPostBackParam();
	
				log.info("" + payUTransactionDetails.getStatus() + "," + payUTransactionDetails.getField9() + ","
						+ payUTransactionDetails.getError_Message());
				transactionDetailModel.setPayUTransactionDetails(payUTransactionDetails);
			}
		return ResponseEntity.ok(transactionDetailModel);
    }
	
}
