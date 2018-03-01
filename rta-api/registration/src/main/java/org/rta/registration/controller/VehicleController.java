package org.rta.registration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.UserType;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.FoundException;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.exception.InvalidVehicleSubClassException;
import org.rta.core.exception.TaxTypeNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.TheftIntimationRevocationModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.sync.ApplicationFormDataModel;
import org.rta.core.model.vehicle.CommonServiceModel;
import org.rta.core.model.vehicle.DuplicateRegistrationModel;
import org.rta.core.model.vehicle.SeatingCovValueModel;
import org.rta.core.model.vehicle.StoppageTaxDetailsModel;
import org.rta.core.model.vehicle.StoppageTaxReportModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleAlterationUpdateModel;
import org.rta.core.model.vehicle.VehicleBodyModel;
import org.rta.core.model.vehicle.VehicleDetailsModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.rta.core.service.master.RegistrationCategoryService;
import org.rta.core.service.vehicle.StoppageTaxService;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.registration.service.vahan.VahanService;
import org.rta.registration.service.vahan.impl.UnregisteredVahanServiceImpl;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.rta.vahan.api.ResponseType;
import org.rta.vahan.api.VahanResponseModel;
import org.rta.vahan.api.unregistered.UnregisteredVahanClient;
import org.rta.vahan.api.unregistered.model.UnregisteredChassisInfo;
import org.rta.vahan.exception.IllegalEngineNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

	private static final Logger log = Logger.getLogger(VehicleController.class);

	private static final Integer CHASSIS_NUMBER_MAX_CHARACTERS = Integer.valueOf(25);
	private static final Integer ENGINE_NUMBER_MAX_CHARACTERS = Integer.valueOf(25);
	private static final String CONFLICT_WITH_RTA = "409";
	private static final String FOUND_IN_CMS = "302";
	private static final String BHARAT_STAGE_III = "777";
	
	private final VahanService vahanService;
	private final VehicleService vehicleService;
	private final VehicleDetailsService vehicleDetailsService;

	private final JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.claim.secret}")
	private String claimSecret;

	private final Integer currentStep;

	private final String tokenHeader;
	@Autowired
    private UnregisteredVahanClient vahanClient;

    @Autowired
    private ConversionService conversionService;
    
    @Autowired
	private RegistrationCategoryService registrationCategoryService;

	@Autowired
	private UnregisteredVahanServiceImpl unregisteredVahanServiceImpl;
	
	@Autowired
	private StoppageTaxService stoppageTaxService;

	@Autowired
	private CmsLiveSyncingService cmsLiveSyncingService;
	
	@Autowired
	public VehicleController(final VahanService vahanService, final VehicleService vehicleService,
			final JwtTokenUtil jwtTokenUtil, @Value("${rta.step.vehicle}") final Integer currentStep,
			final VehicleDetailsService vehicleDetailsService, @Value("${jwt.header}") final String tokenHeader) {
		this.vahanService = vahanService;
		this.vehicleService = vehicleService;
		this.jwtTokenUtil = jwtTokenUtil;
		this.currentStep = currentStep;
		this.vehicleDetailsService = vehicleDetailsService;
		this.tokenHeader = tokenHeader;
	}

	@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CCO')")
	@RequestMapping(produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<?> getVehicleDetails(@RequestParam(value = "chassisno", required = true) String chassisNumber,
			@RequestParam(value = "engineno", required = true) String engineNumber , @RequestParam(value = "migration", required = false) boolean migration) {
		// validate
		if (ObjectsUtil.isNull(chassisNumber) || (chassisNumber.trim().length() > CHASSIS_NUMBER_MAX_CHARACTERS)
				|| (engineNumber.trim().length() > ENGINE_NUMBER_MAX_CHARACTERS)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

		UnregisteredVehicleModel vehicleModel = null;
		try {

			vehicleModel = unregisteredVahanServiceImpl.getChassisInfo(chassisNumber, engineNumber , migration);
		} catch (InvalidEngineNumberException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (ConflictException ex) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("vehicleRcId", ex.getMessage());
			map.put("message", "Provided chassis number is already registered with RTA Application.");
			map.put("code", CONFLICT_WITH_RTA);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		} catch (FoundException ex) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", "Provided chassis number is already registered with CMS Application.");
			map.put("code", FOUND_IN_CMS);
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
		if (ObjectsUtil.isNull(vehicleModel)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(vehicleModel);
	}

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(/* value = "/{currentStep}", */
			produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, consumes = {
					MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<?> saveVehicleDetails(@RequestHeader("Authorization") String token,
			/* @RequestParam("currentStep") Integer currentStep, */
			@Valid @RequestBody VehicleDetailsRequestModel requestModel) {
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		Long userId = jwtTokenUtil.getUserIdFromToken(token);
		UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		if (ObjectsUtil.isNull(userId) || ObjectsUtil.isNull(userName)) {
			throw new IllegalArgumentException("bad request");
		}
		try {
			String decryptedVehicleRcId = decrypt(requestModel.getVehicleRcId());
			requestModel.setVehicleRcId(String.valueOf(Long.parseLong(decryptedVehicleRcId)));
			vehicleService.update(userId, requestModel, userName, currentStep, userType);
		} catch (NumberFormatException e) {
			log.error("saveUpdate :: Invalid VehicleRcId" + requestModel.getVehicleRcId() + " error : " + e);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		} catch (VehicleRcNotFoundException e) {
			log.error("vehicleRc not found : " + requestModel.getVehicleRcId() + " error : " + e);
			throw new IllegalArgumentException("vehicleRc not found!");
		} catch (TaxTypeNotFoundException e) {
			log.error("taxType code not found : " + requestModel.getVehicle().getTaxType() + " error : " + e);
			throw new IllegalArgumentException("taxType code not found!");
		} catch (DataIntegrityViolationException e) {
			log.error("Exception while saving/updating :: " + e.getMessage());
			throw new IllegalArgumentException(
					"chassisNo/engineNo is invalid or already registered !" + " error : " + e);
		} catch (InvalidDataAccessApiUsageException e) {
			log.error("Exception while saving/updating :: " + e);
			throw new IllegalArgumentException("Invalid Data Received !");
		} catch (InvalidVehicleSubClassException e) {
		    SaveUpdateResponse res = new SaveUpdateResponse(SaveUpdateResponse.FAILURE,
                    "invalid vehicle sub-class", requestModel.getVehicleRcId());
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(res);
        }
		return ResponseEntity.status(HttpStatus.OK).body(new SaveUpdateResponse(SaveUpdateResponse.SUCCESS,
				"saved successfully", encrypt(requestModel.getVehicleRcId()), currentStep));
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_CCO','ROLE_MVI','ROLE_DEALER','ROLE_CITIZEN','ROLE_BODY_BUILDER','ROLE_ONLINE_FINANCER', 'ROLE_ADMIN')")
	@RequestMapping(value = "/{vehiclercid}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<?> getVehicleDetailsByVehicleRcId(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") String vehicleRcId,
			@RequestParam(value = "isonlyvehicledetails", required = false) boolean isOnlyVehicleDetails,
			@RequestParam(value = "isvehiclealterdetails", required = false) boolean isVehicleAlterDetails) {
		// validate
		if (StringsUtil.isNullOrEmpty(vehicleRcId)) {
			throw new IllegalArgumentException("bad request");
		}
		UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		Long vrcId;
		try {
			String decryptedVehicleRcId = decrypt(vehicleRcId);
			vrcId = Long.parseLong(decryptedVehicleRcId);
		} catch (NumberFormatException ex) {
			log.error("saveUpdate :: Invalid VehicleRcId" + vehicleRcId);
			throw new IllegalArgumentException("Invalid vehicleRcId!");
		}
		if (isOnlyVehicleDetails) {

			VehicleDetailsModel responeModel = null;
			try {
				responeModel = vehicleDetailsService.getVehicleDetails(vrcId);
			} catch (VehicleRcNotFoundException e) {
				log.error("vehicle details not found : " + vehicleRcId);
				throw new IllegalArgumentException("vehicle Details not found!");
			}
			if (ObjectsUtil.isNull(responeModel)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(responeModel);
		} else if (isVehicleAlterDetails) {

			VehicleBodyModel responeModel = null;
			try {
				responeModel = vehicleDetailsService.getVehicleAlterationDetails(vrcId, userType);
			} catch (VehicleRcNotFoundException e) {
				log.error("vehicle details not found : " + vehicleRcId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			if (ObjectsUtil.isNull(responeModel)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.status(HttpStatus.OK).body(responeModel);
		}

		VehicleDetailsRequestModel vehicleModel = null;
		try {
			vehicleModel = vehicleService.getVehicleDetails(vrcId,userType);
		} catch (VehicleRcNotFoundException e) {
			log.error("vehicleRc not found : " + vehicleRcId);
			throw new IllegalArgumentException("vehicleRc not found!");
		}
		if (ObjectsUtil.isNull(vehicleModel)) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		vehicleModel.setVehicleRcId(encrypt(vehicleModel.getVehicleRcId()));
		return ResponseEntity.status(HttpStatus.OK).body(vehicleModel);
	}

	private String encrypt(String str) {
		return RtaCryptoUtil.generateSecureToken(str, claimSecret);
	}

	private String decrypt(String str) {
		return RtaCryptoUtil.parseSecureToken(str, claimSecret);
	}

	@PreAuthorize("hasRole('ROLE_DEALER')")
	@RequestMapping(value = "/{vehiclercid}/{vehicleclass}/invoice/{amount}", produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<?> isPanCardMandatory(@RequestHeader("Authorization") String token,
			@PathVariable("vehiclercid") Long vehicleRcId, @PathVariable("vehicleclass") String vehicleClass,
			@PathVariable("amount") Double invoiceAmount) {
		Map<String, String> map = vehicleService.getIsPanCardMandatory(vehicleRcId, vehicleClass, invoiceAmount);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	// @PreAuthorize("hasRole('ROLE_BODY_BUILDER')")
	@RequestMapping(value = "/bodybuilder", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	public ResponseEntity<?> updateVehicleDetails(@Valid @RequestBody VehicleBodyModel requestModel,
			@RequestParam(name = "isoldvehicle", required = false) boolean isOldVehicle, HttpServletRequest request) {
		if (ObjectsUtil.isNull(requestModel)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		String token = request.getHeader(tokenHeader);
		String userName = jwtTokenUtil.getUsernameFromToken(token);
		UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
		SaveUpdateResponse response = null;
		try {
			response = vehicleDetailsService.saveOrUpdateVehicleAlteration(requestModel, userName, userType,
					isOldVehicle, token);
		} catch (DataIntegrityViolationException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		} catch (InvalidDataAccessApiUsageException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		} catch (DataMismatchException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PreAuthorize("hasAnyRole('ROLE_RTO','ROLE_AO','ROLE_MVI')")
	@RequestMapping(value = "/seatingcapacity/{vehiclercid}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	public ResponseEntity<?> getMinimumSeatingCapacity(@PathVariable("vehiclercid") Long vehicleRcId) {
		if (ObjectsUtil.isNull(vehicleRcId)) {
			throw new IllegalArgumentException("Invalid Request..!!");
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("minimumSeatingCapacity", vehicleDetailsService.getMinimumSeatingCapacity(vehicleRcId));

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	@RequestMapping(value = "/alteration/details", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	public ResponseEntity<?> updateVehicleAlterationDetails(@Valid @RequestBody VehicleAlterationUpdateModel requestModel,
			HttpServletRequest request) {
		if (ObjectsUtil.isNull(requestModel)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, null, null);
		boolean flag = false;
		try {
			response = vehicleDetailsService.saveOrUpdateVehicleAlteration(requestModel,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
			flag = true;
		} catch (DataIntegrityViolationException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		} catch (InvalidDataAccessApiUsageException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		if(flag){
        	try{
        		String result = cmsLiveSyncingService.cmsLiveSyncingService(requestModel.getVehicleBodyModel().getVehicleRcId() ,null, ServiceType.VEHICLE_ATLERATION.getCode());
            	log.info("::: Syncing data with CMS status :::: " + result);
        	}catch (Exception e) {
        		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
			}
        }
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value = "/duplicateregistration/details", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	public ResponseEntity<?> updateDuplicateRegistrationDetails(
			@Valid @RequestBody DuplicateRegistrationModel requestModel, HttpServletRequest request) {
		if (ObjectsUtil.isNull(requestModel)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = vehicleService.update(requestModel,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));

		} catch (DataMismatchException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		} catch (Exception ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		try{
    		String result = cmsLiveSyncingService.cmsLiveSyncingService(null ,requestModel.getPrNumber(), ServiceType.DUPLICATE_REGISTRATION.getCode());
        	log.info("::: Syncing data with CMS status :::: " + result);
    	}catch (Exception e) {
    		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value = "/commoncitizenservice/details", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	public ResponseEntity<?> commonSaveUpdateDetails(@Valid @RequestBody CommonServiceModel requestModel,
			HttpServletRequest request) {
		log.debug("Sync with commonSaveUpdateDetails in registration with RC : " + requestModel.getPrNumber() + " for service : " + requestModel.getServiceType());
		if (ObjectsUtil.isNull(requestModel)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = vehicleService.update(requestModel,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (DataMismatchException ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		} catch (Exception ex) {
			log.error("Exception while update vehicle details :: " + ex.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
    	try{
    		String result = cmsLiveSyncingService.cmsLiveSyncingService(null ,requestModel.getPrNumber(), requestModel.getServiceType().getCode());
        	log.info("::: Syncing data with CMS status :::: " + result);
    	}catch (Exception e) {
    		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value = "/reg/sync/theft/{pr_number}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	public ResponseEntity<?> syncTI(@RequestBody TheftIntimationRevocationModel theftModel,
			@PathVariable("pr_number") String prNumber) {
		SaveUpdateResponse response;
		try {
			String userName = "";
			response = vehicleService.syncTI(theftModel, prNumber, userName);
		} catch (DataMismatchException e) {
			response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, e.getMessage(), null);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@RequestMapping(value="/reg/{pr_number}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<?> getVehicleClass(@PathVariable("pr_number") String prNumber){
		VehicleDetailsRequestModel vehicleModel=null;
    	try{
    		vehicleModel=vehicleService.getVehicleClass(prNumber);
    	} 
    	 catch (Exception e) {
				log.error("vehicle details not found : " + vehicleModel);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
    	return ResponseEntity.status(HttpStatus.OK).body(vehicleModel);
	}
	
	
	/*@RequestMapping(path = "/migration", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> gatewayresponse(HttpServletRequest request,
             @RequestBody UnregisteredVehicleModel unregisteredVehicleModel) {
        log.debug("::::::::Is Migration on migration server:::::start::::: " + unregisteredVehicleModel);
        try {
        	
			unregisteredVehicleModel = unregisteredVahanServiceImpl.saveMIgrate(unregisteredVehicleModel);
		} catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("::::::::Is Migration on migration server::::::end:::: ");
        return ResponseEntity.status(HttpStatus.OK).body(unregisteredVehicleModel);
        
	}   */
	
	
	@RequestMapping(path = "/migration/{chassisNumber}/{engineNumber}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getMIgration(HttpServletRequest request,@PathVariable("chassisNumber") String chassisNumber,
    		@PathVariable("engineNumber") String engineNumber) {
		UnregisteredVehicleModel uvm = new UnregisteredVehicleModel();
		System.out.println(":::111111::");
		log.info("::::::::Is Migration on migration server:::::start::::: " + chassisNumber  + " - " + engineNumber);
        try {
        	VahanResponseModel<UnregisteredChassisInfo> vrm;
        	try {
                vrm = vahanClient.getChassisInfo(chassisNumber, engineNumber);
                if (ObjectsUtil.isNull(vrm) || vrm.getResponseType() == ResponseType.ERROR) {
                    return null;
                }
                if (!engineNumber.equalsIgnoreCase(vrm.getResponseModel().getEngineNumber())) {
                	log.error("engine number doesn't match!");
                    throw new IllegalEngineNumberException("engine number is not correct");
                }
            } catch (IllegalEngineNumberException e) {
            	e.printStackTrace();
            	log.error(e.getMessage());
                throw new InvalidEngineNumberException("engine number invalid");
            }
            uvm = conversionService.convert(vrm.getResponseModel(), UnregisteredVehicleModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        log.debug("::::::::Is Migration on migration server::::::end:::: " +uvm);
        return ResponseEntity.status(HttpStatus.OK).body(uvm);
        
	}
	
    @RequestMapping(path = "/skip/bsiii/{chassisno}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> skipBsIII(@PathVariable(value = "chassisno") String chassisNumber) {
        Boolean skip = !vehicleService.getIsBharatStageThreeVehicle(chassisNumber);
        Map<String, String> map = new HashMap<>();
        map.put("skip", skip.toString());
        log.info("Skip BS III check for chassis number:" + chassisNumber+ " is "+ skip);
        return ResponseEntity.status(HttpStatus.OK).body(map);
    }
	
    @RequestMapping(value = "/regcategory/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getRTA(@PathVariable("vehicleRcId") Long vehicleRcId) {
        log.info("getRTA()::::=> fetching registration category by vehicle rc id :" + vehicleRcId);
        RegistrationCategoryModel regCat = null;
        if(ObjectsUtil.isNotNull(vehicleRcId)) {
             regCat = registrationCategoryService.getRegistrationCategoryDetails(vehicleRcId);
        }
        return ResponseEntity.ok(regCat);
    }
    
    @RequestMapping(value = "/isvehiclereassignment/applicable/{prNumber}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getIsVehicleReassignmentApplicable(@PathVariable("prNumber") String prNumber) {
        log.info("getRTA()::::=> fetching is Vehicle Reassignment Applicable by prNumber :" + prNumber);
        Boolean isApplicable = false;
        if(ObjectsUtil.isNotNull(prNumber)) {
        	isApplicable = registrationCategoryService.getIsVehicleReassignmentApplicable(prNumber);
        }
        return ResponseEntity.ok(isApplicable);
    }
    
    
	@RequestMapping(value = "/getAlterationCovList/{prNumber}/{regCatCode}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getAlterationCovList(@PathVariable("prNumber") String prNumber,@PathVariable("regCatCode") String regCatCode) {
		log.info("getRTA()::::=> fetching cov for alteration by prNumber :" + prNumber);
		List<VehicleClassDescModel> covList=vehicleService.getAlterationCovList(prNumber,regCatCode);
		return ResponseEntity.ok(covList);
	}
	
	@RequestMapping(value = "/stoppagetax/details/{prnumber}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.PUT)
    public ResponseEntity<?> saveStoppageTaxDetails(@RequestBody List<ApplicationFormDataModel> models, @PathVariable("prnumber") String prNumber,
    		 HttpServletRequest request) {
		SaveUpdateResponse response;
        try {
        	response = stoppageTaxService.saveOrUpdateStoppageTax(models, prNumber, jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        } catch (Exception e) {
        	log.error(":::Getting error in Stoppage Tax Details :::: " + e.getMessage());       	
            response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, e.getMessage(), null);
        }
        log.info(":::saveStoppageTaxDetails::: " + response.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@RequestMapping(value = "/stoppagetax/report/details/{prnumber}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.POST)
    public ResponseEntity<?> saveStoppageTaxReportDetails(@RequestBody StoppageTaxReportModel model, @PathVariable("prnumber") String prNumber,
    		@RequestParam(value="username", required=true) String userName,	HttpServletRequest request) {
		SaveUpdateResponse response;
        try {
        	response = stoppageTaxService.saveStoppageTaxReportDetails(model, prNumber, userName);
        	} catch (Exception e) {
        	log.error(":::Getting error in Stoppage Tax Report Details :::: " + e.getMessage());       	
            response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, e.getMessage(), null);
        }
        log.info(":::saveStoppageTaxReportDetails::: " + response.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@RequestMapping(value = "/stoppagetax/details/{prnumber}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.GET)
    public ResponseEntity<?> getStoppageTaxDetails(@PathVariable("prnumber") String prNumber) {
		StoppageTaxDetailsModel result = null;
        try {
        	result = stoppageTaxService.getStoppageApplication(prNumber);
        	} catch (Exception e) {
        	log.error(":::Getting error in Stoppage Tax Details :::: " + e.getMessage());       	
        	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if(ObjectsUtil.isNull(result)){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info(":::getStoppageTaxDetails::: " );
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
	
	@RequestMapping(value = "/stoppagetax/report/details/{applicationno}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.GET)
    public ResponseEntity<?> getStoppageTaxReportDetails(@PathVariable("applicationno") String applicationNo) {
		List<StoppageTaxReportModel> results = null;
        try {
        	results = stoppageTaxService.getStoppageInspections(applicationNo);
        	} catch (Exception e) {
        	log.error(":::Getting error in Stoppage Tax Report Details :::: " + e.getMessage());       	
        	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if(ObjectsUtil.isNull(results) || results.size() == 0){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info(":::getStoppageTaxReportDetails::: " );
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
	
	@RequestMapping(value = "/stoppagetax/single/report/details/{reportid}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.GET)
    public ResponseEntity<?> getStoppageTaxSingleReportDetails(@PathVariable("reportid") Long stoppageReportId) {
		StoppageTaxReportModel result = null;
        try {
        	result = stoppageTaxService.getStoppageInspection(stoppageReportId);
        	} catch (Exception e) {
        	log.error(":::Getting error in Stoppage Tax Single Report Details :::: " + e.getMessage());       	
        	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if(ObjectsUtil.isNull(result)){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info(":::getStoppageTaxSingleReportDetails::: " );
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
	
	@RequestMapping(value = "/bodytypelist", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getBodyTypeList() {
		List<String> bodyTypeList = vehicleService.getBodyTypeList();
		return new ResponseEntity<List<String>>(bodyTypeList, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getseatingcovvalidation/{cov}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.GET)
    public ResponseEntity<?> getSeatingCovValidationValue(@PathVariable("cov") String cov) {
		SeatingCovValueModel results = null;
        try {
        	results = vehicleDetailsService.getSeatingCovValidateValues(cov);
        	} catch (Exception e) {
        	log.error(":::Getting error in Seating Cov Validation Value :::: " + e.getMessage());       	
        	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        if(ObjectsUtil.isNull(results)){
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info(":::SeatingCovValidationValue::: " );
        return ResponseEntity.status(HttpStatus.OK).body(results);
    }
}
