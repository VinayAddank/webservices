package org.rta.registration.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.enums.NotificationType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.InvalidPrNumberException;
import org.rta.core.exception.InvalidStatusException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.PrNumberExistException;
import org.rta.core.exception.PrSeriesMismatchException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.exceptioncode.InvalidPrNumberExceptionCode;
import org.rta.core.exceptioncode.PrNumberExistExceptionCode;
import org.rta.core.exceptioncode.PrSeriesMismatchExceptionCode;
import org.rta.core.model.OwnerConscent;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.VehicleReassignmentModel;
import org.rta.core.model.application.ApplicationModel;
import org.rta.core.model.application.CommentModel;
import org.rta.core.model.application.GeneralDetails;
import org.rta.core.model.application.rejection.RejectionHistoryModel;
import org.rta.core.model.appversion.AppVersionModel;
import org.rta.core.model.citizen.CitizenApplicationModel;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.docs.ApplicantDocsModel;
import org.rta.core.model.docs.ApprovalDocModel;
import org.rta.core.model.export.SinkAllData;
import org.rta.core.model.finance.FreshRcModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.master.VehicleSpecialNoModel;
import org.rta.core.model.master.registration.SpecialNumberModel;
import org.rta.core.model.response.ApplicationCountResponse;
import org.rta.core.model.specialnumber.SpecialNumberFeeModel;
import org.rta.core.model.vehicle.SuspendedRCNumberModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;
import org.rta.core.service.application.ApplicationService;
import org.rta.core.service.application.rejection.RejectionHistoryService;
import org.rta.core.service.application.specialnumber.SpecialNumberService;
import org.rta.core.service.appversion.AppVersionService;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.export.ExportDataService;
import org.rta.core.service.master.RegistrationCategoryService;
import org.rta.core.service.payment.gateway.TransactionDetailService;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.core.utils.ValidationUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.cms.CmsLiveSyncingService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this controller use for all application related service like rejection
 * document
 */
@RestController
public class ApplicationController {

	private static final Logger logger = Logger.getLogger(ApplicationController.class);

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private RejectionHistoryService rejectionHistoryService;

	@Autowired
	private VehicleService vehicleService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ApplicationService applicatioService;

	@Value("${jwt.claim.secret}")
	private String claimSecret;

	@Value("${attachments.downloaded.path}")
	private String attachmentDocPath;

	@Value("${rta.employee.signature.path}")
	private String signaturePath;

	@Value("${base.url}")
	private String baseURL;

	@Autowired
	private CommunicationService communicationService;
	@Autowired
	TransactionDetailService transactionDetailService;

	@Autowired
	private ExportDataService exportDataService;

	@Autowired
	private AppVersionService appVersionService;

	@Autowired
	private FcDetailsService fcDetailsService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private RegistrationCategoryService registrationCategoryService;
	
	@Autowired
	private SpecialNumberService specialNumberService;
	
	@Autowired
	private VehicleDetailsService vehicleDetailsService;
	
	@Autowired
	private CmsLiveSyncingService cmsLiveSyncingService;

	public final static short SEND_SMS_EMAIL = 0;
	public final static short SEND_SMS = 1;
	public final static short SEND_EMAIL = 2;

	@RequestMapping(value = "test/migrate", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public void mig() {
		logger.info("Start test migrateScript Excel sheet");
		applicatioService.migrateScript();
		logger.info("End test migrateScript Excel sheet");
	}

	@RequestMapping(value = "rejectedattachment/{attachmentDetailsId}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public List<RejectionHistoryModel> getAllRejectAttachment(
			@PathVariable("attachmentDetailsId") Long attachmentDetailsId) {
		logger.info("Rejected Document : " + attachmentDetailsId);
		List<RejectionHistoryModel> rejectionHistoryModels = rejectionHistoryService.getAll(attachmentDetailsId);

		return rejectionHistoryModels;
	}

	@RequestMapping(value = "rejectedattachment", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> rejectedDocs(@Valid @RequestBody RejectionHistoryModel rejectionHistoryModel) {

		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(rejectionHistoryModel)) {
			logger.error("RejectionHistoryModel : " + rejectionHistoryModel);
			errorModel.setMessage("RejectionHistoryModel Required !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}

		String token = request.getHeader(tokenHeader);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		/*
		 * Long userId = jwtTokenUtil.getUserIdFromToken(token);
		 * rejectionHistoryModel.setUserId(1l);
		 * rejectionHistoryModel.setUserType(UserType.ROLE_MVI);
		 */
		rejectionHistoryModel.setCreatedBy(username);
		rejectionHistoryModel.setModifiedBy(username);
		rejectionHistoryModel.setCreatedOn(DateUtil.getDateInLong(new Date()));
		rejectionHistoryModel.setModifiedOn(DateUtil.getDateInLong(new Date()));
		Long id = rejectionHistoryService.save(rejectionHistoryModel);

		logger.info("Rejection Document Succsssfully Saved " + id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	/**
	 * 
	 * Get Applications for Dealer
	 */
	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(value = "application/dashboard/{apptype}/{status}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getApplicationStatus(@PathVariable("apptype") String appType,
			@PathVariable("status") String status, @RequestParam(name = "from", required = false) Long from,
			@RequestParam(name = "to", required = false) Long to,
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "per_page_records", required = true) Integer perPageRecords,
			@RequestParam(name = "page_number", required = true) Integer pageNumber,
			@RequestParam(name = "regcategory", required = false) String regcatogry) {
		logger.info(":::::::getApplicationStatus:::::::::");
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		UserType userType;
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(perPageRecords) || ObjectsUtil.isNull(pageNumber)) {
			errorModel.setMessage("per page records and page number can not be null !!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		try {
			userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		} catch (IllegalArgumentException e) {
			errorModel.setMessage("UserType Invalid");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		Map<String, Object> mapObject = new HashMap<String, Object>();
		try {
			mapObject = vehicleService.getApplicationStatus(userId, userType, appType,
					Status.getStatus(status).getValue(), regcatogry, from, to, query, perPageRecords, pageNumber);
		} catch (Exception e) {
			logger.error("Internal Server Error : " + e.getMessage());
			errorModel.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(mapObject);
	}

	/**
	 * Get Applications for Dealer
	 */
	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(value = "application/count/{type}/{status}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getApplicationCount(@PathVariable("type") String type,
			@PathVariable("status") String status,
			@RequestParam(name = "regcategory", required = false) String regCatogry) {

		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		Long applicationCount = vehicleService.getApplicationCount(userId, type, Status.getStatus(status.toUpperCase()),
				regCatogry);
		logger.info("Application Count , type   " + type + " and " + status + " = " + applicationCount);
		ApplicationCountResponse response = null;
		if (applicationCount == null)
			response = new ApplicationCountResponse(0l, type, status);
		else
			response = new ApplicationCountResponse(applicationCount, type, status);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_DEALER')")
	@RequestMapping(value = "applications/{vehiclercid}/status/{status}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> changeStatus(@RequestHeader("clientip") String ip,
			@PathVariable("vehiclercid") String vehicleRcId, @PathVariable("status") String status,
			@RequestBody(required = false) CommentModel comment, @RequestParam(required = false) String prNumber,
			@RequestParam(required = false, defaultValue = "false") Boolean skipSpecial) {
		Status status1 = Status.getStatus(status);
		SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(status1) || (status1 != Status.APPROVED && status1 != Status.REJECTED
				&& status1 != Status.CANCELLED && status1 != Status.PR_PENDING)) {
			logger.error("Invalid Status : " + status1);
			errorResponse.setMessage("Invalid Status !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		if (!ValidationUtil.validateIPAddress(ip)) {
			errorResponse.setMessage("clientip is invalid !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		UserType userType;
		try {
			userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		} catch (IllegalArgumentException e) {
			errorResponse.setMessage("Invalid User Type !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		Long vrcId;
		Map<Long, Status> map = null;
		try {
			String decryptedVehicleRcId = decrypt(vehicleRcId);
			vrcId = Long.parseLong(decryptedVehicleRcId);
			map = applicatioService.changeStatus(userId, userName, userType, vrcId, status1, comment, ip, prNumber,
					skipSpecial);

		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorResponse.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);

		} catch (NumberFormatException ex) {
			logger.error("Invalid vehicleRcId : " + ex);
			errorResponse.setMessage("Invalid vehicleRcId !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		} catch (IllegalArgumentException e) {
			logger.error("invalid status : " + e);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		} catch (InvalidStatusException e) {
			logger.error("invalid status : " + e);
			errorResponse.setMessage("invalid status provided !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		} catch (NotFoundException e) {
			logger.error("application not found : " + e);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		} catch (PrSeriesMismatchException ex) {
			logger.error("Invalid Pr number" + ex.getMessage());
			Map<String, String> errMap = new HashMap<String, String>();
			errMap.put("status", SaveUpdateResponse.FAILURE);
			errMap.put("Pr Number", prNumber);
			errMap.put("message", "This pr series doesn't belongs to your RTA office.");
			errMap.put("code", PrSeriesMismatchExceptionCode.PR_SERIES_MISMATCH.getErrCode());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errMap);
		} catch (InvalidPrNumberException ex) {
			logger.error("Invalid Pr number" + ex.getMessage());
			Map<String, String> errMap = new HashMap<String, String>();
			errMap.put("status", SaveUpdateResponse.FAILURE);
			errMap.put("Pr Number", prNumber);
			errMap.put("message", "Pr not special number.");
			errMap.put("code", InvalidPrNumberExceptionCode.INVALID_PR_NUMBER_EXCEPTION.getErrCode());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errMap);
		} catch (PrNumberExistException ex) {
			logger.error("Invalid Pr number" + ex.getMessage());
			Map<String, String> errMap = new HashMap<String, String>();
			errMap.put("status", SaveUpdateResponse.FAILURE);
			errMap.put("Pr Number", prNumber);
			errMap.put("message", "RC already exists, please select another no.");
			errMap.put("code", PrNumberExistExceptionCode.PRNUMBER_ALREADY_EXIST.getErrCode());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errMap);
		} catch (Exception e) {
			logger.error("Exception Occured in changeStatus");
			logger.debug("Exception : ", e);
			errorResponse.setMessage(e.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		boolean msgSend = false;
		CustMsgModel custModel = null;
		if (Status.REJECTED == status1 && userType == UserType.ROLE_RTO) {
			custModel = communicationService.setCustModelForNotificationType(vrcId,
					NotificationType.NOTIFICATION_FOR_APP_REJECTION);
			msgSend = communicationService.sendMsg(SEND_SMS_EMAIL, custModel);
			logger.info("RTO rejected application for VehicleRcId : " + vehicleRcId + ". Notification send status is "
					+ msgSend);
		}
		if (!ObjectsUtil.isNull(map) && (userType == UserType.ROLE_RTO || userType == UserType.ROLE_AO)) {
			for (Map.Entry<Long, Status> entry : map.entrySet()) {
				Long id = entry.getKey();
				if (!ObjectsUtil.isNull(id) && map.get(id) == Status.APPROVED) {
					if (vehicleService.getIsPrGenerated(vrcId)) {
						try {
							String result = cmsLiveSyncingService.cmsLiveSyncingService(vrcId ,null, ServiceType.FRESH_REGISTRATION.getCode());
				        	logger.info("::: Syncing data with CMS status :::: " + result);
						} catch (Exception e) {
							logger.error(" getting error with CMS syncing " + e.getMessage());
						}
						SaveUpdateResponse response =	vehicleDetailsService.vehicleAlterationSyncWithVehicleDetails(vrcId, userName, token);
						logger.info(" alteration syncing status for " + vrcId + " : "+ response.getStatus());
					}
				}
				break;
			}
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@RequestMapping(value = "testsyncfitness/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> testSyncFitnessData(@PathVariable("vehiclercid") Long vrcId) {
		logger.info(" testSyncFitnessData for vehicleRcId => " + vrcId);
		String fcMsg = "";
		try {
			RegistrationCategoryModel registrationCategoryModel = registrationCategoryService
					.getRegistrationCategoryDetails(vrcId);
			logger.info(":Fitness start::::::: " + registrationCategoryModel.getRegistrationCategoryId());
			if (RegistrationCategoryType.TRANSPORT.getValue() == registrationCategoryModel
					.getRegistrationCategoryId()) {
				// TODO do not generate permit with pr generation
				// String permitMsg = permitDetailsService.saveOrUpdate(vrcId,
				// userName);
				// logger.info("Permit has been generated for VehicleRcId : " +
				// vehicleRcId + " is " + permitMsg);
				fcMsg = fcDetailsService.saveOrUpdate(vrcId, "admin");
				logger.info("fc has been generated for VehicleRcId : " + vrcId + " is " + fcMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();

			logger.info("getting error in Prmit and FC : " + vrcId);
		}
		return ResponseEntity.ok(fcMsg);
	}

	@RequestMapping(value = "testsync/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> testSyncData(@PathVariable("vehiclercid") Long vehicleRcId, 
			@RequestParam("iscmssyncing") boolean isCmsSyncing ) {
		logger.info(" testSyncData for vehicleRcId => " + vehicleRcId);
		SinkAllData sinkAllData = exportDataService.getVehicleDetailsInfo(vehicleRcId,null);
		if(isCmsSyncing){
			String result = cmsLiveSyncingService.cmsLiveSyncingService(vehicleRcId ,null, ServiceType.FRESH_REGISTRATION.getCode());	
			System.out.println("::::::: CMS Syncing status :::::::: "+result );
		}
		return ResponseEntity.ok(sinkAllData);
	}

	/**
	 * Get count and application list of pending and rejected for CCO, MVI, AO
	 * and RTO
	 * 
	 * @param token
	 * @param count
	 * @param status
	 * @param from
	 * @param to
	 * @param query
	 * @return
	 */

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI')")
	@RequestMapping(value = "/applications/status/{status}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getApplication(@RequestHeader("Authorization") String token,
			@RequestParam(name = "count", required = false) boolean count, @PathVariable("status") String status,
			@RequestParam(name = "from", required = false) Long from,
			@RequestParam(name = "to", required = false) Long to,
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "regcategory", required = false) String regCategory,
			@RequestParam(name = "vehiclecategory", required = false) String vehicleCategory,
			@RequestParam(name = "new", required = false, defaultValue = "true") boolean newService) {
		logger.info("::::::::::::getApplication:::::::::::::");
		Status status1 = Status.getStatus(status);
		HashMap<String, Object> mapObject = new HashMap<String, Object>();
		Integer totalRecords = 0;
		SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(status1)) {
			errorResponse.setMessage("Invalid Status !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		List<ApplicationModel> applications = new ArrayList<ApplicationModel>();
		if (Status.PENDING == status1) {
			if (StringsUtil.isNullOrEmpty(vehicleCategory)) {
				vehicleCategory = VehicleCategory.FULLY_BUILD.getCode();
			}
			VehicleCategory vc = VehicleCategory.getVehicleCategoryByCode(vehicleCategory);
			if (ObjectsUtil.isNull(vc)) {
				throw new IllegalArgumentException("invalid vehicle category");
			}
			if (count) {
				Map<String, Object> map = new HashMap<>();
				map.put("status", status1.getLabel());
				if (newService) {
					logger.info("#### NEW SERVICE: Get Pending Application count for user: " + userId);
					map.put("count", applicatioService.countPendingApplications(userId, userType, regCategory));
				} else {
					if (vc == VehicleCategory.CHASSIS_ONLY && userType == UserType.ROLE_MVI) {
						// for chassis only vehicle the pending apps will be by
						// time
						applications = applicatioService.getPendingApplication(userId, userType, false, regCategory,
								vc);
					} else {
						applications = applicatioService.getPendingApplication(userId, userType, false, regCategory);
					}
					logger.info("getApplication count => for " + userType + ", status : " + status1);
					if (!ObjectsUtil.isNullOrEmpty(applications)) {
						map.put("count", applications.size());
					} else {
						map.put("count", 0);
					}
				}
				return ResponseEntity.ok(map);
			} else {
				if (newService) {
					if (vc == VehicleCategory.CHASSIS_ONLY && userType == UserType.ROLE_MVI) {
						// for chassis only vehicle the pending apps will be by time
						applications = applicatioService.getPendingApplication(userId, userType,
								false/* don't limit apps in this slot */, regCategory, vc);
					}  else {
						ApplicationModel newApp = applicatioService.getOpenORPendingApplications(userId, userType,
								regCategory, false, vc);
						if (null != newApp) {
							applications.add(newApp);
							if (ObjectsUtil.isNull(newApp.getVehicleCategory())) {
								newApp.setVehicleCategory(vc);
							}
						}
					}
				} else {
					if (vc == VehicleCategory.CHASSIS_ONLY && userType == UserType.ROLE_MVI) {
						// for chassis only vehicle the pending apps will be by
						// time
						applications = applicatioService.getPendingApplication(userId, userType,
								false/* don't limit apps in this slot */, regCategory, vc);
					} else {
						applications = applicatioService.getPendingApplication(userId, userType, true, regCategory);
					}
				}
				logger.info("getApplication => for " + userType + ", status : " + status1);
				if (!ObjectsUtil.isNullOrEmpty(applications)) {
					totalRecords = applications.size();
				}
			}
		} else if (status1 == Status.APPROVED || status1 == Status.REJECTED) {
			if (count) {
				logger.info("getApplication count => for " + userType + ", status : " + status1);
				Map<String, Object> map = new HashMap<>();
				map.put("status", status1.getLabel());
				map.put("count", applicatioService.countApplications(userId, status1, regCategory));
				return ResponseEntity.ok(map);
			} else {
				logger.info("getApplication => for " + userType + ", status : " + status1);
				mapObject = applicatioService.getApprovedRejectedApps(userId, query, from, to, perPageRecords,
						pageNumber, status1, regCategory);
				if (mapObject != null) {
					applications = (List<ApplicationModel>) mapObject.get("applicationModels");
					totalRecords = (Integer) mapObject.get("totalRecords");
				}
			}
		}
		mapObject = new HashMap<String, Object>();
		mapObject.put("applicationModels", applications);
		mapObject.put("totalRecords", totalRecords);
		if (ObjectsUtil.isNullOrEmpty(applications)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(mapObject);
	}

	private String encrypt(String str) {
		return RtaCryptoUtil.generateSecureToken(str, claimSecret);
	}

	private String decrypt(String str) {
		return RtaCryptoUtil.parseSecureToken(str, claimSecret);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI')")
	@RequestMapping(value = "/openapplication/{vehiclercid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> applicationOpen(@RequestHeader("clientip") String ip,
			@PathVariable("vehiclercid") Long vehicleRcId,
			@RequestHeader(name = "new", required = false, defaultValue = "true") boolean newService) {
		logger.info("Open application request for vehicleRcId => " + vehicleRcId);
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		Boolean response = null;
		try {
			response = applicatioService.openApplication(vehicleRcId, userId, ip, newService);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			HashMap<String, String> errMap = new HashMap<>();
			logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errMap.put("message", "OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errMap);
		} catch (ConflictException e) {
			HashMap<String, String> errMap = new HashMap<>();
			errMap.put("status", HttpStatus.CONFLICT.toString());
			errMap.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(errMap);
		} catch (AccessDeniedException e) {
			HashMap<String, String> errMap = new HashMap<>();
			errMap.put("status", HttpStatus.FORBIDDEN.toString());
			errMap.put("message", e.getMessage());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errMap);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorResponse = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorResponse.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorResponse);
		}
		if (ObjectsUtil.isNull(response)) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
		return ResponseEntity.ok().build();
	}

	/**
	 * save action on document taken by Dealer, CCO, MVI
	 * 
	 * @param applicantDocModel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER')")
	@RequestMapping(value = "/application/doc/action", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveDocsAction(@RequestHeader("clientip") String ip,
			@RequestHeader("Authorization") String token, @Valid @RequestBody ApplicantDocsModel applicantDocModel,
			@RequestHeader(name = "new", required = false, defaultValue = "true") boolean newService) {
		SaveUpdateResponse saveUpdateMdl = null;
		try {
			saveUpdateMdl = saveDocsAction4Both(ip, token, applicantDocModel, false, newService);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		if (saveUpdateMdl == null) {
			saveUpdateMdl = new SaveUpdateResponse();
			saveUpdateMdl.setMessage("Failed to save/update !!!");
			saveUpdateMdl.setStatus(SaveUpdateResponse.FAILURE);
		}
		return ResponseEntity.ok(saveUpdateMdl);
	}

	/**
	 * save action for rejection on document taken by Dealer, CCO, MVI
	 * 
	 * @param applicantDocModel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER')")
	@RequestMapping(value = "/application/doc/action/rejected", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveDocsAction4Rejection(@RequestHeader("clientip") String ip,
			@RequestHeader("Authorization") String token, @Valid @RequestBody ApplicantDocsModel applicantDocModel,
			@RequestHeader(name = "new", required = false, defaultValue = "true") boolean newService) {
		SaveUpdateResponse saveUpdateMdl = null;
		try {
			saveUpdateMdl = saveDocsAction4Both(ip, token, applicantDocModel, true, newService);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		if (saveUpdateMdl == null) {
			saveUpdateMdl = new SaveUpdateResponse();
			saveUpdateMdl.setStatus(SaveUpdateResponse.FAILURE);
		}
		return ResponseEntity.ok(saveUpdateMdl);
	}

	private SaveUpdateResponse saveDocsAction4Both(String ip, String token, ApplicantDocsModel applicantDocModel,
			boolean rejected, boolean newService) {
		if (!ValidationUtil.validateIPAddress(ip)) {
			throw new IllegalArgumentException("clientip is invalid");
		}
		if (applicantDocModel.getVehicleRcId() == null) {
			throw new IllegalArgumentException("VehicleRcId not received !!!");
		}
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		List<ApprovalDocModel> docs = applicantDocModel.getAppDocs();
		if (ObjectsUtil.isNull(docs) || docs.size() <= 0) {
			throw new IllegalArgumentException("No Document Received !!!");
		}
		// get attachement for validation...
		ApplicantDocsModel validDocModel = applicatioService.getDocsForApplicant(applicantDocModel.getVehicleRcId(),
				userId, userType);
		Set<Long> validAttachmentIds = new HashSet<Long>();
		for (ApprovalDocModel vdocs : validDocModel.getAppDocs()) {
			validAttachmentIds.add(vdocs.getAttachmentDlId());
		}

		for (ApprovalDocModel doc : docs) {
			if (userType == UserType.ROLE_DEALER) {
				logger.debug("rejected => " + rejected);
				if (rejected) {
					if (doc.getStatus() == Status.PENDING || doc.getStatus() == Status.APPROVED) {
						doc.setStatus(Status.PENDING);
					} else {
						logger.debug("Invalid Action for Dealer !!! => " + doc.getStatus());
						throw new IllegalArgumentException("Invalid Action for Dealer !!!");
					}
				} else {
					logger.debug(
							"Document Attachment ID=> " + doc.getAttachmentDlId() + " :: Status=> " + doc.getStatus());
					if (doc.getStatus() != Status.PENDING) {
						logger.debug("Invalid Action for Dealer !!! => " + doc.getStatus());
						throw new IllegalArgumentException("Invalid Action for Dealer !!!");
					}
				}
			}
			if (doc.getStatus() == null) {
				throw new IllegalArgumentException("Status not received for some attachment(s) !!!");
			}
			if (doc.getAttachmentDlId() == null) {
				throw new IllegalArgumentException("AttachmentDlId not received for some attachment(s) !!!");
			}
			if (doc.getStatus() == Status.REJECTED) {
				if (ObjectsUtil.isNull(doc.getComments()) || doc.getComments().trim().equals("")) {
					throw new IllegalArgumentException("No comment received for some rejected doc(s) !!!");
				}
			}

			if (!validAttachmentIds.contains(doc.getAttachmentDlId())) {
				throw new IllegalArgumentException("Invalid AttachmentDlId !!!");
			}
		}
		if (userType != UserType.ROLE_DEALER) {
			boolean isInValid = true;
			if (newService) {
				logger.info("######### NEW SERVICE: Checklist actions on VehicleRcId: "
						+ applicantDocModel.getVehicleRcId());
				try {
				    ApplicationModel newApp = null;
				    if(userType == UserType.ROLE_MVI) {
				        VehicleDetailsModel vehicleDetails = vehicleDetailsService.getVehicleDetails(applicantDocModel.getVehicleRcId());
                        newApp = applicatioService.getOpenORPendingApplications(userId, userType, null, false, vehicleDetails.getVehicleCategory());
				    } else {
	                    newApp = applicatioService.getOpenORPendingApplications(userId, userType, null, false, null);
				    }
                    if (applicantDocModel.getVehicleRcId().compareTo(newApp.getVehicleRcId()) == 0) {
                        if (newApp.getStatus() != null && newApp.getStatus() == Status.OPEN) {
                            isInValid = false;
                        }
                    }
                } catch (VehicleRcNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
			} else {
				// #TODO remove this block
				List<ApplicationModel> applicationList = applicatioService.getPendingApplication(userId, userType,
						false, null, null);
				for (ApplicationModel app : applicationList) {
					if (applicantDocModel.getVehicleRcId().compareTo(app.getVehicleRcId()) == 0) {
						if (app.getStatus() != null && app.getStatus() == Status.OPEN) {
							isInValid = false;
							break;
						}
					}
				}
			}
			if (isInValid) {
				logger.error("Invalid action on Doc Comment on VehicleRcId: " + applicantDocModel.getVehicleRcId());
				throw new IllegalArgumentException("Invalid Action.");
			}
		}

		logger.debug("saveDocsAction => for vehicleRcId :" + applicantDocModel.getVehicleRcId() + " from ip : " + ip
				+ " By " + userRole);
		SaveUpdateResponse saveUpdateMdl = applicatioService.saveDocsAction(applicantDocModel, userId, userName,
				userType, ip, rejected);
		return saveUpdateMdl;
	}

	@RequestMapping(value = "/application/doc/{vehiclercid}/{forusertype}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getApplicantDocsForUser(@PathVariable("vehiclercid") Long vehicleRcId,
			@PathVariable("forusertype") String docForUserType) {
		String token = request.getHeader(tokenHeader);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		ApplicantDocsModel response = null;
		try {
			response = applicatioService.getDocsForApplicant(vehicleRcId, userId,
					UserType.getUserType(docForUserType.toUpperCase()));
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI', 'ROLE_DEALER')")
	@RequestMapping(value = "/application/{vehiclercid}/rejectionhistory", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> rejectionHistory(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId,
			@RequestParam(name = "count", required = false) boolean count) {
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		Integer rejectionHistoryCount;
		if (count) {
			Map<String, Integer> map = new HashMap<>();
			try {
				rejectionHistoryCount = applicatioService.rejectionHistoryCount(vehicleRcId);
			} catch (VehicleRcNotFoundException e) {
				errorModel.setMessage("Invalid VehicleRcId !!!");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
			}
			map.put("count", rejectionHistoryCount);
			return ResponseEntity.ok(map);
		} else {

			Map<Integer, Map<String, List<RejectionHistoryModel>>> map;
			try {
				map = applicatioService.rejectionHistory(vehicleRcId);
			} catch (VehicleRcNotFoundException e) {
				errorModel.setMessage("Invalid VehicleRcId !!!");
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
			}
			if (ObjectsUtil.isNullOrEmpty(map)) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok(map);
		}
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER')")
	@RequestMapping(value = "/application/issecondvehicle/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> isSecondVehicle(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId) {
		Boolean isSecondVehicle = null;
		try {
			isSecondVehicle = applicatioService.doNotRequireSecondVehicle(vehicleRcId);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		Map<String, Boolean> map = new HashMap<>();
		map.put("isSecondVehicle", isSecondVehicle);
		logger.info("isSecondVehicle for vehicleRcId =>" + vehicleRcId + " : " + isSecondVehicle);
		return ResponseEntity.ok(map);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_DEALER')")
	@RequestMapping(value = "/application/checkstatus/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> applicationStatus(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId) {
		logger.info("Getting applicationStatus for vehicleRcId =>" + vehicleRcId);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		Map<UserType, Status> usersStatus = null;
		try {
			usersStatus = applicatioService.getStatusOfUsers(vehicleRcId, userType);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}

		if (ObjectsUtil.isNullOrEmpty(usersStatus)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usersStatus);
	}

	@RequestMapping(value = "/application/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> applicationForVehicleRc(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId) {
		logger.info("Getting applicationForVehicleRc for vehicleRcId =>" + vehicleRcId);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		ApplicationModel appModel = null;
		try {
			appModel = applicatioService.applicationForVehicleRc(vehicleRcId, userType);
		} catch (IllegalArgumentException argumentExc) {
			logger.error("Invalid vehicleRcId : " + argumentExc);
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(argumentExc.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		if (ObjectsUtil.isNull(appModel)) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(appModel);
	}

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(value = "/application/rejectedsteps/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getRejectionHistory4Correction(@PathVariable("vehiclercid") Long vehicleRcId,
			HttpServletRequest request) {
		Map<String, Object> map;
		try {
			map = applicatioService.getRejectionHistory4Correction(vehicleRcId,
					UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(request.getHeader(tokenHeader)).get(0)));
		} catch (VehicleRcNotFoundException e) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage("Invalid VehicleRcId.");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
			errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(map);
	}

	/**
	 * notificationtype ={NOTIFICATION_FOR_PR_GEN =1, NOTIFICATION_FOR_TR_GEN=
	 * 2}
	 */

	@RequestMapping(value = "/sendnotification/{vehiclercid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> sendNotification(@PathVariable("vehiclercid") Long vehicleRcId,
			@RequestHeader("notificationtype") int notificationType, @RequestBody List<String> attachments) {
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		List<String> attRcds = getDocLocForAttachements(attachments);
		NotificationType notifyType = NotificationType.getNotificationType(notificationType);
		CustMsgModel custMsgModel = null;
		try {
			custMsgModel = communicationService.setCustModelForNotificationType(vehicleRcId, notifyType);
		} catch (IllegalArgumentException ie) {
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		boolean msgResponse = communicationService.sendMsg(SEND_SMS_EMAIL, custMsgModel);
		if (!msgResponse) {
			errorModel.setMessage("Message could not be send");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok().build();
	}

	private List<String> getDocLocForAttachements(List<String> attachments) {
		List<String> docAttach = new ArrayList<String>();
		String[] filePathNm = { "", "" };

		for (String fileURLandPath : attachments) {
			// fileURLandPath.replace("%","/");
			filePathNm = fileURLandPath.split(",");
			try {
				saveUrl(attachmentDocPath + File.separator + filePathNm[1], filePathNm[0]);
				docAttach.add(
						attachmentDocPath + File.separator + filePathNm[1] + "," + filePathNm[1].replace("%", "/"));
			} catch (IOException e) {
				logger.error("IOException in getDocLocForAttachements " + e.getMessage());
				logger.debug("Exception : ", e);
			}
		}

		return docAttach;

	}

	public static void saveUrl(final String filename, final String urlString)
			throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(urlString).openStream());
			fout = new FileOutputStream(filename);
			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} catch (Exception e) {
			logger.error("Exception in saveUrl : " + e.getMessage());
			logger.debug("Exception : ", e);
		} finally {
			if (in != null) {
				in.close();
			}
			if (fout != null) {
				fout.close();
			}
		}
	}

	// private int sendMsg(Long vehicleRcId, int msgMode, int notificationType,
	// List<String> attchments) {
	// CustMsgModel customerModel = null;
	// if (notificationType ==
	// NotificationType.NOTIFICATION_FOR_PR_GEN.getValue())
	// customerModel =
	// applicatioService.getCustDtlsForVehicleRCPR(vehicleRcId,notificationType);
	// else if (notificationType ==
	// NotificationType.NOTIFICATION_FOR_TR_GEN.getValue())
	// customerModel =
	// transactionDetailService.getCustDtlsForVehicleRC(vehicleRcId);
	//
	// if (customerModel == null)
	// throw new IllegalArgumentException("No Data received");
	// customerModel.setAttachments(attchments);
	//
	// try {
	// if (msgMode == SEND_SMS)
	// communicationService.sendSms(customerModel);
	// if (msgMode == SEND_EMAIL)
	// communicationService.sendEmail(customerModel);
	// if (msgMode == SEND_SMS_EMAIL) {
	// communicationService.sendSms(customerModel);
	// communicationService.sendEmail(customerModel);
	// }
	//
	// } catch (IllegalArgumentException e) {
	// return 0;
	// }
	//
	// return 1;
	//
	// }

	@RequestMapping(value = "/getsignature/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getSignatureUrl(@PathVariable("vehiclercid") Long vehcleRcId) {
		logger.debug("::Registration:getSignatureUrl::::::start:::: " + vehcleRcId);
		String signatureFile = "";
		try {
			String fileName = applicatioService.getEmployeeSignFileName(vehcleRcId);
			signatureFile = baseURL + File.separator + signaturePath + File.separator + fileName;
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		Map<String, String> responseMap = new HashMap<>(1);
		responseMap.put("absolutepath", signatureFile);
		logger.debug(":Registration::getSignatureUrl:::::end::: " + signatureFile);
		return ResponseEntity.ok(responseMap);
	}

	@RequestMapping(value = "/saveattachmentfornotification/{vehiclercid}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveAttachments4Notification(@PathVariable("vehiclercid") Long vehicleRcId,
			@RequestHeader("notificationtype") int notificationType, @RequestBody List<String> attachments) {
		String attachment = attachments.get(0);
		Boolean response = null;
		try {
			response = applicatioService.saveEventLogForAttachments(notificationType, vehicleRcId, attachment);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/sendnotifications", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> sendManualNotification(@RequestHeader("notificationtype") int notificationType) {

		if (NotificationType.getNotificationType(notificationType) == null)
			return ResponseEntity.ok("Incorrect notification Type");
		communicationService.sendNotificationToCustomer(NotificationType.getNotificationType(notificationType));
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER')")
	@RequestMapping(value = "/applications/processed/status/{status}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getUserActionsOnApplications(@RequestHeader("Authorization") String token,
			@RequestParam(name = "count", required = false) boolean count, @PathVariable("status") String status,
			@RequestParam(name = "from", required = false) Long from,
			@RequestParam(name = "to", required = false) Long to,
			@RequestParam(name = "q", required = false) String query,
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "regcategory", required = false) String regCategory) {
		Status status1 = Status.getStatus(status);
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(status1)) {
			errorModel.setMessage("Invalid Status !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		Map<String, Object> applications = null;
		if (Status.PENDING == status1) {
			logger.error("pending status not allowed for user processed apps");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		} else if (status1 == Status.APPROVED || status1 == Status.REJECTED) {
			if (count) {
				logger.info("getApplication count => for " + userType + ", status : " + status1);
				Map<String, Object> map = new HashMap<>();
				map.put("status", status1.getLabel());
				map.put("count", applicatioService.countProcessedApplications(userId, query, from, to, perPageRecords,
						pageNumber, status1, userType, regCategory));
				return ResponseEntity.ok(map);
			} else {
				logger.info("getApplication => for " + userType + ", status : " + status1);
				applications = applicatioService.getApplicationsProcessedByUser(userId, query, from, to, perPageRecords,
						pageNumber, status1, userType, regCategory);
				if (ObjectsUtil.isNullOrEmpty(applications)) {
					return ResponseEntity.noContent().build();
				}
			}
		}
		return ResponseEntity.ok(applications);
	}

	@RequestMapping(value = "/appversion/{major}/{minor}/{revision}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getAppVersionDetails(@PathVariable("major") Integer major,
			@PathVariable("minor") Integer minor, @PathVariable("revision") Integer revision) {
		AppVersionModel model = null;
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		try {
			model = appVersionService.getAppVersion(major, minor, revision);
		} catch (IllegalArgumentException ie) {
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		if (ObjectsUtil.isNull(model)) {
			errorModel.setMessage("Some Error Occured !!!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok().body(model);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO')")
	@RequestMapping(value = "/applications/prpending", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getPrPendingApplication(@RequestHeader("Authorization") String token,
			@RequestParam(name = "q", required = false) String trNumber,
			@RequestParam(name = "per_page_records", required = false) Integer perPageRecords,
			@RequestParam(name = "page_number", required = false) Integer pageNumber,
			@RequestParam(name = "regcategory", required = false) String regCategory) {

		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		Map<String, Object> applications = null;
		try {
			applications = applicatioService.getPrPendingApplications(userId, userType, trNumber, perPageRecords,
					pageNumber, regCategory);
		} catch (Exception ex) {
			logger.error(":::::::::getPrPendingApplication::::::::::Exception::::::" + ex.getMessage());
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}

		return ResponseEntity.ok(applications);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO')")
	@RequestMapping(value = "/applications/prpending/count", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getPrPendingApplicationCount(@RequestHeader("Authorization") String token,
			@RequestParam(name = "regcategory", required = false) String regCategory) {

		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		Map<String, Integer> applicationCount = new HashMap<String, Integer>();
		try {
			applicationCount.put("totalPRPendingApplication",
					applicatioService.getPrPendingApplicationsCount(userId, userType, regCategory));
		} catch (Exception ex) {
			logger.error(":::::::::getPrPendingApplicationCount::::::::::Exception::::::" + ex.getMessage());
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ex.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}

		return ResponseEntity.ok(applicationCount);
	}

	@RequestMapping(value = "/isvalidcaller/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> isValidCallerForVehicleRc(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehiclercId) {

		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		logger.info("Check invalid access for userid:" + userId);
		return ResponseEntity.ok(applicatioService.isValidCallerForVehicleRc(vehiclercId, userId));
		/*
		 * Boolean isValid; try {
		 * isValid=applicatioService.isValidCallerForVehicleRc(vehiclercId,
		 * userId); logger.info("isValidCallerForVehicleRc => for " + userId); }
		 * catch (Exception ex) { logger.error(
		 * ":::::::::isValidCallerForVehicleRc::::::::::Exception::::::" +
		 * ex.getMessage()); throw new
		 * RuntimeException("Some Error Occured !!!"); }
		 * 
		 * return ResponseEntity.ok(isValid);
		 */
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN', 'ROLE_BODY_BUILDER', 'ROLE_PUC','ROLE_ONLINE_FINANCER')")
	@RequestMapping(value = "/pr/{prnumber}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })

	public ResponseEntity<?> prDetails(@RequestHeader("Authorization") String token,
			@PathVariable("prnumber") String prNumber) {
		ApplicationModel applicationModel;
		try {
			applicationModel = applicatioService.getPRDetails(prNumber);
		} catch (NotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new SaveUpdateResponse(SaveUpdateResponse.FAILURE, "Invalid PR Number", null));
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(applicationModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN','ROLE_ONLINE_FINANCER','ROLE_BODY_BUILDER')")
	@RequestMapping(value = "/tr/{trnumber}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> trDetails(@RequestHeader("Authorization") String token,
			@PathVariable("trnumber") String trNumber) {
		ApplicationModel applicationModel = null;
		try {
			applicationModel = applicatioService.getTRDetails(trNumber);
		} catch (IllegalArgumentException ie) {
			SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(applicationModel);
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN', 'ROLE_PUC','ROLE_ONLINE_FINANCER')")
	@RequestMapping(value = "/trorpr/{number}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> prOrTrDetails(@RequestHeader("Authorization") String token,
			@PathVariable("number") String number) {
		Long vehicleRc;
		try {
			vehicleRc = applicatioService.getPROrTRDetails(number);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(vehicleRc);
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
	@RequestMapping(value = "/slots", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> slotApplicationSlots(@RequestHeader("Authorization") String token,
			@RequestBody CitizenApplicationModel citizenApplicationModel) {
		if (ObjectsUtil.isNull(citizenApplicationModel)) {
			return ResponseEntity.badRequest().build();
		}
		SaveUpdateResponse response;
		try {
			response = applicatioService.saveApplicationSlots(citizenApplicationModel);
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
	@RequestMapping(value = "/prno/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getPRNo(@PathVariable("vehiclercid") Long vehicleRcId) throws NotFoundException {
		String prNo;
		prNo = applicationService.getPrSeriesNo4Other(vehicleRcId);
		return ResponseEntity.ok(prNo);
	}

	@RequestMapping(value = "/application/generaldetails/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getGeneralDetails(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId) throws NotFoundException {
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		UserType userType = UserType.valueOf(userRole);
		GeneralDetails generalDetails = applicationService.getGeneralDetails(vehicleRcId, userType);
		return ResponseEntity.ok(generalDetails);
	}

	@RequestMapping(value = "/application/suspensiondetails/vehicle/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getSuspensionDetails(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId) throws NotFoundException {
		SuspendedRCNumberModel suspension = applicationService.getSuspendededDetailsByRC(vehicleRcId);
		return ResponseEntity.ok(suspension);
	}

	@RequestMapping(value = "/application/vehiclereassignment", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> vehicleReassignment(@RequestHeader("Authorization") String token,
			@RequestBody VehicleReassignmentModel vehicleReassignmentModel) throws NotFoundException {
		SaveUpdateResponse response;
		boolean flag = false;
		try {
			response = applicatioService.generateReassignmentVehicle(vehicleReassignmentModel);
			flag = true;
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		if(flag){
        	try{
        		String result = cmsLiveSyncingService.cmsLiveSyncingService(null ,vehicleReassignmentModel.getPrNumber(), ServiceType.VEHICLE_REASSIGNMENT.getCode());
            	logger.info("::: Syncing data with CMS status :::: " + result);
        	}catch (Exception e) {
        		logger.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
			}
        }
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO')")
	@RequestMapping(value = "/application/vehiclespecialnumberFee", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> vehicleSpecialNumberFee(@RequestHeader("Authorization") String token,
			@Valid @RequestBody SpecialNumberFeeModel specialNumberFeeModel) throws NotFoundException {

		SaveUpdateResponse response = specialNumberService.vehicleSpecialNumberFee(specialNumberFeeModel);
		return ResponseEntity.ok(response);
	}

	// @PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO')")
	@RequestMapping(value = "/application/specialnumber", method = RequestMethod.GET)
	public ResponseEntity<List<SpecialNumberModel>> getSpecialNumber(@RequestHeader("Authorization") String token,
			@RequestParam(value = "rtaOfcId", required = false) String rtaOfcId) {
		String userRole = jwtTokenUtil.getUserRoleFromToken(token).get(0);
		Long userID = jwtTokenUtil.getUserIdFromToken(token);
		List<SpecialNumberModel> list = specialNumberService.specialNumberList(UserType.valueOf(userRole), userID,
				rtaOfcId);
		return ResponseEntity.ok(list);
	}

	@PreAuthorize("hasAnyRole('ROLE_AO')")
	@RequestMapping(value = "/application/lockSpecialNumber", method = RequestMethod.POST)
	public ResponseEntity<?> lockSpecialNumber(@RequestHeader("Authorization") String token,
			@RequestBody VehicleSpecialNoModel vehicleSpecialNoModel) {
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		Long userID = jwtTokenUtil.getUserIdFromToken(token);
		SaveUpdateResponse response = specialNumberService.lockVehicleSpecialNumber(userID, userName,
				vehicleSpecialNoModel.getTrNumber(), vehicleSpecialNoModel.getSpecialNumber());
		return ResponseEntity.ok(response);
	}

//	@RequestMapping(value = "/applicationdetails/status/{vehicleRcId}", method = RequestMethod.GET, produces = {
//			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
//	public ResponseEntity<?> getApplicationStatusDetails(@PathVariable("vehicleRcId") Long vehicleRcId) throws VehicleRcNotFoundException, UserNotFoundException {
//		FreshRcAppStatusDetailsModel applicationDetails = applicatioService.getApplicationStatusDetails(vehicleRcId);
//		return ResponseEntity.ok(applicationDetails);
//	}

	@RequestMapping(value = "/application/submitownerconscent/{appNumber}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> submitownerconscent(@RequestHeader("Authorization") String token,
			@RequestBody OwnerConscent ownerConscent,@PathVariable("appNumber") String appNumber)  {
		SaveUpdateResponse response = applicatioService.submitOwnerConscent(ownerConscent,appNumber);
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/application/updateownerconsent/{appNumber}", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateOwnerConsent(@RequestHeader("Authorization") String token,
			@RequestBody OwnerConscent ownerConscent,@PathVariable("appNumber") String appNumber)  {
		ResponseModel<FreshRcModel> response = applicatioService.updateOwnerConscent(ownerConscent,appNumber);
		return ResponseEntity.ok(response.getData());
	}
	
}
