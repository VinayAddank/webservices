/**
 * 
 */
package org.rta.registration.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.rta.core.enums.Status;
import org.rta.core.model.insurance.CfxTxnDtlModel;
import org.rta.core.service.insurance.CfxTxnDtlService;
import org.rta.registration.service.coverfox.CoverfoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author arun.verma
 *
 */

@Controller
@RequestMapping(path = "/cfx")
public class CoverfoxController {

    private static final Logger log = Logger.getLogger(CoverfoxController.class);
    
    @Autowired
    HttpServletRequest request;

    @Autowired
    CoverfoxService coverfoxService;
    
    @Autowired
    CfxTxnDtlService cfxTxnDtlService;
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getmodels", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getModels() {
        try {
            String response = coverfoxService.getModels();
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getmodelsvariants", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getModelsVariants() {
        try {
            String response = coverfoxService.getModelVariants();
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getquotes", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getQuotes() {
        try {
            String response = coverfoxService.getQuotes();
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/refreshquote/quoteid/{quoteId}/insurerslug/{insurerSlug}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> refreshQuote(@PathVariable("quoteId") String quoteId, @PathVariable("insurerSlug") String insurerSlug) {
        try {
            String response = coverfoxService.refreshQuote(quoteId, insurerSlug);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/confirmquote/quoteid/{quoteId}/insurerslug/{insurerSlug}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> confirmQuote(@PathVariable("quoteId") String quoteId, @PathVariable("insurerSlug") String insurerSlug) {
        try {
            String response = coverfoxService.confirmQuote(quoteId, insurerSlug);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getstates/insurerslug/{insurerSlug}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getStates(@PathVariable("insurerSlug") String insurerSlug) {
        try {
            String response = coverfoxService.getStates(insurerSlug);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getcities/insurerslug/{insurerSlug}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getCities(@PathVariable("insurerSlug") String insurerSlug) {
        try {
            String response = coverfoxService.getCities(insurerSlug);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/getloanproviders/insurerslug/{insurerSlug}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> getLoanProviders(@PathVariable("insurerSlug") String insurerSlug) {
        try {
            String response = coverfoxService.getLoanProviders(insurerSlug);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/buy/insurerslug/{insurerSlug}/transactionid/{transactionId}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> buy(@PathVariable("insurerSlug") String insurerSlug, @PathVariable("transactionId") String transactionId) {
        try {
            String response = coverfoxService.buy(insurerSlug, transactionId);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/retrieveconfirmedquote/transactionid/{transactionId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> retrieveConfirmedQuote(@PathVariable("transactionId") String transactionId) {
        try {
            String response = coverfoxService.retrieveConfirmedQuote(transactionId);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/retrieveproposalform/transactionid/{transactionId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> retrieveProposalForm(@PathVariable("transactionId") String transactionId) {
        try {
            String response = coverfoxService.retrieveProposalForm(transactionId);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }

    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/retrievestatusdetails/transactionid/{transactionId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> retrieveStatusDetails(@PathVariable("transactionId") String transactionId) {
        try {
            String response = coverfoxService.retrieveStatusDetails(transactionId);
            log.info("response=> " + response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/prepayment/transactionid/{transactionId}/vehicleRcId/{vehicleRcId}", method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> prePayment(@PathVariable("transactionId") String transactionId, @PathVariable("vehicleRcId") Long vehicleRcId){
        try {
            String response = coverfoxService.prePayment(transactionId);
            log.info("response=> " + response);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode node = objectMapper.readValue(response, JsonNode.class);
                CfxTxnDtlModel cfxTxnDtlModel = new CfxTxnDtlModel();
                cfxTxnDtlModel.setVehicleRcId(vehicleRcId);
                cfxTxnDtlModel.setCfxTxnId(node.get("transaction_id").toString());
                cfxTxnDtlModel.setCfxPaymentId(node.get("payment_id").toString());
                cfxTxnDtlModel.setAmount(node.get("amount").asDouble());
                cfxTxnDtlModel.setPaymentStatus(Status.OPEN.getValue());
                cfxTxnDtlModel.setPolicyStatus(Status.OPEN.getValue());
                cfxTxnDtlModel.setCustomerName(node.get("customer_name").toString());
                cfxTxnDtlModel.setCustomerEmail(node.get("customer_email").toString());
                cfxTxnDtlModel.setCustomerPhone(node.get("customer_phone").toString());
                cfxTxnDtlService.saveUpdate(cfxTxnDtlModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(NumberFormatException e){
                e.printStackTrace();
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/postpayment/transactionid/{transactionId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> postPayment(@PathVariable("transactionId") String transactionId){
        try {
            CfxTxnDtlModel cfxTxnDtlModel = cfxTxnDtlService.getByCfxTxnId(transactionId);
            String response = coverfoxService.postPayment(cfxTxnDtlModel);
            log.info("response=> " + response);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode node = objectMapper.readValue(response, JsonNode.class);
                cfxTxnDtlService.updatePaymentStatus(transactionId, node.get("success").toString(), node.get("code").toString());
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }
    
    @PreAuthorize("hasRole('ROLE_DEALER')")
    @RequestMapping(path = "/policyapi/transactionid/{transactionId}", method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> policyAPI(@PathVariable("transactionId") String transactionId){
        try {
            String response = coverfoxService.policyAPI(transactionId);
            log.info("response=> " + response);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode node = objectMapper.readValue(response, JsonNode.class);
                cfxTxnDtlService.updatePolicy(transactionId, node.get("success").toString(), node.get("policy_number").toString(), node.get("policy_document").toString());
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Some Error Occured while API calling.");
        }
    }
}
