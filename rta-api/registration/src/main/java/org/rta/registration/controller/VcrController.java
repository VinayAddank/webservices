package org.rta.registration.controller;

import org.rta.core.model.payment.tax.VcrResponseModel;
import org.rta.registration.pan.model.InsertVcrModel;
import org.rta.registration.service.vcr.VcrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VcrController {
	@Autowired
	private VcrService vcrService;

    // @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(value = "/vcr/getReport/{docType}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.GET)
    public ResponseEntity<?> getVcrReport(@RequestHeader("Authorization") String token,
            @PathVariable("docType") String docType,
            @RequestParam(value = "docnumber", required = true) String docNumber) {
        VcrResponseModel vcrList = vcrService.saveCalculateVcrDetails(docType, docNumber);

        return ResponseEntity.status(HttpStatus.OK).body(vcrList);
    }

    @RequestMapping(value = "/vcr/payTax",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public String saveTax(@RequestHeader("Authorization") String token, @RequestBody InsertVcrModel insertVcrModel) {
        // vcrService.updateVcrDetails(insertVcrModel);

        //return vcrService.updateVcrDetails(insertVcrModel.getRegnNo());
    	return null;
    }
}
