package org.rta.registration.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.certificate.OtherVehicleDetailModel;
import org.rta.core.model.reports.invoice.VehicleInvoiceModel;
import org.rta.core.model.vehicle.VehicleBaseModel;
import org.rta.core.service.certificate.NocDetailsService;
import org.rta.core.service.report.invoice.VehicleInvoiceService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.security.utills.JwtTokenUtil;
import org.rta.security.utills.RtaCryptoUtil;
import org.rta.vahan.api.common.model.ManufacturingDate;
import org.rta.vahan.exception.InvalidManufacturingDateFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class InvoiceController {

    private static final Logger log = Logger.getLogger(InvoiceController.class);
    @Value("${jwt.header}")
    private String tokenHeader;
    
    @Value("${jwt.claim.secret}")
    private String claimSecret;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    @Autowired
    private VehicleInvoiceService vehicleInvoiceService;

    @Autowired
    private NocDetailsService nocDetailsService;
    
    @PreAuthorize("hasAnyRole('ROLE_DEALER','ROLE_RTO','ROLE_AO','ROLE_CITIZEN')")
    @RequestMapping(path = "/getcustomerinvoice/{vehiclercid}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getCustomerInvoice(HttpServletRequest request,
            @PathVariable("vehiclercid") String vehicleRcIdEnc,
            @RequestParam(name = "regtype", required = false) String invoiceType,
            @RequestParam(name = "isnoc", required = false) boolean isNoc) {
        SaveUpdateResponse errorModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE);
        String vehicleRcIdDecrypted = RtaCryptoUtil.parseSecureToken(vehicleRcIdEnc, claimSecret);
        Long vehicleRcId = null;
        try {
            vehicleRcId = Long.parseLong(vehicleRcIdDecrypted);
        } catch (NumberFormatException ex) {
            log.error("customerInvoice :: Invalid VehicleRcId =>" + vehicleRcIdDecrypted);
            errorModel.setMessage("Invalid vehicleRcId!");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        String token = request.getHeader(tokenHeader);
        String userName = jwtTokenUtil.getUsernameFromToken(token);
        if (userName == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        VehicleInvoiceModel vehicleInvoiceModel = new VehicleInvoiceModel();
        try {
            vehicleInvoiceModel = vehicleInvoiceService.getCustomerInvoice(vehicleRcId, invoiceType);
            VehicleBaseModel vbm = vehicleInvoiceModel.getVehicleBaseModel();
            if (!ObjectsUtil.isNull(vbm)) {
                String mfgDateString = vbm.getManufacturingMonthYear();
                try {
                    ManufacturingDate mfgDate = new ManufacturingDate(mfgDateString);
                    vbm.setManufacturingMonthYear(new StringBuilder(mfgDate.getYear()).append("-").append(mfgDate.getMonth()).toString());
                } catch (InvalidManufacturingDateFormatException e) {
                    log.warn("date format is found invalid for mfgDate : " + mfgDateString + ". Defaulting to same as sent by vahan-api.");
                    log.debug(e);
                }
            }
            vehicleInvoiceModel.setVehicleRcId(vehicleRcIdEnc);
            if(isNoc){
            	vehicleInvoiceModel.setNocDetails(nocDetailsService.getNocDetails(vehicleRcId, null));
            	OtherVehicleDetailModel detailModel = new OtherVehicleDetailModel();
            	detailModel.setSessionActPending("NO");
            	detailModel.setTaxPending("NO");
            	detailModel.setTheftCase("NO");
            	detailModel.setTransportCaseGoods("NO");
            	vehicleInvoiceModel.setOtherNocDetails(detailModel);
            }
        } catch(IllegalArgumentException ie){
        	errorModel.setMessage(ie.getMessage());
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        } catch (Exception e) {
            log.error("Exception while getCustomerInvoice Details :: " + e.getMessage());
            e.printStackTrace();
            errorModel.setMessage("Parameter(s) violating Some Data Integrity !");
        	return ResponseEntity.status(HttpStatus.ACCEPTED).body(errorModel);
        }
        if (ObjectsUtil.isNull(vehicleInvoiceModel)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(vehicleInvoiceModel);

    }


}
