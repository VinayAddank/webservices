package org.rta.registration.controller;

import java.io.FileNotFoundException;
import java.security.KeyStoreException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rta.registration.pan.model.PanDetailsModel;
import org.rta.registration.pan.service.PanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PanController {
	
	private static final Log logger = LogFactory.getLog(PanController.class);
	
	@Autowired
	private PanService panService; 
	
	@RequestMapping(value = "/pancard/details/{panNumber}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getUserFromToken(@PathVariable("panNumber") String panNumber) 
    		throws KeyStoreException, FileNotFoundException, Exception{
        logger.info(":::::::::::PAN Details are start::::::::::");
		PanDetailsModel pdModel = panService.getPanDetails(panNumber);
		if (pdModel == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(pdModel);
    }
	
}
