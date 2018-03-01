/**
 * 
 */
package org.rta.registration.service.coverfox.impl;

import org.apache.log4j.Logger;
import org.rta.core.model.insurance.CfxTxnDtlModel;
import org.rta.coverfox.model.CustomerCfx;
import org.rta.coverfox.model.InsAddOn;
import org.rta.coverfox.model.PaymentCfxModel;
import org.rta.coverfox.model.VehicleCfx;
import org.rta.coverfox.service.CoverfoxAPIService;
import org.rta.registration.service.coverfox.CoverfoxService;
import org.rta.registration.service.coverfox.helper.CoverfoxHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author arun.verma
 *
 */

@Service
public class CoverfoxServiceImpl implements CoverfoxService{
    
    private static final Logger log = Logger.getLogger(CoverfoxServiceImpl.class);
    
    @Autowired
    CoverfoxAPIService cfsApiService;
    
    //"fourwheeler" or "twowheeler"
    String vehicleType = "twowheeler";
    String modelId = "2192";
    String vehicleId = "7092";
    String fuelType = "Petrol";
    
    @Override
    public String getModels() throws RuntimeException {
        ResponseEntity<?> response = cfsApiService.getVehicleModels(vehicleType);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String getModelVariants() throws RuntimeException {
        ResponseEntity<?> response = cfsApiService.getVehicleModelVariants(vehicleType, modelId, fuelType);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }
    
    @Override
    public String getQuotes() throws RuntimeException{
        VehicleCfx vehicle = CoverfoxHelper.getVehicleDetails();
        InsAddOn addOn = CoverfoxHelper.getAddOn();
        ResponseEntity<?> response = cfsApiService.getQuotes(vehicleType, vehicleId, vehicle, addOn);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String refreshQuote(String quoteId, String insurerSlug) throws RuntimeException{
        VehicleCfx vehicle = CoverfoxHelper.getVehicleDetails();
        InsAddOn addOn = CoverfoxHelper.getAddOn();
        ResponseEntity<?> response = cfsApiService.refreshQuote(vehicleType, quoteId, insurerSlug, vehicleId, vehicle, addOn);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String confirmQuote(String quoteId, String insurerSlug) throws RuntimeException{
        VehicleCfx vehicle = CoverfoxHelper.getVehicleDetails();
        InsAddOn addOn = CoverfoxHelper.getAddOn();
        ResponseEntity<?> response = cfsApiService.confirmQuote(vehicleType, quoteId, insurerSlug, vehicleId, vehicle, addOn);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String getStates(String insurerSlug) throws RuntimeException{
        ResponseEntity<?> response = cfsApiService.getStates(vehicleType, insurerSlug);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String getCities(String insurerSlug) throws RuntimeException{
        String stateCode = "MH";
        ResponseEntity<?> response = cfsApiService.getCities(stateCode, vehicleType, insurerSlug);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String getLoanProviders(String insurerSlug) throws RuntimeException{
        ResponseEntity<?> response = cfsApiService.getLoanProviders(vehicleType, insurerSlug);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String buy(String insurerSlug, String transactionId) throws RuntimeException{
        VehicleCfx vehicle = CoverfoxHelper.getVehicleDetails();
        CustomerCfx customer = CoverfoxHelper.getCustomerDetails();
        ResponseEntity<?> response = cfsApiService.buy(vehicleType, insurerSlug, transactionId, vehicle, customer);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String retrieveConfirmedQuote(String transactionId) throws RuntimeException{
        ResponseEntity<?> response = cfsApiService.retrieveConfirmedQuote(vehicleType, transactionId);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String retrieveProposalForm(String transactionId) throws RuntimeException{
        ResponseEntity<?> response = cfsApiService.retrieveProposalForm(vehicleType, transactionId);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String retrieveStatusDetails(String transactionId) throws RuntimeException{
        ResponseEntity<?> response = cfsApiService.retrieveStatusDetails(vehicleType, transactionId);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String prePayment(String transactionId) throws RuntimeException {
        ResponseEntity<?> response = cfsApiService.prePayment(transactionId);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String postPayment(CfxTxnDtlModel cfxTxnDtlModel) throws RuntimeException {
        PaymentCfxModel paymentCfxModel = CoverfoxHelper.getPaymentData(cfxTxnDtlModel);
        ResponseEntity<?> response = cfsApiService.postPayment(paymentCfxModel);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

    @Override
    public String policyAPI(String transactionId) throws RuntimeException {
        ResponseEntity<?> response = cfsApiService.policyAPI(transactionId);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.info("API calling Failed : HTTP error code : " + response.getStatusCode());
            throw new RuntimeException("API calling Failed : HTTP error code : " + response.getStatusCode());
        }
        return response.getBody().toString();
    }

}
