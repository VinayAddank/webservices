package org.rta.registration.controller;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.rta.core.enums.UserType;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.master.AddressProofTypeModel;
import org.rta.core.model.master.CountryModel;
import org.rta.core.model.master.DesignationModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.model.master.InsuranceTypeModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.OwnershipModel;
import org.rta.core.model.master.PermitTypeModel;
import org.rta.core.model.master.PostOfficeModel;
import org.rta.core.model.master.QualificationModel;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.docs.AttachmentDetailsService;
import org.rta.core.service.docs.DocPermissionService;
import org.rta.core.service.master.AddressProofTypeService;
import org.rta.core.service.master.CountryService;
import org.rta.core.service.master.DesignationService;
import org.rta.core.service.master.DistrictService;
import org.rta.core.service.master.InsuranceTypeService;
import org.rta.core.service.master.MandalService;
import org.rta.core.service.master.OwnershipService;
import org.rta.core.service.master.PermitTypeService;
import org.rta.core.service.master.PostOfficeService;
import org.rta.core.service.master.QualificationService;
import org.rta.core.service.master.RegistrationCategoryService;
import org.rta.core.service.master.StateService;
import org.rta.core.service.master.TaxTypeService;
import org.rta.core.service.office.RTAOfficeService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
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

/**
 * @Author sohan.maurya created on Jul 12, 2016.
 */
@RestController
public class MasterController {

	private static final Logger log = Logger.getLogger(MasterController.class);
	
	@Value("${jwt.claim.secret}")
	private String claimSecret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private InsuranceTypeService insuranceTypeService;

	@Autowired
	private AddressProofTypeService addressProofTypeService;

	@Autowired
	private OwnershipService ownershipService;

	@Autowired
	private TaxTypeService taxTypeService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private StateService stateService;

	@Autowired
	MandalService mandalService;

	@Autowired
	PostOfficeService postOfficeService;

	@Autowired
	QualificationService qualificationService;

	@Autowired
	CountryService countryService;

	@Autowired
	RegistrationCategoryService registrationCategoryService;

	@Autowired
	DesignationService designationService;

	@Autowired
	private DocPermissionService docPermissionService;

    @Autowired
    private AttachmentDetailsService attachmentDetailsService;
    
    @Autowired
    private PermitTypeService permitTypeService;
    
    @Autowired
    private RTAOfficeService rtaOfficeService;

	@RequestMapping(path = "/insurancetype", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<InsuranceTypeModel> getInsuranceTypes() {
		List<InsuranceTypeModel> insuranceModels = insuranceTypeService.getAll();
		log.debug("Insurance Type " + insuranceModels);
		return insuranceModels;
	}

	@RequestMapping(path = "/addresstype", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<AddressProofTypeModel> getAddressProofTypes() {
		List<AddressProofTypeModel> addressModels = addressProofTypeService.getAll();
		log.debug(" Address Proof Type " + addressModels);
		return addressModels;
	}

	@RequestMapping(path = "/ownershiptype", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<OwnershipModel> getOwnershipTypes() {
		List<OwnershipModel> ownershipModels = ownershipService.getAll();
		log.debug("ownership : " + ownershipModels);
		return ownershipModels;
	}

	@RequestMapping(path = "/taxtype", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<TaxTypeModel> getTaxTypes() {
		List<TaxTypeModel> taxTypModels = taxTypeService.getAll();
		log.debug("taxtype : " + taxTypModels);
		return taxTypModels;
	}

	@RequestMapping(path = "/district", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<DistrictModel> getDistrict() {
		List<DistrictModel> districtModels = districtService.getAll();
		return districtModels;
	}
	
	@RequestMapping(path = "/neighbouringdistrict/{prNumber}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getNeighbouringDistrict(@PathVariable("prNumber") String prNumber) {
		log.debug("<<<<<<<<<<<<neighbouring district start>>>>>>>>>>>>>");
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		List<DistrictModel> districtModels;
		try {
			districtModels = districtService.getAllNeighbouring(prNumber);
		} catch (Exception e) {
		    errorModel.setMessage("Invalid PR Number !!!");
		    return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(districtModels);
	}
	
	@RequestMapping(path = "/neighbouringstate/{prNumber}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getNeighbouringState(@PathVariable("prNumber") String prNumber) {
		log.debug("<<<<<<<<<<<<neighbouring state start>>>>>>>>>>>>>");
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		List<StateModel> stateModels;
		try {
			stateModels = stateService.getAllNeighbouring(prNumber);
		} catch (Exception e) {
		    errorModel.setMessage("Invalid PR Number !!!");
		    return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(stateModels);
	}

	@RequestMapping(path = "/state", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<StateModel> getState() {
		log.debug("<<<<<<<<<<<<state start>>>>>>>>>>>>>");
		List<StateModel> stateModels = stateService.getAll();
		return stateModels;
	}

	@RequestMapping(value = "/mandals", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getMandals() {
		log.debug("getMandals()::::=> fetching list of mandals");
		List<MandalModel> mandalList = mandalService.getAll();
		if (mandalList == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(mandalList);
	}

	@RequestMapping(value = "/mandal/{code}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getByMandalName(@PathVariable("code") Integer mandalCode) {
		log.debug("getByMandalName()::::=> fetching mandal by code : " + mandalCode);
		MandalModel mandal = mandalService.getByMandalCode(mandalCode);
		if (mandal == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(mandal);
	}

	@RequestMapping(value = "/postoffices", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getPostOffices() {
		log.debug("getPostOffices()::::=> fetching list of postoffices");
		List<PostOfficeModel> postOfficeList = postOfficeService.getAll();
		if (postOfficeList == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(postOfficeList);
	}

	@RequestMapping(value = "/postoffice/{code}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getByPinCode(@PathVariable("code") String pinCode) {
		log.debug("getByPinCode()::::=> fetching postoffice by pincode : " + pinCode);
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (pinCode.length() != 6) {
			errorModel.setMessage("Pin Code must be 6 digits !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		try {
			Integer.parseInt(pinCode);
		} catch (NumberFormatException ex) {
			errorModel.setMessage("Pin Code must be numeric !");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		PostOfficeModel postOffice = postOfficeService.getByPinCode(pinCode);
		if (postOffice == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(postOffice);
	}

	@RequestMapping(path = "/qualification", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<QualificationModel> getQualification() {
		log.debug("<<<<<<<<<<<<qualification start>>>>>>>>>>>>>");
		List<QualificationModel> qualificationModels = qualificationService.getAll();
		return qualificationModels;
	}

	@RequestMapping(path = "/country", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<CountryModel> getCountry() {
		log.debug("<<<<<<<<<<<<Country start>>>>>>>>>>>>>");
		List<CountryModel> countryModels = countryService.getAll();
		return countryModels;
	}

	@RequestMapping(path = "/registcategory", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<RegistrationCategoryModel> getRegistrationCategory() {
		log.debug("<<<<<<<<<<<<RegistrationCategory start>>>>>>>>>>>>>");
		List<RegistrationCategoryModel> registrationCategorys = registrationCategoryService.getAll();
		return registrationCategorys;
	}

	@RequestMapping(path = "/designation", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<DesignationModel> getDesignation() {
		log.debug("<<<<<<<<<<<<DesignationModel start>>>>>>>>>>>>>");
		List<DesignationModel> designationModels = designationService.getAll();
		return designationModels;
	}

	@RequestMapping(path = "/uploadtype/{vehiclercid}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getUploadType(@PathVariable("vehiclercid") String vehicleRcId,
    		@RequestParam(name="isoldvehicle", required=false) boolean isOldVehicle,
            HttpServletRequest request) {
		log.debug("<<<<<<<<<<<<UploadType start>>>>>>>>>>>>>");
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcId, claimSecret);
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		Long vehicleId = null;
		try {
			vehicleId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException ex) {
			log.error("getUploadType :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
			errorModel.setMessage("Invalid vehicleRcId!");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
        String token = request.getHeader(tokenHeader);
        UserModel userModel = new UserModel();
        userModel.setUserId(jwtTokenUtil.getUserIdFromToken(token));
        userModel.setUserType(UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0)));
        userModel.setUserName(jwtTokenUtil.getUsernameFromToken(token));
        Boolean isValidUpload = attachmentDetailsService.isValidUpload(vehicleId, userModel.getUserType(),isOldVehicle);
        if (!ObjectsUtil.isNull(isValidUpload) && isValidUpload) {
            List<DocTypesModel> docTypesModels = null;
            try {
                docTypesModels = docPermissionService.getDocTypes(vehicleId, userModel, isOldVehicle);
            } catch(IllegalArgumentException ie){
                log.error(ie.getMessage());
                errorModel.setMessage(ie.getMessage());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            } catch (HibernateException ex) {
                log.error("getUploadType Attachment documents :: " + ex.getMessage());
                errorModel.setMessage("Some Error Occured !!!");
    			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            log.debug(" Get Docs List >>>>>>>>>>>No. of documents=" + docTypesModels.size());
        return ResponseEntity.ok(docTypesModels);
        } else {
            log.error("docs already uploaded vehicleRcId : " + vehicleId);
            errorModel.setMessage("Documents can not be Uploaded (Already uploaded).");
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
	}

	@RequestMapping(path = "/payment/sbi/result", method = RequestMethod.POST)
	public String getSBIRequest(@RequestBody String sbiResponseModel) {
		log.debug("<<<<<<<SBI RESPONSE>>>>>>>>>" + sbiResponseModel);
		// String res = sbiService.validateNdsaveSBIData(sbiResponseModel);
		return sbiResponseModel;
	}

	@RequestMapping(value = "/districts/state/{code}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getDistrictByStateCode(@PathVariable("code") String StateCode) {
		log.debug("getDistrictByStateCode()::::=> fetching list of district by state code:" + StateCode);
		List<DistrictModel> districtList = districtService.getDistrictsByStateCode(StateCode);
		if (districtList == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(districtList);
	}

	@RequestMapping(value = "/mandals/district/{code}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getMandalsByDistrictCode(@PathVariable("code") String code) {
		log.debug("getMandalsByDistrictCode()::::=> fetching list of mandals by distric code :" + code);
		List<MandalModel> mandalList = mandalService.getByDistrictCode(code);
		if (mandalList == null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(mandalList);
	}

    @RequestMapping(value = "/rta/mandal/{code}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getRTA(@PathVariable("code") Integer code,
            @RequestParam(name = "regCategory", required = false) String registrationCategory,
            @RequestParam(name = "vehiclercid", required = false) Long vehicleRcId) {
        log.info("getRTA()::::=> fetching RTA by mandal code :" + code);
        if(ObjectsUtil.isNull(registrationCategory) && ObjectsUtil.isNotNull(vehicleRcId)) {
            RegistrationCategoryModel regCat = registrationCategoryService.getRegistrationCategoryDetails(vehicleRcId);
            registrationCategory = regCat.getCode();
        }
        RTAOfficeModel rtOffice = mandalService.getRTAOfficeByMandalCode(code, registrationCategory);
        if (rtOffice == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rtOffice);
    }
	
	@RequestMapping(path = "/permittypes", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getPermitTypes() {
        log.info("<<<<<<<<<<<<Get PermitTypes All>>>>>>>>>>>>>");
        List<PermitTypeModel> permitTypeModels = permitTypeService.getPermitType();
        log.info("<<<<<<<<<<PermitTypeModels end>>>>>>>>>>> " + permitTypeModels.size());
        return ResponseEntity.ok(permitTypeModels);
    }
	
    @RequestMapping(path = "/permittype/{vehicleclasscode}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getPermitTypes(@PathVariable("vehicleclasscode") String vehicleClassDescCode) {
		log.info("<<<<<<<<<<<<PermitTypes start>>>>>>>>>>>>>");
        if (StringsUtil.isNullOrEmpty(vehicleClassDescCode)) {
            throw new IllegalArgumentException(" Vehicle Class Description Code is null ");
        }
        List<PermitTypeModel> permitTypeModels = permitTypeService.getPermitType(vehicleClassDescCode);
        log.info("<<<<<<<<<<PermitTypeModels end>>>>>>>>>>> " + permitTypeModels.size());
        return ResponseEntity.ok(permitTypeModels);
	}
    
    @RequestMapping(value = "/rtaoffices", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public List<RTAOfficeModel> getRtaOfficeList(@RequestParam(name = "state", required = false) String stateCode,
	        @RequestParam(name = "showunit", required = false, defaultValue = "false") Boolean showUnit) {
		log.info("<<<<<<<<<<<<RTAOfficeListModel start>>>>>>>>>>>>>");
		List<RTAOfficeModel> rtaOfficeModel = null;
		if (!StringsUtil.isNullOrEmpty(stateCode)) {
		    rtaOfficeModel = rtaOfficeService.getRtaOfficeListByState(stateCode, true, showUnit);
		} else {
		    rtaOfficeModel = rtaOfficeService.getRtaOfficeList(true);
		}
        log.info("<<<<<<<<<< RTAOfficeListModel end>>>>>>>>>>>");
        return rtaOfficeModel;
    }
    
    @RequestMapping(value = "/rtaoffices/{code}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public RTAOfficeModel getRtaOffice(@PathVariable(value = "code") String code) {
        RTAOfficeModel rtaOfficeModel = rtaOfficeService.getRTAOffice(code);
        return rtaOfficeModel;
    }

	@RequestMapping(value = "/rta/{port}/{email}/{pass}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public void getTest(@PathVariable("port") Integer port, @PathVariable("email") String email,
			@PathVariable("pass") String pass) {
		log.debug("getRTA()::::=> fetching RTA by mandal code :" + port);

		String msgstatus = "Initial";
		MimeMessage msg;
		Transport tr;
		try {
			Properties props = getProperties(port);
			Session session = Session.getInstance(props, null);
			msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("sandeepknit89@gmail.com"));
			msg.setSubject("test");
			Multipart multipart = new MimeMultipart();
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("test", "text/html");
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			// msg.setContent(model.getMailContent(),
			// config.getMailContentType());
			tr = session.getTransport("smtp");
			tr.connect("smtp.gmail.com", email, pass);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();

		} catch (Exception e) {
			log.debug(e);
			msgstatus += e.toString();
			throw new IllegalArgumentException();
		}
		log.debug("::::::::end:::::");

	}

	private static Properties getProperties(int port) {
		Properties props = new Properties();
		String smtpHost = "smtp.gmail.com";
		String smtpPort = String.valueOf(port);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", "true");
		// props.put("mail.debug", "true");
		props.put("mail.smtp.starttls.enable", "true");
		return props;
	}

}
