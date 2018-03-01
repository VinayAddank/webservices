package org.rta.registration.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.cardprinter.PrintStatusModel;
import org.rta.core.model.cardprinter.RcCardDetailsModel;
import org.rta.core.model.cardprinter.RtyFromtoModel;
import org.rta.core.service.cardprinter.RcCardDetailsService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *	@Author sohan.maurya created on Sep 13, 2016.
 */
@RestController
public class CardPrinterController {

    private static final Logger logger = Logger.getLogger(CardPrinterController.class);

    @Autowired
    private RcCardDetailsService rcCardDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PreAuthorize("hasRole('ROLE_PRINTER')")
    @RequestMapping(value = "aptransprod/rccard_api", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getRcCardPrinter(@RequestParam(name = "rcno", required = false) String prNumer,
            @RequestParam(name = "rty", required = true) String rty,
            @RequestParam(name = "rta", required = true) String rtaCode,
            @RequestParam(name = "date", required = false) String date,
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to) {
        logger.info(":::::::::getRcCardPrinter::::::::::start::::::");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (StringsUtil.isNullOrEmpty(rty) || StringsUtil.isNullOrEmpty(rtaCode)) {
            errorModel.setMessage("Invalid parameter  !!!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        else if (rty.equalsIgnoreCase("rccard")) {
            if (StringsUtil.isNullOrEmpty(prNumer)) {
                errorModel.setMessage("please enter rcno  !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            RcCardDetailsModel rcCardDetailsModel = null;
            try {
                rcCardDetailsModel = rcCardDetailsService.getRcCardDetails(prNumer, rtaCode);
            } catch (Exception ex) {
                logger.error("Exception in getRcCardPrinter : " + ex.getMessage());
                logger.debug(ex);
                errorModel.setMessage(ex.getMessage());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            return ResponseEntity.ok(rcCardDetailsModel);
        }
        else if (rty.equalsIgnoreCase("date")) {
            if (StringsUtil.isNullOrEmpty(date)) {
                errorModel.setMessage("please enter date  !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            List<RcCardDetailsModel> rcCardDetailsModels = null;
            try{
                rcCardDetailsModels = rcCardDetailsService.getRcCardDetailsByDate(date, rtaCode);
            } catch (Exception ex) {
                logger.error("Exception in getRcCardPrinter : " + ex.getMessage());
                logger.debug(ex);
                errorModel.setMessage(ex.getMessage());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            return ResponseEntity.ok(rcCardDetailsModels);
        }
        else if (rty.equalsIgnoreCase("fromto")) {
            if (StringsUtil.isNullOrEmpty(from) || StringsUtil.isNullOrEmpty(to)) {
                errorModel.setMessage("please enter from and to  !!!");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            List<RtyFromtoModel> rtyFromtoModels = null;
            try {
                rtyFromtoModels = rcCardDetailsService.getRcCardBetweenTwoDate(from, to, rtaCode);
            } catch (Exception ex) {
                logger.error("Exception in getRcCardPrinter : " + ex.getMessage());
                logger.debug(ex);
                errorModel.setMessage(ex.getMessage());
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
            }
            return ResponseEntity.ok(rtyFromtoModels);
        }
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

    @PreAuthorize("hasRole('ROLE_PRINTER')")
    @RequestMapping(value = "aptransprod/rccard_api/update", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> updatePrintStatus(@Valid @RequestBody PrintStatusModel printStatusModel) {
        logger.info(":::::::::updatePrintStatus::::::::::start::::::");
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        if (ObjectsUtil.isNull(printStatusModel.getIsPrinted()) || !printStatusModel.getIsPrinted()) {
            errorModel.setMessage("Bad Request !!, isPrinted should be true");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        SaveUpdateResponse model = rcCardDetailsService.updatePrintStatus(printStatusModel);

        return (ResponseEntity<?>) ResponseEntity.ok(model);
    }

    @RequestMapping(value = "aptransprod/rccard_api/rtaoffice", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getOfficeCode(HttpServletRequest request) {
        logger.info(":::::::::getOfficeCode::::::::::start::::::");
        String token = request.getHeader(tokenHeader);
        Long userId = jwtTokenUtil.getUserIdFromToken(token);

        String rtaOfficeCode =null;
        try {
            rtaOfficeCode = rcCardDetailsService.getRtaOfficeCode(userId);
        } catch (Exception ex) {
            SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
            errorModel.setMessage(ex.getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (StringsUtil.isNullOrEmpty(rtaOfficeCode)) {
            return ResponseEntity.noContent().build();
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("officeCode", rtaOfficeCode);
        return (ResponseEntity<?>) ResponseEntity.ok(map);
    }

}
