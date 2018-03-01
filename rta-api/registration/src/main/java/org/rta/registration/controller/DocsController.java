package org.rta.registration.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.hibernate.StaleObjectStateException;
import org.rta.core.enums.AttachmentType;
import org.rta.core.enums.NotificationType;
import org.rta.core.enums.UserType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.UploadDocumentsResponse;
import org.rta.core.model.customer.CustMsgModel;
import org.rta.core.model.docs.AttachmentDetailsModel;
import org.rta.core.model.export.FormsModel;
import org.rta.core.model.master.DocTypesModel;
import org.rta.core.service.docs.AttachmentDetailsService;
import org.rta.core.service.docs.DocPermissionService;
import org.rta.core.service.export.ExportDataService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.CommunicationService;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * this controller use for all documents related service
 */
@RestController
public class DocsController {

    private static final Logger logger = Logger.getLogger(DocsController.class);

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${doc.root}")
    private String rootPath;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${rta.step.docs}")
    private Integer currentStep;

    @Autowired
    private AttachmentDetailsService attachmentDetailsService;
    
    @Autowired
    private CommunicationService communicationService;
    
    @Autowired
    private ExportDataService exportDataService;

    @Autowired
    private DocPermissionService docPermissionService;

	public final static short SEND_SMS_EMAIL = 0;
	public final static short SEND_SMS = 1;
	public final static short SEND_EMAIL = 2;


    
    @RequestMapping(value = "docsurls/{chassisNumber}/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllDocsUrls(@PathVariable("chassisNumber") String chassisNumber, @PathVariable("vehiclercid") String vehicleRcId, 
            @RequestParam(name = "isoldvehicle", required = false) boolean isOldVehicle, HttpServletRequest request) {

        if (StringsUtil.isNullOrEmpty(chassisNumber) || StringsUtil.isNullOrEmpty(vehicleRcId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String vehicleRcIdDecrypted =
                RtaCryptoUtil.parseSecureToken(vehicleRcId, claimSecret);
        Long vehicleRcIdLong = null;
        try {
            vehicleRcIdLong = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            logger.error("getAllDocsUrls :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<AttachmentDetailsModel> attachmentDetailsModels = null;
        if(isOldVehicle){
            try{
                attachmentDetailsModels = attachmentDetailsService
                        .getAllVehicleAlterationAttachmentDetails(chassisNumber, vehicleRcIdLong);
            } catch (NotFoundException ex) {
                logger.error("getAllDocsUrls :: Invalid VehicleRcId isoldvehicle=>" + vehicleRcIdDecrypted);
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
            }
        }else{
            attachmentDetailsModels = attachmentDetailsService.getAllAttachments(chassisNumber, vehicleRcIdLong,
                        UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(request.getHeader(tokenHeader)).get(0)));
        }
        logger.info("Attachment Documents Urls " + attachmentDetailsModels.size());
		return ResponseEntity.ok(attachmentDetailsModels);
	}

	@RequestMapping(value = "docsurl", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> docsUrl(@Valid @RequestBody AttachmentDetailsModel attachmentDetailsModel,
			@RequestParam(name = "isoldvehicle", required = false) boolean isOldVehicle, HttpServletRequest request) {

	    SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		if (ObjectsUtil.isNull(attachmentDetailsModel)) {
		    errorModel.setMessage("Attachment Details Requiered !!!");
		    return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(attachmentDetailsModel.getVehicleRcId(),
				claimSecret);
		Long vehicleRcId = null;
		try {
			vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
		} catch (NumberFormatException ex) {
			logger.error("getInsuranceDetails :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
			errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}

		SaveUpdateResponse saveUpdateModel = null;
		try {
			String token = request.getHeader(tokenHeader);
			String userName = jwtTokenUtil.getUsernameFromToken(token);
	        UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
			attachmentDetailsModel.setVehicleRcId(String.valueOf(vehicleRcId));
			if(isOldVehicle){
				saveUpdateModel = attachmentDetailsService.saveOrUpdateforVehicleAlteration(attachmentDetailsModel, userName, userType);
			}else{
				saveUpdateModel = attachmentDetailsService.saveOrUpdate(attachmentDetailsModel, userName, userType);
			}
			saveUpdateModel.setVehicleRcId(
					RtaCryptoUtil.generateSecureToken(String.valueOf(saveUpdateModel.getVehicleRcId()), claimSecret));
		}catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch(IllegalArgumentException ie){
		    errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (DataIntegrityViolationException ex) {
			logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
			errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		} catch (InvalidDataAccessApiUsageException ex) {
			logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
			errorModel.setMessage("Invalid Data Received !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		return ResponseEntity.ok(saveUpdateModel);
	}

    @RequestMapping(value = "multipledocs", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> saveMultipledocUrls( @Valid @RequestBody List<AttachmentDetailsModel> attachmentDetailsModels,
    		@RequestParam(name = "isoldvehicle", required = false) boolean isOldVehicle, HttpServletRequest request) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(attachmentDetailsModels)) {
            errorModel.setMessage("Attachment Details Requiered !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        SaveUpdateResponse saveUpdateModel = null;
        try {
            String token = request.getHeader(tokenHeader);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
            if(isOldVehicle){
            	 saveUpdateModel = attachmentDetailsService.multipleSaveOrUpdateForVehicleAlteration(attachmentDetailsModels, userName, userType);
            }else{
            	saveUpdateModel = attachmentDetailsService.multipleSaveOrUpdate(attachmentDetailsModels, userName, userType);
            }
            saveUpdateModel = attachmentDetailsService.multipleSaveOrUpdate(attachmentDetailsModels, userName, userType);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);    
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        return ResponseEntity.ok(saveUpdateModel);
    }

    @RequestMapping(value = "attachcomplete/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> saveCompleteAttachments(@PathVariable("vehiclercid") String vehicleRcId,
            HttpServletRequest request) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleRcId)) {
            errorModel.setMessage("VehicleRcId Not Found !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcId, claimSecret);
        Long vehicleRcIdLong = null;
        try {
            vehicleRcIdLong = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            logger.error("getInsuranceDetails :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }

        SaveUpdateResponse saveUpdateModel = null;
        try {
            String token = request.getHeader(tokenHeader);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
            saveUpdateModel =
                    attachmentDetailsService.saveCompleteAttachments(vehicleRcIdLong, userName, currentStep, userType);
            saveUpdateModel.setVehicleRcId(
                    RtaCryptoUtil.generateSecureToken(String.valueOf(saveUpdateModel.getVehicleRcId()), claimSecret));
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);    
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (NullPointerException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        logger.debug("Attachment Document process Succsssfully completed " + vehicleRcIdLong);
        return ResponseEntity.ok(saveUpdateModel);
    }

    @RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<?> uploadMultipleFileHandler(HttpServletRequest request,
            @RequestParam("doctype") String doc_type, @RequestParam("vehiclercid") String vehicleRcId,
            @RequestParam("file") MultipartFile file, @RequestParam(name="isoldvehicle", required=false) boolean isOldVehicle) {
        if(ObjectsUtil.isNull(vehicleRcId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                    "Vehicle Rc Not found.", ""));
        }
        Boolean isValidUpload = attachmentDetailsService.isValidUpload(Long.valueOf(vehicleRcId),
                UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(request.getHeader(tokenHeader)).get(0)), isOldVehicle);

        if(!ObjectsUtil.isNull(isValidUpload) && isValidUpload){
            String filePath = "/rta/docs/" + vehicleRcId + File.separator + doc_type;
            String name = file.getOriginalFilename();
            File dir = new File(rootPath + filePath);
            if (!dir.exists())
                dir.mkdirs();
            
            logger.debug("Uploading document :" + name);
            try {
                byte[] bytes = file.getBytes();
                
                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath() + File.separator + name.replace(" ", ""));
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
                logger.debug("Doc upload completed, Location:" + serverFile.getAbsolutePath());
            } catch (Exception e) {
                logger.error("Exception in uploadMultipleFileHandler : " + e.getMessage());
                logger.debug(e);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                        "Documents can not be Uploaded.", ""));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new UploadDocumentsResponse(HttpStatus.OK.toString(),
                    "Documents Uploaded successfully", filePath + File.separator + name.replace(" ", "")));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                    "Documents can not be Uploaded.", ""));
        }
    }

    @RequestMapping(value = "/multifileupload/{vehicleRcId}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> multiFileUpload(MultipartHttpServletRequest request, @PathVariable("vehicleRcId") Long vehicleRcId,
    		@RequestParam(name="isoldvehicle", required=false) boolean isOldVehicle){
        MultiValueMap<String,MultipartFile> map = request.getMultiFileMap();
        Map<String, UploadDocumentsResponse> resMap = new HashMap<String, UploadDocumentsResponse>();
        if(ObjectsUtil.isNull(vehicleRcId)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                    "Vehicle Rc Not found.", ""));
        }
       
        Boolean isValidUpload = attachmentDetailsService.isValidUpload(Long.valueOf(vehicleRcId),
                UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(request.getHeader(tokenHeader)).get(0)),isOldVehicle);
        for(Entry<String, List<MultipartFile>> entry : map.entrySet()){
            if(!ObjectsUtil.isNull(isValidUpload) && isValidUpload){
                String filePath = File.separator + "rta" + File.separator + "docs" + File.separator + vehicleRcId + File.separator + entry.getKey();
                String name = ((MultipartFile) entry.getValue().get(0)).getOriginalFilename();
                File dir = new File(rootPath + filePath);
                if (!dir.exists())
                    dir.mkdirs();
                try {
                    byte[] bytes = ((MultipartFile) entry.getValue().get(0)).getBytes();
                    if((name.toLowerCase().endsWith(AttachmentType.JPG.getLabel()) || name.toLowerCase().endsWith(AttachmentType.JPEG.getLabel()) || 
                    		name.toLowerCase().endsWith(AttachmentType.PNG.getLabel()) || name.toLowerCase().endsWith(AttachmentType.PDF.getLabel())) && !(name.split("\\.").length > 2)) {
                    	// Create the file on server
                    	File serverFile = new File(dir.getAbsolutePath() + File.separator + name.replace(" ", ""));
                    	BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    	stream.write(bytes);
                    	stream.close();
                    	resMap.put(entry.getKey(), new UploadDocumentsResponse(UploadDocumentsResponse.SUCCESS,
                    			"Document Uploaded successfully", filePath + File.separator + name.replace(" ", "")));
                    	logger.info("Doc : " + entry.getKey() + ", Uploaded. Location:" + serverFile.getAbsolutePath());
                    } else {
                    	logger.info(" Unsupported File format ");
                    }
                } catch (Exception e) {
                    logger.error("Exception in uploadMultipleFileHandler : " + e.getMessage());
                    logger.debug(e);
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                            "Documents can not be Uploaded.", ""));
                }
            } else {
                resMap.put(entry.getKey(), new UploadDocumentsResponse(UploadDocumentsResponse.FAILURE,
                        "Documents can not be Uploaded.", ""));
            }
        }
        return ResponseEntity.ok(resMap);
    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "docs/uploadconsent/{vehicleRcId}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateDocUploadConsent(@PathVariable("vehicleRcId") Long vehicleRcId){
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if(ObjectsUtil.isNull(vehicleRcId)){
            errorModel.setMessage("Invalid VehicleRcId, value is null");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        SaveUpdateResponse response = null;
        try{
            response = attachmentDetailsService.saveUploadConsent(vehicleRcId);
        }catch (StaleObjectStateException | HibernateOptimisticLockingFailureException e ) {
        	logger.error("OPTIMISTIC ERROR :: " + e.getMessage());
        	errorModel.setMessage("OPTIMISTIC ERROR :: Parameter(s) violating Some Data Integrity !");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);    
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        try{
            CustMsgModel msgModel=communicationService.setCustModelForNotificationType(vehicleRcId, NotificationType.NOTIFICATION_FOR_UPLOAD_CONSENT);
            boolean notificationSend=communicationService.sendMsg(SEND_SMS, msgModel);
        } catch(Exception ex){
            logger.error("Exception while communication !!!!!!");
            logger.debug(ex);
        }
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "docs/isdealerandownersign/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getIsAttatchDealerSignatureStatus(@PathVariable("vehicleRcId") Long vehicleRcId) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleRcId)) {
            errorModel.setMessage("Invalid VehicleRcId, value is null");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("isDealerAndOwnerSign", attachmentDetailsService.getIsAttatchDealerAndOwnerSignature(vehicleRcId));
        logger.debug("Attachment check Dealer and owner Signature : " + map.get("isDealerAndOwnerSign"));
        return ResponseEntity.ok(map);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "docs/dealerandownersign/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getDealerAndOwnerSignature(@PathVariable("vehicleRcId") Long vehicleRcId) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleRcId)) {
            errorModel.setMessage("Invalid VehicleRcId, value is null");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        Map<String, String> map = attachmentDetailsService.getDealerAndOwnerSignature(vehicleRcId);
        logger.debug("Dealer Signature : " + map.get("dealerSignature"));
        return ResponseEntity.ok(map);
    }


    @RequestMapping(value = "formdetails/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFormDetails(@PathVariable("vehicleRcId") Long vehicleRcId) {
        logger.debug("getFormDetails : Start");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleRcId)) {
            errorModel.setMessage("Invalid VehicleRcId, value is null");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        FormsModel formsModel = null;
        try{
            formsModel = exportDataService.getVehicleDetails(vehicleRcId);
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        logger.debug("getFormDetails : end");
        return ResponseEntity.ok(formsModel);
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "docs/formdocs/{vehicleRcId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFormDocs(@PathVariable("vehicleRcId") Long vehicleRcId) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleRcId)) {
            errorModel.setMessage("Invalid VehicleRcId, value is null");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        List<DocTypesModel> docTypesModels = docPermissionService.getFormDocs(vehicleRcId);
        return ResponseEntity.ok(docTypesModels);
    }


    @RequestMapping(value = "docs/user/{username}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserAttachmentDetails(@PathVariable("username") String userName,
            @RequestParam(value = "docid", required = false) Integer docId) {
        logger.info("getAllUserAttachmentDetails :::::::::::::::: Start");
        if (StringsUtil.isNullOrEmpty(userName)) {
            throw new IllegalArgumentException("Invalid Username, value is null");
        }
        if (ObjectsUtil.isNull(docId)) {
            List<AttachmentDetailsModel> models = null;
            try {
                models = attachmentDetailsService.getAllUserAttachmentDetails(userName);
            } catch (NotFoundException ne) {
                logger.error("getAllUserAttachmentDetails :::::::::::::::: NotFoundException");
            }
            return ResponseEntity.ok(models);
        } else {
            AttachmentDetailsModel model = null;
            try {
                model = attachmentDetailsService.getUserAttachmentDetails(userName, docId);
            } catch (NotFoundException ne) {
                logger.error("getAllUserAttachmentDetails :::::::::::::::: NotFoundException");
            }
            return ResponseEntity.ok(model);
        }
    }

    @RequestMapping(value = "docs/user", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> userDocs(@Valid @RequestBody AttachmentDetailsModel attachmentDetailsModel,
            HttpServletRequest request) {

        if (ObjectsUtil.isNull(attachmentDetailsModel)) {
            throw new IllegalArgumentException("bad request");
        }
        SaveUpdateResponse saveUpdateModel = null;
        try {
            String token = request.getHeader(tokenHeader);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
            saveUpdateModel = attachmentDetailsService.saveOrUpdateforUser(attachmentDetailsModel, userName, userType);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }

        logger.info("User Attachment Document Succsssfully Saved " + attachmentDetailsModel.getFileName());
        return ResponseEntity.ok(saveUpdateModel);
    }

    @RequestMapping(value = "multipledocs/user", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> userMultipleDocs(@Valid @RequestBody List<AttachmentDetailsModel> attachmentDetailsModels,
            HttpServletRequest request) {

        if (ObjectsUtil.isNull(attachmentDetailsModels)) {
            throw new IllegalArgumentException("bad request");
        }
        SaveUpdateResponse saveUpdateModel = null;
        try {
            String token = request.getHeader(tokenHeader);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            UserType userType = UserType.valueOf(jwtTokenUtil.getUserRoleFromToken(token).get(0));
            saveUpdateModel = attachmentDetailsService.multipleSaveOrUpdateForUser(attachmentDetailsModels, userName, userType);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }

        logger.info("User Multiple Document  Succsssfully Saved " + attachmentDetailsModels.size());
        return ResponseEntity.ok(saveUpdateModel);
    }

    @RequestMapping(value = "/multipledocs/licence/{aadhaarno}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> licenceMultipleDocs(@Valid @RequestBody List<AttachmentDetailsModel> attachmentDetailsModels,
    		@PathVariable("aadhaarno") String aadhaarNo, HttpServletRequest request) {

        if (ObjectsUtil.isNull(attachmentDetailsModels)) {
        	return ResponseEntity.badRequest().build();
        }
        SaveUpdateResponse saveUpdateModel = null;
        try {
            String userName = jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader));
            saveUpdateModel = attachmentDetailsService.multipleSaveOrUpdateForLicenceAttachments(attachmentDetailsModels, userName, aadhaarNo);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        } catch (Exception ex) {
            logger.error("Exception while saving Attachment documents :: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
        logger.info("Licence Multiple Document  Succsssfully Saved " + attachmentDetailsModels.size());
        return ResponseEntity.ok(saveUpdateModel);
    }
}
