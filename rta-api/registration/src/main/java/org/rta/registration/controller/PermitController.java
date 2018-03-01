/**
 * 
 */
package org.rta.registration.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.rta.core.enums.PermitDetailsType;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.certificate.PermitTypeVehicleClassModel;
import org.rta.core.model.master.PermitTypeModel;
import org.rta.core.model.permit.PermitAuthorizationCardModel;
import org.rta.core.model.permit.PermitCodeDescModel;
import org.rta.core.model.permit.PermitHeaderModel;
import org.rta.core.model.permit.PermitTempPermitModel;
import org.rta.core.service.certificate.PermitAuthCardService;
import org.rta.core.service.certificate.PermitDetailsService;
import org.rta.core.service.master.PermitTypeService;
import org.rta.core.service.service.PermitService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PermitController {

	private static final Logger logger = Logger.getLogger(PermitController.class);

    @Autowired
    private PermitTypeService permitTypeService;
    
    @Autowired
    private PermitService permitService;

    @Autowired
    private PermitDetailsService permitDetailsService;
    
    @Autowired
    private PermitAuthCardService permitAuthCardService;
    
    @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_CITIZEN')")
    @RequestMapping(value = "permitdetails/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> permitDetails(@PathVariable("vehiclercid") Long vehicleRcId,
    		@RequestParam(name = "permit_type", required = false) String permitType) {

        if (ObjectsUtil.isNull(vehicleRcId)) {
            throw new IllegalArgumentException("bad request");
        }
        List<PermitHeaderModel> model = null;
        try {
            model = permitDetailsService.getPermitHeaderDetails(vehicleRcId, permitType);
        } catch (VehicleRcNotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        logger.info(" Permit Details send Succsssfully ");
        return ResponseEntity.ok(model);
    }

//    @RequestMapping(path = "/permittypelist", method = RequestMethod.GET,
//            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
//    public ResponseEntity<?> getPermitTypes(@RequestParam(name = "trnumber", required = true) String trNumber) {
//        logger.info("PermitTypes list start :");
//        if (StringsUtil.isNullOrEmpty(trNumber)) {
//            throw new IllegalArgumentException(" Tr Number is null ");
//        }
//        PermitTypeVehicleClassModel permitTypeModels = null;
//        try {
//            permitTypeModels = permitTypeService.getPermitTypeVehicleClass(trNumber);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            logger.error("PermitTypeModels  Exception for tr number : " + trNumber);
//        }
//        logger.info(" PermitTypeModels end  ");
//        return ResponseEntity.ok(permitTypeModels);
//    }

    @RequestMapping(path = "/permittypes/cov/{cov}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getPermitTypesByCov(@PathVariable("cov") String cov) {
        logger.info("PermitTypes list start for cov :" + cov);
        List<PermitTypeModel> permitTypes = permitTypeService.getPermitType(cov);
        logger.info(" PermitTypeModels end  ");
        return ResponseEntity.ok(permitTypes);
    }
    
    @RequestMapping(path = "/type/{type}/cov/{cov}/permit/{permit_type}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getGoodsRouteCondnsForPrimaryPermit(@PathVariable("type") PermitDetailsType permitDetailsType, @PathVariable("cov") String cov, 
            @PathVariable("permit_type") String permitType) {
        logger.info("getGoodsRouteCondnsForPrimaryPermit start for cov :" + cov + " permit: " + permitType + "type : " + permitDetailsType);
        List<PermitCodeDescModel> goodsList = permitService.getRouteGoodsConditionsForPrimaryPermit(permitDetailsType, cov, permitType);
        logger.info(" getGoodsRouteCondnsForPrimaryPermit end  ");
        return ResponseEntity.ok(goodsList);
    }
    
    @RequestMapping(path = "/permit/temporary/pr/{pr_number}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getTempPermits(@PathVariable("pr_number") String prNumber) {
        logger.info("getTempPermits start for pr :" + prNumber);
        PermitTempPermitModel model = new PermitTempPermitModel();
        try {
            model = permitService.getTempPermits(prNumber);
        } catch (NotFoundException e) {
            logger.info("Error : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(model);
        }
        logger.info(" getTempPermits end  ");
        return ResponseEntity.ok(model);
    }
    
    @RequestMapping(value = "/pukkatemp/permittype/{pr_number}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getSelectedPukkaTempPermit(@PathVariable("pr_number") String prNumber) {
        logger.info("getSelectedPukkaTempPermit in registration Start pr : " + prNumber);
        Map<String, Object> map = permitService.getPukkaTempPermit(prNumber);
        logger.info("getSelectedPukkaTempPermit end ");
        return ResponseEntity.ok(map);
    }
    
    @RequestMapping(value = "/permit/authcard/{pr_number}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getPermitAuthCardDetails(@PathVariable("pr_number") String prNumber) {
        PermitAuthorizationCardModel permitAuthCardModel = permitAuthCardService.getPermitAuthCardDetails(prNumber);
        if (!ObjectsUtil.isNull(permitAuthCardModel)) {
            return ResponseEntity.ok(permitAuthCardModel);
        }
        logger.info("permit auth card details not found for pr_number : " + prNumber);
        return ResponseEntity.notFound().build();
    }
    
    @RequestMapping(path = "/temppermit/{type}/permit/{permit_type}/temp/{temp_type}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getRouteGoodsConditionsForTemporaryPermit(@PathVariable("type") PermitDetailsType permitDetailsType, @PathVariable("permit_type") String permitType,
            @PathVariable("temp_type") String tempPermitType) {
        logger.info("getRouteGoodsConditionsForTemporaryPermit start for permit_type :" + permitType + " temp_type: " + tempPermitType + " type :" + permitDetailsType);
        List<PermitCodeDescModel> condtionList = permitService.getRouteGoodsConditionsForTemporaryPermit(permitDetailsType, permitType, tempPermitType);
        logger.info(" getRouteGoodsConditionsForTemporaryPermit end  ");
        return ResponseEntity.ok(condtionList);
    }
}
