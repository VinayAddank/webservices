package org.rta.registration.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecialNumberConroller {

    private static final Logger logger = Logger.getLogger(SpecialNumberConroller.class);

    /*
     * @Autowired private SpecialNumberService specialNumberService;
     * 
     * @RequestMapping(path = "/getspecialno", method = RequestMethod.GET, produces =
     * {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) public
     * List<SpecialNumberModel> getPayTaxNdLockRegistNo() { log.info(
     * "::::::::::PayTax start::::::::::"); List<SpecialNumberModel> specialNumberModels =
     * specialNumberService.getAll(); log.info("::::::::::specialNumberModels::::::::" +
     * specialNumberModels); return specialNumberModels; }
     * 
     * @RequestMapping(path = "/lockspecialno", method = RequestMethod.GET, produces =
     * {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}) public void
     * getLockSpecialNumber(@RequestBody SpecialNumberModel specialNumberModel) { log.info(
     * "::::::::::lockspecialno start::::::::::");
     * specialNumberService.lockSpecialNo(specialNumberModel); log.info(
     * "::::::::::lockSpecialNo locked::::::::");
     * 
     * }
     */

}
