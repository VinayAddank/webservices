package org.rta.registration.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.rta.core.model.export.IibExportModel;
import org.rta.core.model.export.SinkAllData;
import org.rta.core.model.export.SinkTaxDetails;
import org.rta.core.service.citizen.ResponseModel;
import org.rta.core.service.export.ExportDataService;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * this controller use for cms sinking related service
 */
@RestController
public class CmsSinkController {

	private static final Logger logger = Logger.getLogger(CmsSinkController.class);

    @Autowired
    private ExportDataService exportDataService;

    
    @RequestMapping(value = "cmssink/vehicledetails", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getVehicleDetails(@RequestParam(name = "from", required = true) String from,
            @RequestParam(name = "to", required = true) String to,
            @RequestParam(name = "bytrdate", required = false, defaultValue = "false") Boolean byTrIssueTime) {
        if (StringsUtil.isNullOrEmpty(from) || StringsUtil.isNullOrEmpty(to)) {
            throw new IllegalArgumentException("Please pass valid Parameter ....!!!");
        }
		List<SinkAllData> sinkAllDatas=null;
		try{
            sinkAllDatas = exportDataService.getAllVehicleDetailsBetweenADay(from, to, byTrIssueTime);
        } catch (Exception ex) {
		    throw new IllegalArgumentException("Vehicle Record Not found..!");
		}
        logger.info("  getVehicleDetails " + sinkAllDatas.size());
        return ResponseEntity.ok(sinkAllDatas);
	}

    @RequestMapping(value = "cmssink/taxdetails", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getTaxDetails(@RequestParam(name = "from", required = true) String from,
            @RequestParam(name = "to", required = true) String to, 
            @RequestParam(name = "bytrdate", required = false, defaultValue = "false") Boolean byTrIssueTime) {
        if (StringsUtil.isNullOrEmpty(from) || StringsUtil.isNullOrEmpty(to)) {
            throw new IllegalArgumentException("Please pass valid Parameter ....!!!");
        }
        List<SinkTaxDetails> sinkTaxDetails = null;
        try {
            sinkTaxDetails = exportDataService.getAllTaxDetailsBetweenADay(from, to, byTrIssueTime);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Vehicle Record Not found..!");
        }
        logger.info("  getVehicleDetails " + sinkTaxDetails.size());
        return ResponseEntity.ok(sinkTaxDetails);
    }

    
    @RequestMapping(value = "/iib/vehicle/details/{keytype}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getVehicleDetailsForIIB(@PathVariable("keytype") String keyType, @RequestParam(name = "from", required = true) String from,
            @RequestParam(name = "to", required = true) String to) {
        if (StringsUtil.isNullOrEmpty(from) || StringsUtil.isNullOrEmpty(to)) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseModel.FAILED);
        }
        List<IibExportModel> models = null;
        try {
        	models = exportDataService.getVehicleRegistrationDetails(keyType, from, to);
        } catch (Exception ex) {
        	return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseModel.FAILED);
        }
        logger.info("  getVehicleDetailsForIIB " + models.size());
        return ResponseEntity.ok(models);
    }
}
