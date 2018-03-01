package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.sync.RCAadharSeedModel;
import org.rta.core.model.sync.SyncDataModel;
import org.rta.core.service.sync.SyncService;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncDataController {

	private static final Logger log = Logger.getLogger(SyncDataController.class);
	
	@Value("${jwt.header}")
	private String tokenHeader;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	 
	@Autowired
	private SyncService syncService;
	
	@Autowired
	private CmsLiveSyncingService cmsLiveSyncingService;

	@RequestMapping(value = "/syncdata",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, method = RequestMethod.POST)
    public ResponseEntity<?> syncData(@RequestBody SyncDataModel syncDataModel) {
        log.info(":::REGISTRATION syncdata:::controller::: " + syncDataModel);
		SaveUpdateResponse response;
		boolean flag = false;
        try {
        	response = syncService.syncData(syncDataModel);
        	flag = true;
        } catch (Exception e) {
        	e.printStackTrace();
        	
            response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, e.getMessage(), null);
        }
        if(flag){
        	try{
        		String result = cmsLiveSyncingService.cmsLiveSyncingService(syncDataModel.getVehicleRcId(), syncDataModel.getPrNumber(), syncDataModel.getServiceType());
            	log.info("::: Syncing data with CMS status :::: " + result);
        	}catch (Exception e) {
        		log.info("::: Syncing data with CMS status :::: found something is wrong, when getting details ");
			}
        }
        log.info(":::REGISTRATION syncdata:::controller::: " + response.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
	@RequestMapping(value = "/rc/aadhaarseeding",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}, 
			method = RequestMethod.PUT)
    public ResponseEntity<?> rcAadhaarSeeding(@RequestBody RCAadharSeedModel aadharSeedModel,
    		 HttpServletRequest request) {
		SaveUpdateResponse response;
        try {
        	response = syncService.aadhaarSeeding(aadharSeedModel, jwtTokenUtil.getUsernameFromToken(request.getHeader(tokenHeader)));
        } catch (Exception e) {
        	log.error(":::Getting error in RC Aadhaar Seeding :::: " + e.getMessage());       	
            response = new SaveUpdateResponse(SaveUpdateResponse.FAILURE, e.getMessage(), null);
        }
        log.info(":::RC Aadhaar Seeding status::: " + response.getStatus());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
	
}
