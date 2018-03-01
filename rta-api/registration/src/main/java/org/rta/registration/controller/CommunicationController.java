package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommunicationController {

	private static final Logger log = Logger.getLogger(CommunicationController.class);

	@Autowired
	MessageConfig msgconfig;
	
	
	@PreAuthorize("hasAnyRole('ROLE_CITIZEN')")
	@RequestMapping(path = "/msgconfig", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> msgConfig(HttpServletRequest request) {
        log.info("::::::::msgconfig:::::::::: ");
        return ResponseEntity.status(HttpStatus.OK).body(msgconfig);
    }
}
