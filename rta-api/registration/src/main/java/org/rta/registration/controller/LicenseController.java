package org.rta.registration.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.enums.ServiceType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.license.ForgetLicenseModel;
import org.rta.core.model.license.LearnersPermitDtlModel;
import org.rta.core.model.license.LicenseHolderDtlsModel;
import org.rta.core.model.license.LicenseHolderPermitDetails;
import org.rta.core.model.license.LicenseIdpDtlModel;
import org.rta.core.model.license.LicenseVehicleClassRefModel;
import org.rta.core.model.license.SupensionCancellationModel;
import org.rta.core.model.license.SuspensionRevocationModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.service.license.DriversLicenceDetailsService;
import org.rta.core.service.license.LearnersPermitDtlService;
import org.rta.core.service.license.LicenseHolderDtlsService;
import org.rta.core.service.license.LicenseVehicleClassRefService;
import org.rta.core.service.office.RTAOfficeService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LicenseController {

	private static final Logger logger = Logger.getLogger(LicenseController.class);

	@Value("${jwt.header}")
	private String tokenHeader;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private LicenseHolderDtlsService licenseHolderDtlsService;

	@Autowired
	private LicenseVehicleClassRefService licensevehicleclassrefservice;

	@Autowired
	private LearnersPermitDtlService learnersPermitDtlService;

	@Autowired
	private DriversLicenceDetailsService driversLicenceDetailsService;

	@Autowired
	private RTAOfficeService rtaOfficeService;

	@RequestMapping(value = "/learner/license/details", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getLicenseHolderDtls(@RequestParam(value = "aadharno", required = false) String aadharNo,
			@RequestParam(value = "licenceholderid", required = false) Long licenceHolderId,
			@RequestParam(value = "uniquekey", required = false) String uniqueKey) {

		if (StringsUtil.isNullOrEmpty(aadharNo) && StringsUtil.isNullOrEmpty(uniqueKey)
				&& ObjectsUtil.isNull(licenceHolderId)) {

			return ResponseEntity.badRequest().build();
		}
		LicenseHolderPermitDetails model = null;
		try {
			model = licenseHolderDtlsService.getLicenseHolderDtls(aadharNo, licenceHolderId, uniqueKey);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.noContent().build();
		}
		return new ResponseEntity<LicenseHolderPermitDetails>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/learner/license/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveLicenseHolderDtls(@Valid @RequestBody LicenseHolderDtlsModel model,
			HttpServletRequest request) {

		if (ObjectsUtil.isNull(model)) {
			return ResponseEntity.badRequest().build();
		}
		SaveUpdateResponse response = null;
		try {
			response = licenseHolderDtlsService.saveLicenseHolderDtls(model,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/learner/license/permit/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveLicenseHolderDtls(@Valid @RequestBody LicenseHolderPermitDetails models,
			HttpServletRequest request) {
		if (ObjectsUtil.isNull(models)) {
			return ResponseEntity.badRequest().build();
		}
		SaveUpdateResponse response = null;
		try {
			response = licenseHolderDtlsService.saveOnlyLicenseHolderDtlsAndPermitDetails(models,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/license/vehicleclass/list/{aadharno}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getSelectedVehicleClass(@PathVariable("aadharno") String aadharNumber,
			@RequestParam(value = "show", required = false) String showVehicleClass) {
		if (StringsUtil.isNullOrEmpty(aadharNumber)) {
			return ResponseEntity.badRequest().build();
		}
		List<String> response = null;
		try {
			response = licenseHolderDtlsService.getLicensePermitVehicle(aadharNumber, showVehicleClass);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<List<String>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/license/vehicleclass/details", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getVehicleClassDtls(@RequestParam(value = "vehicleclasscode") String vehicleClassCode) {

		if (StringsUtil.isNullOrEmpty(vehicleClassCode)) {
			return ResponseEntity.badRequest().build();
		}
		LicenseVehicleClassRefModel response = null;
		try {
			response = licensevehicleclassrefservice.getLicenseVehicleClassRef(vehicleClassCode);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<LicenseVehicleClassRefModel>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/learner/permit/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveLearnerPermitDetails(@Valid @RequestBody List<LearnersPermitDtlModel> models,
			@RequestParam(value = "aadharno", required = true) String aadharNo, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(aadharNo) || ObjectsUtil.isNull(models)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			if (ServiceType.LL_ENDORSEMENT.getCode().equalsIgnoreCase(models.get(0).getLlrNoType())) {
				response = learnersPermitDtlService.saveLearnersPermitDetail(models, aadharNo,
						jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
			} else {
				response = learnersPermitDtlService.updateLearnersPermitDetail(models, aadharNo,
						jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
			}

		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/details", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getLicenseHolderDtlsForDriver(
			@RequestParam(value = "aadharno", required = false) String aadharNo,
			@RequestParam(value = "uniquekey", required = false) String uniqueKey) {

		if (StringsUtil.isNullOrEmpty(aadharNo) && StringsUtil.isNullOrEmpty(uniqueKey)) {

			return ResponseEntity.badRequest().build();
		}
		LicenseHolderPermitDetails model = null;
		try {
			model = licenseHolderDtlsService.getDriverLicenseHolderDetails(aadharNo, uniqueKey);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<LicenseHolderPermitDetails>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/details/{uniquekey}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getLicenseHolderDtlsForDriver(@PathVariable(value = "uniquekey") String uniqueKey) {

		if (StringsUtil.isNullOrEmpty(uniqueKey)) {

			return ResponseEntity.badRequest().build();
		}
		LicenseHolderPermitDetails model = null;
		try {
			model = licenseHolderDtlsService.getDriverLicenseHolderDetails(uniqueKey);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<LicenseHolderPermitDetails>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveDriverPermitDetails(@Valid @RequestBody LicenseHolderPermitDetails model,
			@RequestParam(value = "aadharno", required = true) String aadharNo, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(aadharNo) || ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.saveDriverPermitDetails(model, aadharNo,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/license/holder/details", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateLicenseHodlerDetails(@Valid @RequestBody LicenseHolderDtlsModel model,
			HttpServletRequest request) {
		if (ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = licenseHolderDtlsService.updateLicenseHodlerDetails(model,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/license/holder/details", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getLicenseHodlerDetails(
			@RequestParam(value = "aadharno", required = false) String aadhaarNumber,
			@RequestParam(value = "passportnumber", required = false) String passportNumber) {

		if (StringsUtil.isNullOrEmpty(aadhaarNumber) && StringsUtil.isNullOrEmpty(passportNumber)) {

			return ResponseEntity.badRequest().build();
		}
		LicenseHolderDtlsModel model = null;
		try {
			model = licenseHolderDtlsService.getLicenseHolderDetails(aadhaarNumber, passportNumber);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<LicenseHolderDtlsModel>(model, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/endorsment/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> aadDriverPermitDetails(@Valid @RequestBody LicenseHolderPermitDetails model,
			HttpServletRequest request) {
		if (ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.addDriverPermitDetails(model,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/retest/details", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> addDriverPermitDetails(@Valid @RequestBody List<DriversLicenceDetailsModel> models,
			@RequestParam(value = "aadharno", required = true) String aadharNo, HttpServletRequest request) {
		if (ObjectsUtil.isNull(models) || StringsUtil.isNullOrEmpty(aadharNo)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.addDriverPermitDetails(models, aadharNo,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/rtaoffice/details", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getRtaOfficeDetails() {
		List<RTAOfficeModel> response = null;
		try {
			response = rtaOfficeService.getRtaOfficeInfo();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return new ResponseEntity<List<RTAOfficeModel>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/dl/common/service/details", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateDriverLicenceCommonService(
			@Valid @RequestBody List<DriversLicenceDetailsModel> models,
			@RequestParam(value = "aadharno", required = true) String aadharNumber, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(aadharNumber) || ObjectsUtil.isNull(models)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.updateDriverLicenceCommonService(aadharNumber, models,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/licence/holder/details", method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> updateDriverLicenceCommonService(@Valid @RequestBody LicenseHolderDtlsModel model,
			@RequestParam(value = "servicecode", required = true) String serviceCode, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(serviceCode) || ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		String response = null;
		try {
			response = licenseHolderDtlsService.updateInLicenceHodlerDetails(model,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)), serviceCode);
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgot/licence", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> forgotLicenceNumber(@RequestBody ForgetLicenseModel forgetLicenseModel) {
		if (ObjectsUtil.isNull(forgetLicenseModel.getDob())
				|| StringsUtil.isNullOrEmpty(forgetLicenseModel.getAadharNumber())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LicenseHolderPermitDetails response = null;
		try {
			response = licenseHolderDtlsService.forgotLicenceNumber(forgetLicenseModel);
			// communicationService.sendMsg(CommunicationServiceImpl.SEND_SMS,
			// response);
		} catch (NotFoundException e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<LicenseHolderPermitDetails>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/idpdetails", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> saveInternationalDriverPermitDetails(@Valid @RequestBody LicenseIdpDtlModel model,
			@RequestParam(value = "aadharno", required = true) String aadharNo, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(aadharNo) || ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.saveInternationalDriverPermitDetails(model, aadharNo,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/suscancel", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> suspendCancelLicense(@Valid @RequestBody SupensionCancellationModel model,
			@RequestParam(value = "dlnumber", required = true) String dlnumber, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(dlnumber) || ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.suspendCancelLicense(model, dlnumber,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/driver/license/revokesusp", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> licenseRevokeSuspension(@Valid @RequestBody SuspensionRevocationModel model,
			@RequestParam(value = "dlnumber", required = true) String dlnumber, HttpServletRequest request) {
		if (StringsUtil.isNullOrEmpty(dlnumber) || ObjectsUtil.isNull(model)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		SaveUpdateResponse response = null;
		try {
			response = driversLicenceDetailsService.licenseRevokeSuspension(model, dlnumber,
					jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
		} catch (Exception e) {
			logger.error("Getting error in request " + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
		}
		return new ResponseEntity<SaveUpdateResponse>(response, HttpStatus.OK);
	}

}
