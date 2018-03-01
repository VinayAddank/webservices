/**
 * 
 */
package org.rta.registration.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.dao.finance.impl.FinancerFreshRcDAOImpl;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.exception.FoundException;
import org.rta.core.exception.UserNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.application.CommentModel;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.finance.FinanceApp;
import org.rta.core.model.finance.FinanceApproveRejectModel;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FinanceOtherServiceModel;
import org.rta.core.model.finance.FinancerFreshContactDetailsModel;
import org.rta.core.model.finance.FinancerModel;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.finance.ShowcaseInfoRequestModel;
import org.rta.core.model.finance.ShowcaseNoticeInfoModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.sync.HPAHPTSyncModel;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.citizen.model.CitizenTokenModel;
import org.rta.core.service.finance.FinanceApprovalService;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.finance.FinanceFreshRcService;
import org.rta.core.service.finance.FinancerFreshContactDetailsService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.impl.CommunicationServiceImpl;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import javassist.NotFoundException;

/**
 * @author shivangi.gupta
 *
 */
@RestController
public class FinanceController {

	private static final Logger log = Logger.getLogger(FinanceController.class);

	@Autowired
	FinanceDtlsService financeDtlsServices;

	@Autowired
	FinanceApprovalService financeApprovalServices;

	@Autowired
	CommunicationService communicationService;

	@Autowired
	private FinancerFreshContactDetailsService financerFreshContactDetailsService;

	@Autowired
	FinancerFreshRcDAOImpl financerFreshRcDAOImpl;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.claim.secret}")
	private String claimSecret;

	@Autowired
	HttpServletRequest request;

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private FinanceFreshRcService freshRcService;
	
	@Autowired
	private CitizenService citizenService;

	@Value("${url.citizen}")
	private String citizenURL;

	@RequestMapping(path = "/financerdetails", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> save(@Valid @RequestBody FinanceModel financeModel) {

		log.info(" FinanceDetails :: Financer Name => " + financeModel.getName() + ", for vehicle RC Id=> "
				+ financeModel.getVehicleRcId());
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(financeModel.getVehicleRcId(), claimSecret);
		try {
			financeModel.setVehicleRcId(String.valueOf(Long.parseLong(vehicleRcIdDecrypted)));
		} catch (NumberFormatException ex) {
			log.error("saveUpdate :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
			errorModel.setMessage("Invalid vehicleRcId!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		SaveUpdateResponse response = null;

		try {
			String token = request.getHeader(tokenHeader);
			financeModel.setUserNm(jwtTokenUtil.getUsernameFromToken(token));
			response = financeDtlsServices.saveUpdateFinanceDtls(financeModel);
			String vehicleRcIdEncripted = RtaCryptoUtil.generateSecureToken(String.valueOf(response.getVehicleRcId()),
					claimSecret);
			response.setVehicleRcId(vehicleRcIdEncripted);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			log.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (DataIntegrityViolationException ex) {
			log.error("Exception while saving Finance Details :: " + ex.getMessage());
			errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (InvalidDataAccessApiUsageException ex) {
			log.error("Exception while saving Finance Details :: " + ex.getMessage());
			errorModel.setMessage("Invalid Data Received !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (IllegalArgumentException ex) {
			log.error("Exception while saving Finance Details :: " + ex.getMessage());
			errorModel.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (Exception ex) {
			log.error("Exception while saving Finance Details :: " + ex.getMessage());
			errorModel.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(response); // TODO check
	}

	@RequestMapping(path = "/financers", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<FinancerModel> getFinancerList() {
		log.info("<<<<<<<FINANCER LIST>>>>>>>>>");
		List<FinancerModel> financers = financeDtlsServices.getFinancers();
		return financers;
	}

	@RequestMapping(path = "/financerdetails/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinanceDetails(@PathVariable("vehiclercid") String vehicleRcIdEnc) {
		log.info("getFinanceDetails :: vehicleRcIdEncripted => " + vehicleRcIdEnc);
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
		Long vehicleRcId = null;
		try {
			vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException ex) {
			log.error("getInsuranceDetails :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		String token = request.getHeader(tokenHeader);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userTyp = UserType.valueOf(userRole);
		FinanceModel financeDetails = new FinanceModel();
		try {
			financeDetails = financeDtlsServices.getFinanceDtlsByVehicleRcId(vehicleRcId, userTyp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		financeDetails.setVehicleRcId(vehicleRcIdEnc);
		return ResponseEntity.ok(financeDetails);
	}

	@RequestMapping(path = "/step1generatetoken/{vehiclercid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> generateToken(@PathVariable("vehiclercid") String vehicleRc,
			@RequestParam(name = "quotation_price", required = false) Double quotationPrice) {
		log.info("generateToken :: vehiclercid => " + vehicleRc + " for quotation_price " + quotationPrice);
		Long vehicleRcId = null;
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		try {
			vehicleRcId = Long.parseLong(vehicleRc);
		} catch (NumberFormatException ex) {
			log.error("getToken :: Invalid VehicleRcId =>" + vehicleRc);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		Map<String, String> financeMap = financeApprovalServices.getTokenForVehicle(vehicleRcId, userId,
				quotationPrice);

		return ResponseEntity.ok(financeMap);
	}

	@PreAuthorize("hasRole('ROLE_CITIZEN')")
	@RequestMapping(path = "/hpa/apply/app_number/{app_number}/pr/{pr}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveCitizenHPARequestToken(@PathVariable("pr") String prNo,
			@PathVariable("app_number") String citizenTokenId,
			@RequestParam(name = "quotation_price", required = false) Double quotationPrice) {
		log.info("generateToken for HPA :: PrNumber => " + prNo);
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		financeApprovalServices.saveTokenForHPA(citizenTokenId, prNo, quotationPrice);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/step2vehiclefortoken/{financetokenid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getVehicleForToken(@PathVariable("financetokenid") String tokenId) {
		log.info("getVehicleForToken :: tokenId => " + tokenId);
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		HashMap<String, String> response = financeApprovalServices.getVehicleForToken(tokenId, userId);
		log.info("getVehicleForToken :: response => " + response);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/dashboardpendingapp", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getPendingList(
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "tokenid", required = false) String tokenId) {
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		HashMap financerApps = null;
		FinanceApp financeApp = null;
		List<Integer> statuses = new ArrayList<>();
		statuses.add(Status.STEP2_FINANCER_PENDING.getValue());
		statuses.add(Status.STEP2_FINANCER_ACCEPTED.getValue());
		statuses.add(Status.STEP3_BUYER_CONFIRM.getValue());
		// statuses.add(Status.HPT_BUYER_REQUEST.getValue());
		if (tokenId != null) {
			financeApp = financeApprovalServices.getApp4Financer(userId, statuses, tokenId);
			return ResponseEntity.ok(financeApp);
		}
		financerApps = (HashMap<String, Object>) financeApprovalServices.getAppList4Financer(userId, null, statuses,
				null, null, perPageRecords, pageNumber);
		return ResponseEntity.ok(financerApps);
	}

	// @PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	// @RequestMapping(path = "/dashboardpendingappforhpt", method =
	// RequestMethod.GET, produces = {
	// MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	// public ResponseEntity<?> getPendingList4HPT(
	// @RequestParam(name = "per_page_records", required = false) Integer
	// perPageRecords,
	// @RequestParam(name = "page_number", required = false) Integer pageNumber,
	// @RequestParam(name = "tokenid", required = false) String tokenId) {
	// String token = request.getHeader(tokenHeader);
	// Long userId = jwtTokenUtil.getUserIdFromToken(token);
	// HashMap financerApps = null;
	// FinanceApp financeApp = null;
	// List<Integer> statuses = new ArrayList<>();
	// statuses.add(Status.HPT_BUYER_REQUEST.getValue());
	// if (tokenId != null) {
	// financeApp = financeApprovalServices.getApp4Financer(userId, statuses,
	// tokenId);
	// return ResponseEntity.ok(financeApp);
	// }
	// financerApps = (HashMap<String, Object>)
	// financeApprovalServices.getAppList4Financer(userId, statuses,
	// perPageRecords, pageNumber);
	// return ResponseEntity.ok(financerApps);
	// }

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/dashboardapprovedapp", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getApprovedList(
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "tokenid", required = false) String tokenId) {
		log.info("::getApprovedList::::start::: " + tokenId + " pagenumber " + pageNumber);
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		HashMap<String, Object> financerApps = null;
		FinanceApp financeApp = null;
		List<Integer> statuses = new ArrayList<>();
		statuses.add(Status.STEP4_FINANCER_APPROVED.getValue());
		statuses.add(Status.APPROVED.getValue());
		if (tokenId != null) {
			try {
				financeApp = financeApprovalServices.getApp4Financer(userId, statuses, tokenId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok(financeApp);
		}

		try {
			financerApps = (HashMap<String, Object>) financeApprovalServices.getAppList4Financer(userId, null, statuses,
					null, null, perPageRecords, pageNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(financerApps);

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/dashboardrejectedapp", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getRejectedList(
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "tokenid", required = false) String tokenId) {
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		HashMap financerApps = null;
		FinanceApp financeApp = null;

		List<Integer> statuses = new ArrayList<>();
		statuses.add(Status.STEP4_FINANCER_REJECTED.getValue());
		statuses.add(Status.REJECTED.getValue());
		if (tokenId != null) {
			financeApp = financeApprovalServices.getApp4Financer(userId, statuses, tokenId);
			return ResponseEntity.ok(financeApp);
		}

		financerApps = (HashMap<String, Object>) financeApprovalServices.getAppList4Financer(userId, null, statuses,
				null, null, perPageRecords, pageNumber);
		return ResponseEntity.ok(financerApps);

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/step2financeraccept/{tokenid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinancerResponseForAcceptence(@PathVariable("tokenid") String tokenId) {
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		String msg = financeApprovalServices.approveRejectFinanceRequest(tokenId, Status.STEP2_FINANCER_ACCEPTED,
				financerId);
		log.info("financeraccept reponse ::::::::::: " + msg);
		Map response = new HashMap<>();
		response.put("financer", financerId);
		return ResponseEntity.ok(response);

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/step2financerreject/{tokenid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinancerResponseForRejection(@PathVariable("tokenid") String tokenId) {
		List<String> validStatus = new ArrayList<>();
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		String msg = financeApprovalServices.approveRejectFinanceRequest(tokenId, Status.STEP2_FINANCER_REJECTED,
				financerId);
		log.info("financerreject reponse ::::::::::: " + msg);
		log.info("-----------Sending msg for finnace rejection------------");
		CustMsgModel custMsgModel = financeApprovalServices.setMsgModel(tokenId, Status.STEP2_FINANCER_REJECTED,
				financerId);
		communicationService.sendMsg(0, custMsgModel);
		return ResponseEntity.ok(true);
	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	@RequestMapping(path = "dealer/financerlist/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinancerLstForDealer(@PathVariable("vehiclercid") String vehicleRc) {

		Long vehicleRcId = null;
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		try {
			vehicleRcId = Long.parseLong(vehicleRc);
		} catch (NumberFormatException ex) {
			log.error("getInsuranceDetails :: Invalid VehicleRcId =>" + vehicleRc);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		List<FinancerModel> finances = financeApprovalServices.getFinancersListForVehicle(vehicleRcId);
		return ResponseEntity.ok(finances);
	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	@RequestMapping(path = "citizen/financerlist/{app_num}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinancerLstForCitizen(@PathVariable("app_num") String appNum) {

		List<FinancerModel> finances = financeApprovalServices.getFinancersListForToken(appNum);
		return ResponseEntity.ok(finances);
	}

	@RequestMapping(path = "vehiclercidforpr/{pr_num}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	private Long getVehicleRcFromPR(@PathVariable("pr_num") String prNum) {
		Long vehicle = financeApprovalServices.getVehicleRcIdfromPr(prNum);
		return vehicle;
	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	@RequestMapping(path = "citizen/financerselection/{app_num}/{financerid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinancerFrmCitizen(@PathVariable("app_num") String appNum,
			@PathVariable("financerid") String financerId) {

		Long financer = null;
		Long dealerId = null;

		try {

			financer = Long.valueOf(financerId);
			String token = request.getHeader(tokenHeader);
			dealerId = jwtTokenUtil.getUserIdFromToken(token);
		} catch (NumberFormatException ex) {
			log.error("Either vehicle or finance id is in incorrect format");
			throw new IllegalArgumentException("Invalid Id!");
		}
		try {
			financeApprovalServices.saveCitizenResponse(appNum, financer, dealerId);
		} catch (FoundException ex) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	@RequestMapping(path = "dealer/financerselection/{vehiclercid}/{financerid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinancerfrmDealer(@PathVariable("vehiclercid") String vehicleRcId,
			@PathVariable("financerid") String financerId) {

		Long vehicle = null;
		Long financer = null;
		Long dealerId = null;

		try {
			vehicle = Long.valueOf(vehicleRcId);
			financer = Long.valueOf(financerId);
			String token = request.getHeader(tokenHeader);
			dealerId = jwtTokenUtil.getUserIdFromToken(token);
		} catch (NumberFormatException ex) {
			log.error("Either vehicle or finance id is in incorrect format");
			throw new IllegalArgumentException("Invalid Id!");
		}

		boolean done = false;
		try {
			financeApprovalServices.saveDealerResponse(vehicle, financer, dealerId);
			done = true;
		} catch (IllegalArgumentException e) {
			log.error("error for financer selection", e);
			throw e;
		}
		if (done) {
			try {
				CustMsgModel custMsgModel = financeApprovalServices.getCustomerMessageModel(financer, dealerId,
						vehicle);
				communicationService.sendMsg(CommunicationServiceImpl.SEND_EMAIL, custMsgModel);
			} catch (Exception e) {
				log.error("error in sending mail for financer selection", e);
			}
		}
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/step4appapproval/{tokenId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> approveAppByFinancer(@PathVariable("tokenId") String tokenId,
			@RequestBody FinanceModel financeModel) {
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		String msg = "";
		msg = financeApprovalServices.approveRejectFinalFinances(tokenId, Status.STEP4_FINANCER_APPROVED, financerId,
				financeModel);
		HttpStatus httpStatus = updateHPACitizen(tokenId, "APPROVED", "");
		log.info("step4 Approval response::::::::: " + msg);
		log.info("-----------Sending msg for finnace final approval------------");
		CustMsgModel custMsgModel = financeApprovalServices.setMsgModel(tokenId, Status.STEP4_FINANCER_APPROVED,
				financerId);
		try {
			communicationService.sendMsg(0, custMsgModel);
		} catch (Exception ex) {
			log.log(Level.ERROR, "Exception in communicationService", ex);
		}
		HashMap<String, String> map = new HashMap<>(1);
		map.put("financierid", financerId + "");
		return new ResponseEntity(map, httpStatus);

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "citizen/step4appapproval/{tokenId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> citizenApproveAppByFinancer(@PathVariable("tokenId") String tokenId,
			@RequestBody FinanceModel financeModel) {
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		String msg = financeApprovalServices.approveRejectFinalFinances(tokenId, Status.STEP4_FINANCER_APPROVED,
				financerId, financeModel);
		log.info("citizen/step4appapproval response::::::::: " + msg);
		HttpStatus httpStatus = updateHPACitizen(tokenId, "APPROVED", "");

		CustMsgModel custMsgModel = financeApprovalServices.setMsgModel(tokenId, Status.STEP4_FINANCER_APPROVED,
				financerId);
		try {
			communicationService.sendMsg(0, custMsgModel);
		} catch (Exception ex) {
			log.log(Level.ERROR, "Error in communicationService", ex);
		}
		HashMap<String, String> map = new HashMap<>(1);
		map.put("financierid", financerId + "");
		log.info("Citizen End Updation on citizen portal!!");
		return new ResponseEntity(map, httpStatus);

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/step4apprejection/{tokenId}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> rejectAppByFinancer(@PathVariable("tokenId") String tokenId) {
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		String msg = financeApprovalServices.approveRejectFinalFinances(tokenId, Status.STEP4_FINANCER_REJECTED,
				financerId, null);
		HttpStatus httpStatus = updateHPACitizen(tokenId, "REJECTED", "");
		log.info("citizen/step4 rejection response::::::::: " + msg);
		CustMsgModel custMsg = financeApprovalServices.setMsgModel(tokenId, Status.STEP4_FINANCER_REJECTED, financerId);
		try {
			communicationService.sendMsg(0, custMsg);
		} catch (Exception ex) {
			log.log(Level.ERROR, "Error in communicationService", ex);
		}
		log.info("Dealer End Updation on citizen portal!!");

		return ResponseEntity.ok().build();

	}

	// @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	// @RequestMapping(path = "hpt/apply", method = RequestMethod.POST, produces
	// = {
	// MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	// public ResponseEntity<?> appForHPT(@Valid @RequestBody
	// FinanceOtherServiceModel otherServiceModel) {
	// String token = request.getHeader(tokenHeader);
	// Long dealerId = jwtTokenUtil.getUserIdFromToken(token);
	// FinanceOtherServiceModel msg=null;
	//
	// try {
	// msg=
	// financeApprovalServices.applyForFinanceApprovalOtherService(otherServiceModel,dealerId);
	// } catch (VehicleRcNotFoundException e) {
	// log.error(e.getMessage());
	// return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	// } catch(NotFoundException e){
	// log.error(e.getMessage());
	// return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	// }catch (FoundException e) {
	// log.error(e.getMessage());
	// return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
	// }
	//
	// return ResponseEntity.ok().build();
	//
	// }

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "hpt/requestaccept", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> approveHPTRequest(@RequestBody FinanceApproveRejectModel financeApproveModel)
			throws NotFoundException {
		log.info(":::::::::::::approveHPTRequest::::: ");
		String token;
		Long financerId;
		Status stat;

		token = request.getHeader(tokenHeader);
		financerId = jwtTokenUtil.getUserIdFromToken(token);
		stat = Status.APPROVED;
		if (financeApproveModel == null)
			throw new IllegalArgumentException("No data found!!");
		if (financeApproveModel.getVehicleRcId().equals(""))
			throw new IllegalArgumentException("Enter vehicleRcId");
		if (financeApproveModel == null || (financeApproveModel.getFirstApproverName().equalsIgnoreCase("")
				|| financeApproveModel.getFirstApproverAadhar().equalsIgnoreCase("")
		/*
		 * || financeApproveModel.getSecondApproverName().equalsIgnoreCase("")
		 * || financeApproveModel.getSecondApproverAadhar().equalsIgnoreCase("")
		 */))
			throw new IllegalArgumentException("FinanceRejection must have atleast 1 Approvers and Aadhar number");

		financeApproveModel.setFinancerId(financerId);
		financeApproveModel.setStatus(stat);
		String msg = "";
		HttpStatus httpStatus;
		try {
			msg = financeApprovalServices.approveRejectHPTRequest(financeApproveModel);

			log.info("Approval/Rejection of HPT by financer::::::::::::: " + msg);
			String comment = financeApproveModel.getComment();

			httpStatus = updateCitizenAbtServiceApproval(financeApproveModel.getFinancerToken(), "APPROVED",
					comment == null ? "" : comment);
			CustMsgModel custMsg = financeApprovalServices.setMsgModel(financeApproveModel.getFinancerToken(),
					Status.APPROVED, financerId);

			communicationService.sendMsg(0, custMsg);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
		}
		return ResponseEntity.status(httpStatus).build();

	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "hpt/requestreject", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> rejectHPTRequest(@RequestBody FinanceApproveRejectModel financeRejectModel)
			throws NotFoundException {
		String token;
		Long financerId;
		Status stat;
		token = request.getHeader(tokenHeader);
		financerId = jwtTokenUtil.getUserIdFromToken(token);
		stat = Status.REJECTED;

		if (financeRejectModel.getVehicleRcId().equals(""))
			throw new IllegalArgumentException("Enter vehicleRcId");
		if (financeRejectModel == null || (financeRejectModel.getComment().equalsIgnoreCase("")
				&& financeRejectModel.getAttachmentsURL().equalsIgnoreCase("")))
			throw new IllegalArgumentException("FinanceRejection must have comments or Attachments with it");

		financeRejectModel.setFinancerId(financerId);
		financeRejectModel.setStatus(stat);
		String msg = financeApprovalServices.approveRejectHPTRequest(financeRejectModel);
		log.info("APProval/Rejection of HPT by financer::::::::::::: " + msg);
		String comments = financeRejectModel.getComment();
		HttpStatus httpStatus = updateCitizenAbtServiceApproval(financeRejectModel.getFinancerToken(), "REJECTED",
				comments == null ? "" : comments);
		return ResponseEntity.ok(httpStatus);

	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
	@RequestMapping(path = "hpt/status/{prnumber}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getHPTRequestStatus(@PathVariable("prnumber") String prNumber,
			@RequestParam(name = "appnumber", required = false) String appNumber) {

		FinanceOtherServiceModel response = null;
		try {
			response = financeApprovalServices.getHPTStatusOfVehicle(prNumber, appNumber);
		} catch (VehicleRcNotFoundException e) {
			// vehicle rc not found
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (NotFoundException e) {
			// not financed
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (UserNotFoundException e) {
			// financier not exist
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		log.info("HPT Terminated by financer:::::::::::; " + response);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/detailsforsanction/{token}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> details4Sanction(@PathVariable("token") String tokenId) {
		String token;
		Long financerId;
		try {
			token = request.getHeader(tokenHeader);
			financerId = jwtTokenUtil.getUserIdFromToken(token);
		} catch (NumberFormatException e) {
			log.error("Either vehicle or finance id is in incorrect format");
			throw new IllegalArgumentException("Invalid Id!");
		}
		FinanceApp response = financeApprovalServices.getDetailsForSanctionLetterGeneration(tokenId, financerId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/aggrementdetails/{token}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> details4Finance(@PathVariable("token") String tokenId) {
		String token;
		Long financerId;
		try {
			token = request.getHeader(tokenHeader);
			financerId = jwtTokenUtil.getUserIdFromToken(token);
		} catch (NumberFormatException e) {
			log.error("Either vehicle or finance id is in incorrect format");
			throw new IllegalArgumentException("Invalid Id!");
		}
		FinanceApp custmorInfo = financeApprovalServices.getDetailsForSanctionLetterGeneration(tokenId, financerId);
		FinanceModel custFinanceDtls = financeApprovalServices.getCustomerFinanceDtls(tokenId, financerId);
		HashMap map = new HashMap<>();
		map.put("customerInfo", custmorInfo);
		map.put("customerFinanceDtls", custFinanceDtls);
		return ResponseEntity.ok(map);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/sanctionletter/{token}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveSanctionLetter(@PathVariable("token") String tokenId,
			@RequestBody String sanctionLetter) {
		String token;
		Long financerId;
		String sl = null;
		try {
			// we had to replace backslash due to html escaping, so that we can
			// get proper url
			sl = sanctionLetter.replaceAll("\\\\/", "/");
			sl = sl.replace("[\"", "");
			sl = sl.replace("\"]", "");
			token = request.getHeader(tokenHeader);
			financerId = jwtTokenUtil.getUserIdFromToken(token);
		} catch (NumberFormatException e) {
			log.error("Either vehicle or finance id is in incorrect format");
			throw new IllegalArgumentException("Invalid Id!");
		}
		String response = financeApprovalServices.saveSanctionLetterURL(tokenId, financerId, sl);
		CustMsgModel custMsg = financeApprovalServices.setMsgModel(tokenId, Status.STEP2_FINANCER_ACCEPTED, financerId);
		communicationService.sendMsg(0, custMsg);
		HashMap map = new HashMap<String, String>();
		map.put("response", response);
		return ResponseEntity.ok(map);
	}

	private StringBuilder getRootURL() {
		return new StringBuilder(citizenURL);
	}

	@RequestMapping(path = "/hpaapplied/{pr_num}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> hasAppliedHPA(@PathVariable("pr_num") String prNo) {
		Boolean isHPA = false;
		try {
			isHPA = financeApprovalServices.isHPAAppliedForPRnum(prNo);
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(isHPA);
	}

	@RequestMapping(path = "/hpaapplied/vehiclerc/{vehicleRcId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> hasAppliedHPA(@PathVariable("vehicleRcId") Long vehicleRcId) {
		Boolean isHPA = false;
		try {
			isHPA = financeApprovalServices.isHPAAppliedForPRnum(vehicleRcId);
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(isHPA);
	}

	@RequestMapping(path = "/finance/info", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> financeInfo(@RequestParam(name = "rcid", required = false) Long rcId,
			@RequestParam(name = "prnumber", required = false) String prNumber) {
		FinanceModel financeDetails = new FinanceModel();
		try {
			financeDetails = financeApprovalServices.getFinanceDeatils(prNumber, rcId);
		} catch (VehicleRcNotFoundException e) {
			log.warn("Can't fetch finance details " + e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(financeDetails);
	}

	public HttpStatus updateHPACitizen(String tokenId, String status, String comment) {
		String token = request.getHeader(tokenHeader);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		CommentModel commentModel = new CommentModel();
		commentModel.setComment(comment);
		HttpEntity<CommentModel> httpEntity = new HttpEntity<CommentModel>(commentModel, headers);
		ResponseEntity<Object> response = null;
		HttpStatus httpStatus = null;
		try {
			String url = getRootURL().append("/hpa/finance/details/user/")
					.append(jwtTokenUtil.getUsernameFromToken(token)).append("/app/").append(tokenId).append("/action/")
					.append(status).toString();
			log.info(url);
			response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
			httpStatus = response.getStatusCode();
		} catch (HttpStatusCodeException ex) {
			httpStatus = ex.getStatusCode();
		}
		return httpStatus;
	}

	public HttpStatus updateCitizenAbtServiceApproval(String tokenId, String status, String comment) {
		log.info("::updateCitizenAbtServiceApproval::::::start:::");
		String token = request.getHeader(tokenHeader);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", token);
		CommentModel commentModel = new CommentModel();
		commentModel.setComment(comment);
		HttpEntity<CommentModel> httpEntity = new HttpEntity<CommentModel>(commentModel, headers);
		ResponseEntity<Object> response = null;
		HttpStatus httpStatus = null;
		String url = getRootURL().append("/application/financier/app/").append(tokenId).append("/action/")
				.append(status).toString();
		log.info("Calling citizen to terminate bpm => " + url);
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Object.class);
			httpStatus = response.getStatusCode();
		} catch (HttpStatusCodeException ex) {
			httpStatus = ex.getStatusCode();
		}
		log.info("::updateCitizenAbtServiceApproval:::::end::");
		return httpStatus;
	}
	
	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/freshrc", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinanceFreshRc(@RequestBody FreshRcModel freshRc) {

		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		freshRc.setFinancerId(financerId);
		ResponseModel<CitizenTokenModel> model = null;
		try {
			model = freshRcService.saveFinanceFreshRCDtl(token, freshRc);
			// if owner consent true
			// if (freshRc.getOwnerConsent()) {
			// CustMsgModel msgModel =
			// communicationService.setCustModelForNotificationType(freshRc.getVehicleRcId(),
			// NotificationType.NOTIFICATION_FOR_FINANCER_FRESH);
			// communicationService.sendMsg(0, msgModel);
			// }
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (org.rta.core.exception.NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		return ResponseEntity.ok(model);
	}

	@PreAuthorize("hasAnyRole('ROLE_ONLINE_FINANCER','ROLE_CCO')")
	@RequestMapping(path = "/freshrc/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveFinanceFreshRc(@PathVariable("vehiclercid") String vehicleRc) {

		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		FreshRcModel model = null;
		Long vehicle = null;
		try {
			vehicle = Long.valueOf(vehicleRc);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		try {
			model = freshRcService.getFinanceFreshRCDtlByFinancerId(vehicle, financerId);
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(model);
	}

	@PreAuthorize("hasAnyRole('ROLE_ONLINE_FINANCER','ROLE_CITIZEN')")
	@RequestMapping(path = "/freshrcbyaadhar/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinanceFreshRcData(@PathVariable("vehiclercid") String vehicleRc,
			@RequestParam(name = "aadharnumber", required = true) String aadharNumber) {

		if (StringsUtil.isNullOrEmpty(aadharNumber)) {
			log.error("invalid aadhar number = " + aadharNumber);
			return ResponseEntity.badRequest().build();
		}
		FreshRcModel model = null;
		Long vehicle = null;
		try {
			vehicle = Long.valueOf(vehicleRc);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		try {
			model = freshRcService.getFinanceFreshRCDtl(vehicle, aadharNumber);
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(model);
	}

	@RequestMapping(path = "/freshrchstep/{vehiclerc}/{currentstep}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateCurrentStep(@PathVariable("vehiclerc") Long vehicleRc,
			@PathVariable("currentstep") int currentstep) {
		String response = freshRcService.updateFreshRcStep(currentstep, vehicleRc);
		Map map = new HashMap<>();
		map.put("response", response);
		return ResponseEntity.ok(response);

	}

	@PreAuthorize("hasAnyRole('ROLE_DTC','ROLE_CCO')")
	@RequestMapping(path = "/freshrcs/{status}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> appList4FreshRc(@PathVariable("status") String status) {
		List<String> allowedStatus = new ArrayList<>();
		allowedStatus.add(Status.PENDING.getLabel());
		allowedStatus.add(Status.APPROVED.getLabel());
		allowedStatus.add(Status.REJECTED.getLabel());
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		if (!allowedStatus.contains(status))
			throw new IllegalArgumentException("Invalid status " + status);
		List<FreshRcModel> response = freshRcService.freshRcAppListForApproverType(userId, Status.getStatus(status));
		return ResponseEntity.ok(response);

	}

	@PreAuthorize("hasAnyRole('ROLE_DTC','ROLE_CCO')")
	@RequestMapping(path = "/freshrcresponse/{prortr}/{status}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> approveRejectFreshRc(@PathVariable("prortr") String prOrTr,
			@PathVariable("status") String status) {
		List<String> allowedStatus = new ArrayList<>();
		allowedStatus.add(Status.APPROVED.getLabel());
		allowedStatus.add(Status.REJECTED.getLabel());
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		if (!allowedStatus.contains(status))
			throw new IllegalArgumentException("Invalid status " + status);
		FreshRcModel response = freshRcService.freshRcApproveReject(prOrTr, userId, Status.getStatus(status));
		return ResponseEntity.ok(response);
	}

	/**
	 * For NOC / change of address and other services will take the financier
	 * response.
	 * 
	 * @param prNum
	 * @return
	 */
	@RequestMapping(path = "/finance_response/{pr_num}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinanceStatus(@PathVariable("pr_num") String prNum) {
		Boolean response = true;
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_CITIZEN')")
	@RequestMapping(path = "/finance/otherservice/apply", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> applyOtherServiceForFinancierApproval(@RequestBody FinanceOtherServiceModel model) {
		log.info("Apply Other service for appNum: " + model.getAppNo() + " and ServiceType" + model.getServiceCode());
		String token = request.getHeader(tokenHeader);
		Long requester = jwtTokenUtil.getUserIdFromToken(token);
		FinanceOtherServiceModel response = null;
		try {
			response = financeApprovalServices.applyForFinanceApprovalOtherService(model, requester);
		} catch (VehicleRcNotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (NotFoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} catch (FoundException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
		}
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/finance/otherservice/{app_num}/{status}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveOtherServiceResp(@PathVariable("app_num") String appNum,
            @PathVariable("status") String status, @RequestBody CommentModel commentModel) throws NotFoundException {
        log.info("saveOtherServiceResp Other service for appNum: " + appNum + " and ServiceType" + status);
        String comments = null;
        if (ObjectsUtil.isNotNull(commentModel)) {
            comments = commentModel.getComment();
        }
        String token = request.getHeader(tokenHeader);
        Long financerId = jwtTokenUtil.getUserIdFromToken(token);
        FinanceApproveRejectModel model = new FinanceApproveRejectModel();
        model.setFinancerToken(appNum);
        model.setFinancerId(financerId);
        model.setStatus(Status.getStatus(status));
        model.setComment(comments);
        String response = financeApprovalServices.approveRejectOtherService(model);
        Map<String, String> responsMap = new HashMap<>();
        responsMap.put("response", response);
        HttpStatus httpStatus = updateCitizenAbtServiceApproval(appNum, status, comments == null ? "" : comments);
     
        try {
            Integer serviceCode = financeApprovalServices.getServiceCode(appNum);
            if (!ObjectsUtil.isNull(serviceCode)  && (serviceCode == ServiceType.OWNERSHIP_TRANSFER_DEATH.getValue())) {
                CitizenServiceResponseModel<ApplicationFormDataModel> formDataResponse = citizenService.getApplicantDetails(appNum, token);
                if (formDataResponse.getHttpStatus() == HttpStatus.OK) {
                    ApplicationFormDataModel appFormDataModel = formDataResponse.getResponseBody();
                    try {
                        if (!ObjectsUtil.isNull(appFormDataModel.getFormData())) {
                            CustMsgModel custMsgModel = financeApprovalServices.setMsgModelOTD(appNum, model.getStatus(), financerId, appFormDataModel, token);
                            if (!ObjectsUtil.isNull(custMsgModel)) {
                                if (!ObjectsUtil.isNull(custMsgModel.getTo())) {
                                    communicationService.sendMsg(0, custMsgModel);
                                } else {
                                    communicationService.sendMsg(1, custMsgModel);
                                }
                            }
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Status not specified!!");
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Status not specified!!");
        }
        log.info("END saveOtherServiceResp Other service for appNum: " + appNum + " and ServiceType" + status);
        return ResponseEntity.status(httpStatus).build();
    }

        
    

    @PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/finance/dashboard/otherservice", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> dashboardOtherService(@RequestParam(name = "tokenid", required = false) String tokenId,
			@RequestParam(name = "rtaoffice", required = false) String rtaOffice,
			@RequestParam(name = "servicecode", required = false) String serviceCode,
			@RequestParam(name = "status", required = false) String stat,
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber) {

		log.info("DashBoard service for Other service ServiceType" + serviceCode + " rtaOffice " + rtaOffice
				+ " status " + stat);
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		ServiceType service = ServiceType.getServiceType(serviceCode);
		Status status = Status.getStatus(stat);
		if (status == null)
			throw new IllegalArgumentException("Status not specified!!");
		List<Integer> statuses = null;
		statuses = new ArrayList<>();
		statuses.add(status.getValue());

		Map<String, Object> response = financeApprovalServices.getAppList4FinancerNew(financerId, tokenId, statuses,
				service, rtaOffice, perPageRecords, pageNumber);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/finance/otherservice/rtaofficeservicelist", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> dashboardOtherServiceList() {
		String token = request.getHeader(tokenHeader);
		Long financerId = jwtTokenUtil.getUserIdFromToken(token);
		List<Integer> statuses = new ArrayList<>();
		statuses.add(Status.PENDING.getValue());
		Map response = financeApprovalServices.listOfRtoService4Financer(financerId, statuses);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(path = "/onlinefinanced/{prnumber}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> isOnlineFinanced(@PathVariable("prnumber") String prNum) {
		Boolean response = financeApprovalServices.isOnlineFinanced(prNum);
		HashMap map = new HashMap<>();
		map.put("isOnlineFinanced", response);
		return ResponseEntity.ok(map);
	}

	@RequestMapping(path = "/rtoresponseonservice/hpahpt/{status}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> rtoResponseForService(@RequestBody HPAHPTSyncModel hPAHPTSyncModel,
			@PathVariable("status") String status) {
		Status stat = Status.getStatus(status);
		if (stat == null || !(stat == Status.APPROVED || stat == Status.REJECTED))
			throw new IllegalArgumentException("Invalid status!");
		String token = request.getHeader(tokenHeader);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		financeApprovalServices.financeApproveRejectByRTO(userName, stat, hPAHPTSyncModel);
		return ResponseEntity.ok().build();
	}

	@RequestMapping(path = "/getshowcaseinfo", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getShowcaseInfo(@RequestBody ShowcaseInfoRequestModel showcaseRequestModel) {
		if (showcaseRequestModel == null) {
			log.error("invalid request for showcase model");
			return ResponseEntity.badRequest().build();
		}
		if (StringsUtil.isNullOrEmpty(showcaseRequestModel.getAadharNumber())) {
			log.error("invalid aadhar number");
			return ResponseEntity.badRequest().build();
		}
		ShowcaseNoticeInfoModel showcaseResponse;
		try {
			showcaseResponse = freshRcService.getShowcaseNoticeInfoModel(showcaseRequestModel);
		} catch (VehicleRcNotFoundException e) {
			log.error("invalid vehicleRcId : " + showcaseRequestModel.getVehicleRcId());
			return ResponseEntity.badRequest().build();
		} catch (UserNotFoundException e) {
			log.error("user not found for vehicleRcId : " + showcaseRequestModel.getVehicleRcId());
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(showcaseResponse);

	}

	@PreAuthorize("hasRole('ROLE_CITIZEN')")
	@RequestMapping(path = "/reiterate/finance/app/{app_no}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> reIterateAppFinanceStep(@PathVariable("app_no") String appNo) {
		SaveUpdateResponse res = financeApprovalServices.reIterateAppFinanceStep(appNo);
		return ResponseEntity.ok(res);
	}

	@RequestMapping(path = "/financierfreshdetails/{vehicleRcId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinancerFreshContactDetails(@PathVariable("vehicleRcId") Long vehicleRcId) {
		FinancerFreshContactDetailsModel financerFreshContactDetails = null;
		try {
			financerFreshContactDetails = financerFreshContactDetailsService
					.getFinancerFreshContactDetails(vehicleRcId);
		} catch (VehicleRcNotFoundException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(financerFreshContactDetails);
	}

	@PreAuthorize("hasRole('ROLE_ONLINE_FINANCER')")
	@RequestMapping(path = "/savefinancierfreshdetails", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public SaveUpdateResponse saveFinancerFreshContactDetails(
			@RequestBody FinancerFreshContactDetailsModel financerFreshContactDetailsModel)
			throws VehicleRcNotFoundException, UserNotFoundException {
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
		log.info(">>>>>>>>>save financer fresh details");
		FreshRcModel financerFreshRc = freshRcService.getFinanceFreshRCDtlByFinancerId(
				financerFreshContactDetailsModel.getVehicleRcId(), financerFreshContactDetailsModel.getFinancierId());
		if (ObjectsUtil.isNotNull(financerFreshRc)) {
			try {
				if (ObjectsUtil.isNull(financerFreshContactDetailsModel.getEmail())
						|| ObjectsUtil.isNull(financerFreshContactDetailsModel.getMobileNumber())) {
					log.error("Mobile No :" + financerFreshContactDetailsModel.getMobileNumber() + " Email : " + financerFreshContactDetailsModel.getEmail());
					throw new IllegalArgumentException("Mobile number and email id cannot be blank");
				}
				financerFreshContactDetailsModel.setCreatedBy(financerFreshRc.getCreatedBy());
				response = financerFreshContactDetailsService
						.saveFinancerFreshContactDetails(financerFreshContactDetailsModel);
			} catch (Exception e) {
				log.error("Getting error in request " + e.getMessage());
				response.setMessage("error caught while saving contact details");
				response.setStatus(SaveUpdateResponse.FAILURE);
				return response;
			}
		}

		return response;
	}

	// save show cause data
	@RequestMapping(path = "/saveshowcauseDate/{appNumber}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public SaveUpdateResponse saveShowCauseDate(@RequestBody FreshRcModel freshRcModel,
			@PathVariable("appNumber") String appNumber, @RequestHeader("Authorization") String token)
			throws VehicleRcNotFoundException, UserNotFoundException {
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
		log.info(">>>>>>>>>save Show cause date");
		FreshRcModel financerFreshRc = freshRcService.getFinanceFreshRCDtlByAppNumber(appNumber);
		if (ObjectsUtil.isNotNull(financerFreshRc)) {
			financerFreshRc.setShowCauseIssuedBy(financerFreshRc.getCreatedBy());
			financerFreshRc.setShowCauseDate(freshRcModel.getShowCauseDate());
			financerFreshRc.setShowCauseDoc(freshRcModel.getShowCauseDoc());
			
			response = freshRcService.saveFinanceShowCauseDetails(appNumber, financerFreshRc);
		}
		return response;
	}
	
	@RequestMapping(path = "/freshrcbyappno/{appNumber}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinanceFreshRcData(@PathVariable("appNumber") String appNumber) {
		FreshRcModel model = null;
		model = freshRcService.getFinanceFreshRCDtlByAppNumber(appNumber);
		return ResponseEntity.ok(model);
	}
	
	@RequestMapping(path = "/freshrcconsent", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getFinanceFreshRcListOnConsent() {
		List<FreshRcModel> response = freshRcService.getOpenedFreshRc();
		return ResponseEntity.ok(response);
	}
	
	
}
