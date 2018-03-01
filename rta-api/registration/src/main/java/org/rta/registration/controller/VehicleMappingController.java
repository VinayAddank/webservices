package org.rta.registration.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.vehicle.cms.MakerMasterModel;
import org.rta.core.model.vehicle.cms.ModelDetailsModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.rta.core.model.vehicle.cms.VehicleClassModel;
import org.rta.core.service.vehicle.cms.MakerMasterService;
import org.rta.core.service.vehicle.cms.ModelDetailsService;
import org.rta.core.service.vehicle.cms.VehicleMappingService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehicleMappingController {
    private static final Logger logger = Logger.getLogger(VehicleMappingController.class);

	@Autowired
    private VehicleMappingService vehicleMappingService;

    @Autowired
    private MakerMasterService makerMasterService;

    @Autowired
    private ModelDetailsService modelDetailsService;

	@Value("${jwt.header}")
	private String tokenHeader;

	@RequestMapping(path = "/vehicleclasses", method = RequestMethod.GET, 
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<?> getVehicleClasses() {
		SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		List<VehicleClassModel> vehiclesClasses = null;
		try{
			vehiclesClasses = vehicleMappingService.getAllVehicleClass();
		} catch(IllegalArgumentException ie){
			errorModel.setMessage(ie.getMessage());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
		}
		if (vehiclesClasses == null || vehiclesClasses.size() <= 0)
			return ResponseEntity.noContent().build();

		return ResponseEntity.ok(vehiclesClasses);

	}
	
    @RequestMapping(path = "/vehicleclassdetails/{regcategorycode}/{classcode}", method = RequestMethod.GET,
			produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getAllVehicleDes4ClassCode(@PathVariable("regcategorycode") String regCategoryCode,
            @PathVariable("classcode") String classCode) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
		List<VehicleClassDescModel> vehiclesClasses = null;
		try{
		    vehiclesClasses = vehicleMappingService.getAllVehicleClassDescription(regCategoryCode, classCode);
		} catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
		if (vehiclesClasses == null || vehiclesClasses.size() <= 0)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(vehiclesClasses);

	}

    @RequestMapping(path = "/makers", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getAllMakers() {

        List<MakerMasterModel> makerMasterModels = null;
        makerMasterModels = makerMasterService.getMakersMaster();
        if (makerMasterModels == null || makerMasterModels.size() <= 0)
            return ResponseEntity.noContent().build();
        logger.debug("Get Maker Master ::: " + makerMasterModels.size());
        return ResponseEntity.ok(makerMasterModels);
    }

    @RequestMapping(path = "/modeldetails/{makerId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getModelDetails(@PathVariable("makerId") Integer makerId) {
    	SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(makerId)) {
        	errorModel.setMessage("Invalid makerId !!!");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        List<ModelDetailsModel> modelDetailsModels = null;
        modelDetailsModels = modelDetailsService.getModelByMakerId(makerId);
        if (modelDetailsModels == null || modelDetailsModels.size() <= 0)
            return ResponseEntity.noContent().build();
        logger.debug("Get Model Details basis of makerId ::: " + modelDetailsModels.size());
        return ResponseEntity.ok(modelDetailsModels);

    }
    
	
    @RequestMapping(path = "/taxtypeforvehiclesubclass/{vehiclesubclass}", method = RequestMethod.GET,
	produces={MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getTaxType4VehicleClass( @PathVariable("vehiclesubclass") String vehicleClass,
            @RequestParam(value = "seatingcapacity", required = false) Integer seatingCapacity) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(vehicleClass)) {
            errorModel.setMessage("Invalid vehicle Class !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        TaxTypeModel taxTypeModel = null;
        try{
            taxTypeModel = vehicleMappingService.getTaxType4VehicleSubClass(vehicleClass, seatingCapacity);
        } catch(IllegalArgumentException ie){
            errorModel.setMessage(ie.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (taxTypeModel == null){
        	   return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(taxTypeModel);
    }
    
    /**
     * Get vehicle class description 
     * 
     * @param regCategoryCode
     * @param alterationCategory
     * @return
     */
    @RequestMapping(path = "/vehicleclassdetails/{regcategorycode}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getVehicleClassDesc(@PathVariable("regcategorycode") String regCategoryCode, 
    		@RequestParam(value = "classcode", required = false) String classCode,
    		@RequestParam(value = "alteration_cat", required = false) Integer alterationCategory,
    		@RequestParam(value = "isnewvehicle", required = false) boolean isNewVehicle) {
    
        List<VehicleClassDescModel> vehiclesClasses = null;
        vehiclesClasses = vehicleMappingService.getVehicleClassDesc(regCategoryCode, classCode, alterationCategory,isNewVehicle);
        if (vehiclesClasses == null || vehiclesClasses.size() <= 0)
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(vehiclesClasses);

    }
    
    /**
     * Get vehicle class code and description by pr number 
     * @param prNumber
     * @return
     */
    @RequestMapping(path = "/vehicleclassdetails/pr/{pr_number}", method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> getVehicleClassDesc(@PathVariable("pr_number") String pr_number) {
        VehicleClassDescModel vehiclesClasses = vehicleMappingService.getVehicleClassDesc(pr_number);
        return ResponseEntity.ok(vehiclesClasses);
    }
}
        
