package org.rta.registration.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.NocDetailsModel;
import org.rta.core.model.certificate.PermitDetailsModel;
import org.rta.core.model.certificate.PucDetailsModel;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.certificate.NocDetailsService;
import org.rta.core.service.certificate.PermitDetailsService;
import org.rta.core.service.certificate.PucDetailsService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
 * this controller use for all documents related service
 */
@RestController
public class CertificateController {

	private static final Logger logger = Logger.getLogger(CertificateController.class);

    @Value("${jwt.claim.secret}")
    private String claimSecret;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${rta.fc.text}")
    private String fcText;

    @Value("${rta.pc.text}")
    private String pcText;
    
    @Value("${rta.tpc.text}")
    private String tpcText;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PermitDetailsService permitDetailsService;

    @Autowired
    private FcDetailsService fcDetailsService;

    @Autowired
    private NocDetailsService nocDetailsService;

    @Autowired	
    private PucDetailsService pucDetailsService;

    @Autowired
    private CmsLiveSyncingService cmsLiveSyncingService;

    @RequestMapping(value = "permitdetails", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> permitDetails(@Valid @RequestBody PermitDetailsModel permitDetailsModel,
            HttpServletRequest request) {

        if (ObjectsUtil.isNull(permitDetailsModel)) {
            throw new IllegalArgumentException("bad request");
        }
        SaveUpdateResponse saveUpdateModel = null;
        try {
            String token = request.getHeader(tokenHeader);
            String userName = jwtTokenUtil.getUsernameFromToken(token);
            saveUpdateModel = permitDetailsService.saveOrUpdate(permitDetailsModel, userName);
        } catch (DataIntegrityViolationException ex) {
            logger.error("Exception while saving Permit Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Parameter(s) violating Some Data Integrity !");
        } catch (InvalidDataAccessApiUsageException ex) {
            logger.error("Exception while saving Permit Details :: " + ex.getMessage());
            throw new IllegalArgumentException("Invalid Data Received !");
        }

        logger.info(" Permit Details Succsssfully Saved ");
        return ResponseEntity.ok(saveUpdateModel);
    }

    /*@PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")*/
    @RequestMapping(value = "certificate/{vehiclercid}/{certificatetype}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getCertificate(@PathVariable("vehiclercid") Long vehicleRcId, @PathVariable("certificatetype") String certificateType,
            @RequestParam(value = "approver_mvi", required = false) String mviUserIdString) {
        /*
         * If called from citizen then passing mvi userId for name and signature purpose
         */
        Long mviUserId = null;
        try{
            mviUserId = Long.parseLong(mviUserIdString);
        } catch(Exception ex){
            logger.error("Exception in casting mviUserIdString : " + mviUserIdString);
        }
        if (ObjectsUtil.isNull(vehicleRcId) || StringsUtil.isNullOrEmpty(certificateType)) {
            throw new IllegalArgumentException("bad request");
        }
        if (certificateType.equalsIgnoreCase(pcText)) {
            logger.info("::::::: Permit certificate  :: ");
            return ResponseEntity.ok(permitDetailsService.getPermitCertificate(vehicleRcId, mviUserId));
        } else if (certificateType.equalsIgnoreCase(tpcText)) {
            logger.info("::::::: Temporary Permit certificate  :: ");
            return ResponseEntity.ok(permitDetailsService.getTempPermitCertifcate(vehicleRcId, mviUserId));
        } else if (certificateType.equalsIgnoreCase(fcText)) {
            logger.info("::::::: Fitness certificate  :: ");
            return ResponseEntity.ok(fcDetailsService.getFcCertificate(vehicleRcId, mviUserId));
        }
        logger.info(" certificateType is wrong ");
        return ResponseEntity.ok("Please enter valid certificateType");
    }

    /*
     * #TODO delete it. Not in Use
     * 
     * @RequestMapping(value = "permitsubtype/{vehiclercid}/{subtype}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPermitSubType(@PathVariable("vehiclercid") Long vehicleRcId,
            @PathVariable("subtype") String permitSubType) {

        if (ObjectsUtil.isNull(vehicleRcId) || StringsUtil.isNullOrEmpty(permitSubType)) {
            throw new IllegalArgumentException("bad request");
        }

        List<PermitSubTypeModel> models =
                permitDetailsService.getPermitSubTypeList(vehicleRcId, PermitSubType.valueOf(permitSubType));

        logger.info(" :::::::::::getPermitSubType ::::::::: ");
        return ResponseEntity.ok(models);
    }*/


    // @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
    @RequestMapping(value = "noc/details", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> nocDetails(@RequestParam(name = "vehiclercid", required = false) Long vehicleRcId,
            @RequestParam(name = "prnumber", required = false) String prNumber) {
        if (ObjectsUtil.isNull(vehicleRcId) && StringsUtil.isNullOrEmpty(prNumber)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        NocDetailsModel model = null;
        try {
            model = nocDetailsService.getNocDetails(vehicleRcId, prNumber);
        } catch (NotFoundException e) {
            logger.error(" NOC Details : ");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        logger.info(" NOC Details send Succsssfully ");
        return ResponseEntity.ok(model);
    }

    // @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
    @RequestMapping(value = "noc/details", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> nocDetails(@Valid @RequestBody NocDetailsModel model, HttpServletRequest request) {

        if (ObjectsUtil.isNull(model)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        SaveUpdateResponse response = null;
        try {
            response = nocDetailsService.saveOrUpdate(model, jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        } catch (DataMismatchException e) {
            logger.error(" NOC Details : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        try{
    		String result = cmsLiveSyncingService.cmsLiveSyncingService(model.getVehicleRcId() ,null, model.getServiceCode());
        	logger.info("::: Syncing data with CMS status :::: " + result);
    	}catch (Exception e) {
    		logger.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
		}
        logger.info(" NOC Details Saved Succsssfully ");
        return ResponseEntity.ok(response);
    }

    // @PreAuthorize("hasAnyRole('ROLE_PUC','ROLE_CITIZEN')")
    @RequestMapping(value = "pucdetails/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> pucDetails(@PathVariable("vehiclercid") Long vehicleRcId) {

        if (ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("bad request..VehicleRcId is null");
        }
        PucDetailsModel model = null;
        try {
            model = pucDetailsService.getPucDetails(vehicleRcId);
        } catch (NotFoundException e) {
            logger.error(" PUC Details : ");
            throw new IllegalArgumentException("There is not PUC for vehicleRcId = " + vehicleRcId);
        }
        logger.info(" PUC Details send Succsssfully ");
        return ResponseEntity.ok(model);
    }

    // @PreAuthorize("hasAnyRole('ROLE_PUC','ROLE_CITIZEN')")
    @RequestMapping(value = "pucdetails", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> pucDetails(@Valid @RequestBody PucDetailsModel model, HttpServletRequest request) {

        if (ObjectsUtil.isNull(model)) {
            throw new IllegalArgumentException("bad request..");
        }
        SaveUpdateResponse response = null;
        try {
            response = pucDetailsService.saveOrUpdate(model,
                    jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        } catch (DataMismatchException e) {
            logger.error(" PUC Details : ", e);
            throw new IllegalArgumentException("There is not PUC for vehicleRcId = " + model.getVehicleRcId());
        }
        logger.info(" PUC Details Saved Succsssfully ");
        return ResponseEntity.ok(response);
    }


    @RequestMapping(value = "noc/address", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> addressList(@RequestParam(name = "districtcode", required = false) String districtCode,
            @RequestParam(name = "nocaddresscode", required = false) String nocAddressCode) {

        if (StringsUtil.isNullOrEmpty(districtCode) && StringsUtil.isNullOrEmpty(nocAddressCode)) {
            throw new IllegalArgumentException("bad request..");
        }
        if (!StringsUtil.isNullOrEmpty(nocAddressCode)) {
            NocDetailsModel nocAddress = null;
            try{
                nocAddress = nocDetailsService.getNocAddressDetails(nocAddressCode);
            } catch (NotFoundException ex) {
                throw new IllegalArgumentException("There is not Noc Adddress Details for nocAddressCode = " + nocAddressCode);
            }
            logger.info(" NOC Address Details Send Succsssfully ");
            return ResponseEntity.ok(nocAddress);
        }
        else if (!StringsUtil.isNullOrEmpty(districtCode)) {
            List<NocDetailsModel> nocAddressList = null;
            try {
                nocAddressList = nocDetailsService.getNocAddressList(districtCode);
            } catch (NotFoundException ex) {
                throw new IllegalArgumentException("There is not Noc Adddress List for districtCode = " + districtCode);
            }
            logger.info(" NOC Address List Send Succsssfully ");
            return ResponseEntity.ok(nocAddressList);
        }
        logger.info(" get bad request ");
        return ResponseEntity.badRequest().build();
    }

}
