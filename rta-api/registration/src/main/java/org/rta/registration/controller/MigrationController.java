package org.rta.registration.controller;

import org.apache.log4j.Logger;
import org.rta.core.service.application.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/migration")
public class MigrationController {

    private static final Logger LOGGER = Logger.getLogger(MigrationController.class);
    
    @Autowired
    private ApplicationService applicatioService;
       
    /**
     * Move rto user action history to vehicle rc table
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "history/to/vehicle", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<?> moveActionHistory() {
        LOGGER.info("######################## START ############################");
        applicatioService.movePrPendingAppActionHistory();
        LOGGER.info("##################### THE End #############################");
        return ResponseEntity.ok().build();
    }

}
