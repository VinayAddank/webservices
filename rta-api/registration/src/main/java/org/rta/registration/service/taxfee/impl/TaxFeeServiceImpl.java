package org.rta.registration.service.taxfee.impl;

import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.vehicle.VehicleBillingDetailsDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.vehicle.VehicleBillingDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.VehicleCategory;
import org.rta.core.enums.master.TaxType;
import org.rta.core.model.citizen.CitizenServiceResponseModel;
import org.rta.core.model.payment.TaxFeeDetailModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.HSRPModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.model.payment.tax.CessFeeModel;
import org.rta.core.model.payment.tax.DifferentialTaxFeeModel;
import org.rta.core.model.payment.tax.TaxDetailModel;
import org.rta.core.model.payment.tax.TaxRuleModel;
import org.rta.core.service.citizen.CitizenService;
import org.rta.core.service.hsrp.HSRPDetailService;
import org.rta.core.service.payment.registfee.RegFeeDetailService;
import org.rta.core.service.payment.tax.TaxDetailService;
import org.rta.core.utils.NumberParser;
import org.rta.registration.service.CommunicationService;
import org.rta.registration.service.taxfee.TaxFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TaxFeeServiceImpl implements TaxFeeService {
	
	private static final Logger log = Logger.getLogger(TaxFeeServiceImpl.class);

	@Value("${drools.tax.url}")
	private String droolsTaxURL;
	
	@Value("${drools.fee.url}")
	private String droolsFeeURL;
	
	@Value("${hsrp.contenttype}")
	String contenttype;
	
	@Autowired
    private TaxDetailService taxDetailService;
    
	@Autowired
    private RegFeeDetailService regFeeDetailService;
    
	@Autowired
    CommunicationService communicationService;
    
	@Autowired
	public VehicleDetailsDAO vehicleDetailsDAO;
	
	@Autowired
	public VehicleBillingDetailsDAO vehicleBillingDetailsDAO;
	
	@Autowired
	public HSRPDetailService hsrpDetailService;
	
	@Autowired
	private CitizenService citizenService;
	
	private static final RestTemplate restTemplate = new RestTemplate();

	@Override
	public TaxRuleModel getTaxByRule(TaxRuleModel taxRuleModel) {
		ResponseEntity<String> responseEntity = callAPIPost4Tax(droolsTaxURL, taxRuleModel, contenttype);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			ObjectMapper mapper = new ObjectMapper();
			TaxRuleModel taxRuleModelResponse = new TaxRuleModel();
			try {
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				taxRuleModelResponse = mapper.readValue(responseEntity.getBody(), TaxRuleModel.class);
			} catch (IOException e) {
				log.error("getTaxByRule Object json parser Internal Server Error: " + e.getMessage());
				e.printStackTrace();
				throw new IllegalArgumentException(" getTaxByRule Object json parser Internal Server Error:");
			}
			log.debug(":::getTaxByRule::: " + responseEntity.getBody());
			return taxRuleModelResponse;
		} else {
			log.error(":::getTaxByRule:::Internal Server Error:::::: " + responseEntity.getStatusCode());
			throw new IllegalArgumentException(" getTaxByRule Internal Server Error:");
		}
	}

	public ResponseEntity<String> callAPIPost4Tax(String apiPath, TaxRuleModel taxRuleModel, String contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", contentType);
		HttpEntity<TaxRuleModel> entity = new HttpEntity<TaxRuleModel>(taxRuleModel, headers);
		try {
			return restTemplate.exchange(apiPath, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			log.error("callAPIPost4Tax Internal Server Error: Calling Drolls API's " + e.getMessage());
			e.printStackTrace();
			throw new IllegalArgumentException(" callAPIPost4Tax Internal Server Error: Calling Drolls API's");
		}

	}

	@Override
	public FeeRuleModel getFeeByRule(FeeRuleModel feeRuleModel) {
		ResponseEntity<String> responseEntity = callAPIPost4Fee(droolsFeeURL, feeRuleModel, contenttype);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			ObjectMapper mapper = new ObjectMapper();
			FeeRuleModel feeRuleModelResponse = new FeeRuleModel();
			try {
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				feeRuleModelResponse = mapper.readValue(responseEntity.getBody(), FeeRuleModel.class);
			} catch (IOException e) {
				log.error("getFeeByRule Object json parser Internal Server Error: " + responseEntity.getStatusCode());
				e.printStackTrace();
				throw new IllegalArgumentException(" getFeeByRule Object json parser Internal Server Error:");
			}
			log.debug("::getFeeByRule::: " + responseEntity.getBody());
			return feeRuleModelResponse;
		} else {
			log.error("::getFeeByRule::Internal Server Error: " + responseEntity.getStatusCode());
			throw new IllegalArgumentException(" getFeeByRule Internal Server Error:");
		}
	}

	public ResponseEntity<String> callAPIPost4Fee(String apiPath, FeeRuleModel feeRuleModel, String contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", contentType);
		HttpEntity<FeeRuleModel> entity = new HttpEntity<FeeRuleModel>(feeRuleModel, headers);
		try {
			return restTemplate.exchange(apiPath, HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			log.error("callAPIPost4Fee Internal Server Error: Calling Drolls API's " + e.getMessage());
			e.printStackTrace();
			throw new IllegalArgumentException(" getFeeByRule Internal Server Error: Calling Drolls API's");
		}

	}

	@Override
	@Transactional
	public TaxFeeDetailModel getTaxFee(Long vehicleRcId , String userName) {
		log.debug(":::getTaxFee:::: " + vehicleRcId);
		TaxRuleModel taxRuleModel = new TaxRuleModel();
        FeeRuleModel feeRuleModel = new FeeRuleModel();
        TaxDetailModel taxDetailModel = new TaxDetailModel();
        TaxFeeDetailModel taxFeeDetailModel = new TaxFeeDetailModel();
        RegFeeDetailModel regFeeDetailModel = new RegFeeDetailModel();
        HSRPModel hsrpModel = new HSRPModel();
        CessFeeModel cessFeeModel = new CessFeeModel();
        feeRuleModel.setServiceCode(ServiceType.FRESH_REGISTRATION.getCode());
        taxRuleModel.setServiceCode(ServiceType.FRESH_REGISTRATION.getCode());
        VehicleDetailsEntity vehicleDetailEntity = null;
        VehicleBillingDetailsEntity vehicleBillingDetailsEntity = null;
        vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
        vehicleBillingDetailsEntity = vehicleBillingDetailsDAO.getByVehicleDetailId(vehicleDetailEntity);
        if (vehicleDetailEntity == null && vehicleBillingDetailsEntity == null)
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        
        taxRuleModel = taxDetailService.getTaxRuleModel(vehicleRcId, userName, taxRuleModel , vehicleDetailEntity , vehicleBillingDetailsEntity);
        taxRuleModel = getTaxByRule(taxRuleModel);
        
        feeRuleModel = regFeeDetailService.getFeeRuleModel(vehicleRcId, userName, feeRuleModel , vehicleDetailEntity);
        feeRuleModel = getFeeByRule(feeRuleModel);
        
        taxDetailModel = taxDetailService.saveTaxDetails(vehicleRcId, userName, taxRuleModel , feeRuleModel , vehicleDetailEntity , vehicleBillingDetailsEntity);
        regFeeDetailModel = regFeeDetailService.saveFeeDetails(vehicleRcId, userName, feeRuleModel , vehicleDetailEntity);
        hsrpModel = hsrpDetailService.saveHSRP(userName, feeRuleModel, vehicleDetailEntity);
        
        double cessFee = 0.0d;
        double hpaFee = 0.0d;
        double trFee = 0.0d;
        double trServiceCharges = 0.0d;
        double taxAmt = 0.0d;
        double totalTrFee = 0.0d;
        
        double prFee = 0.0d;
        double postalFee = 0.0d;
        double cardFee = 0.0d;
        double prServiceCharges = 0.0d;
        double totalPrFee = 0.0d;
        if(taxRuleModel.getRegCategory() == RegistrationCategoryType.TRANSPORT.getValue() && taxRuleModel.getTaxType().equalsIgnoreCase(TaxType.FIRST_QUARTERLY.getCode())){
        	cessFeeModel = taxDetailService.cessFeeDetails(vehicleDetailEntity ,userName, taxRuleModel);
        	if(cessFeeModel != null)
        	cessFee = cessFeeModel.getCessFee();
        }
        taxDetailModel.setHsrpFee(NumberParser.numberFormat(hsrpModel.getHsrpAmount()));
        taxDetailModel.setTrFee(regFeeDetailModel.getTrFee());
        taxDetailModel.setTrServiceCharge(regFeeDetailModel.getTrServiceCharges());
        taxDetailModel.setHpaFee(regFeeDetailModel.getHpaFee());
        taxDetailModel.setCessFee(NumberParser.numberFormat(cessFee));
        taxDetailModel.setPenalty("0.00");
        taxDetailModel = taxDetailService.isPaymentVerification(vehicleRcId, taxDetailModel);
        
        taxAmt = new Double(taxDetailModel.getTotalAmt());
        trFee = new Double(regFeeDetailModel.getTrFee());
        trServiceCharges = new Double(taxDetailModel.getTrServiceCharge());
        hpaFee = new Double(regFeeDetailModel.getHpaFee());
        totalTrFee = taxAmt + trFee  + trServiceCharges + hpaFee + cessFee ;
        taxDetailModel.setGrandTotal(NumberParser
                .numberFormat(NumberParser.roundOff(totalTrFee, "########.##")));

        prFee = new Double(regFeeDetailModel.getRegFee());
        postalFee = new Double(regFeeDetailModel.getPostalCharge());
        cardFee = new Double(regFeeDetailModel.getSmartCardFee());
        prServiceCharges = new Double(regFeeDetailModel.getServiceCharge());
        totalPrFee = prFee + postalFee  + cardFee + prServiceCharges ;
        regFeeDetailModel.setTotalRegFee(NumberParser
                .numberFormat(NumberParser.roundOff(totalPrFee , "########.##")));

        taxFeeDetailModel.setTaxDetailModel(taxDetailModel);
        taxFeeDetailModel.setRegFeeDetailModel(regFeeDetailModel);
        
        double specilNoFee = new Double(regFeeDetailModel.getSpecialNumberFee()).doubleValue();
        double fitnessFee = 0.0d;
        switch (RegistrationCategoryType.getRegistrationCategoryType(taxRuleModel.getRegCategory())) {
		case TRANSPORT:
			fitnessFee = new Double(regFeeDetailModel.getFitnessFeeModel().getTotalFitnessFee());
			break;
		}
        taxFeeDetailModel.setHsrpAmount(taxDetailModel.getHsrpFee());
        taxFeeDetailModel.setGrandTotal(NumberParser
                .numberFormat(NumberParser.roundOff( totalTrFee + totalPrFee +specilNoFee + hsrpModel.getHsrpAmount() + fitnessFee , "########.##")));
        return taxFeeDetailModel;
	}

	
	@Override
	@Transactional
	public TaxFeeDetailModel viewTaxFee(Long vehicleRcId , String userName, String token) {
		log.debug(":::viewTaxFee:::: " + vehicleRcId);
		TaxDetailModel taxDetailModel = new TaxDetailModel();
        TaxFeeDetailModel taxFeeDetailModel = new TaxFeeDetailModel();
        RegFeeDetailModel regFeeDetailModel = new RegFeeDetailModel();
        HSRPModel hsrpModel = new HSRPModel();
        CessFeeModel cessFeeModel = new CessFeeModel();
        VehicleDetailsEntity vehicleDetailEntity = null;
        VehicleBillingDetailsEntity vehicleBillingDetailsEntity = null;
        vehicleDetailEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
        vehicleBillingDetailsEntity = vehicleBillingDetailsDAO.getByVehicleDetailId(vehicleDetailEntity);
        if (vehicleDetailEntity == null && vehicleBillingDetailsEntity == null)
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        
        taxDetailModel = taxDetailService.viewTaxDetails(vehicleRcId, userName,  vehicleDetailEntity , vehicleBillingDetailsEntity);
        regFeeDetailModel = regFeeDetailService.viewFeeDetails(vehicleRcId, userName, vehicleDetailEntity);
        taxDetailModel.setTrFee(regFeeDetailModel.getTrFee());
        taxDetailModel.setTrServiceCharge(regFeeDetailModel.getTrServiceCharges());
        hsrpModel = hsrpDetailService.viewHSRP(userName,vehicleDetailEntity);
        double cessFee = 0.0d;
        double hpaFee = 0.0d;
        double trFee = 0.0d;
        double trServiceCharges = 0.0d;
        double taxAmt = 0.0d;
        double totalTrFee = 0.0d;
        
        double prFee = 0.0d;
        double postalFee = 0.0d;
        double cardFee = 0.0d;
        double prServiceCharges = 0.0d;
        double totalPrFee = 0.0d;
        cessFeeModel = taxDetailService.viewCessFeeDetails(vehicleDetailEntity);
        if(cessFeeModel != null)
        	cessFee = cessFeeModel.getCessFee();
        taxDetailModel.setHsrpFee(NumberParser.numberFormat(hsrpModel.getHsrpAmount()));
        taxDetailModel.setTrFee(regFeeDetailModel.getTrFee());
        taxDetailModel.setTrServiceCharge(regFeeDetailModel.getTrServiceCharges());
        taxDetailModel.setHpaFee(regFeeDetailModel.getHpaFee());
        taxDetailModel.setCessFee(NumberParser.numberFormat(cessFee));
        taxDetailModel.setPenalty("0.00");
        
        taxAmt = new Double(taxDetailModel.getTotalAmt());
        trFee = new Double(regFeeDetailModel.getTrFee());
        trServiceCharges = new Double(regFeeDetailModel.getTrServiceCharges());
        hpaFee = new Double(regFeeDetailModel.getHpaFee());
        totalTrFee = taxAmt + trFee  + trServiceCharges + hpaFee + cessFee ;
        taxDetailModel.setGrandTotal(NumberParser
                .numberFormat(NumberParser.roundOff(totalTrFee, "########.##")));

        prFee = new Double(regFeeDetailModel.getRegFee());
        postalFee = new Double(regFeeDetailModel.getPostalCharge());
        cardFee = new Double(regFeeDetailModel.getSmartCardFee());
        prServiceCharges = new Double(regFeeDetailModel.getServiceCharge());
        totalPrFee = prFee + postalFee  + cardFee + prServiceCharges ;
        regFeeDetailModel.setTotalRegFee(NumberParser
                .numberFormat(NumberParser.roundOff(totalPrFee , "########.##")));
        taxDetailModel = taxDetailService.isPaymentVerification(vehicleRcId, taxDetailModel);
		taxFeeDetailModel.setTaxDetailModel(taxDetailModel);
        taxFeeDetailModel.setRegFeeDetailModel(regFeeDetailModel);
        
        double specilNoFee = new Double(regFeeDetailModel.getSpecialNumberFee()).doubleValue();
        double fitnessFee = 0.0d;
        if(regFeeDetailModel.getFitnessFeeModel() != null)
			fitnessFee = new Double(regFeeDetailModel.getFitnessFeeModel().getTotalFitnessFee());
		taxFeeDetailModel.setHsrpAmount(taxDetailModel.getHsrpFee());
		
        taxFeeDetailModel.setGrandTotal(NumberParser
                .numberFormat(NumberParser.roundOff( totalTrFee + totalPrFee +specilNoFee + hsrpModel.getHsrpAmount() + fitnessFee , "########.##")));
        if(vehicleDetailEntity.getVehicleCategory() == VehicleCategory.CHASSIS_ONLY 
        		&& vehicleDetailEntity.getVehicleRcId().getPrStatus() == Status.PENDING.getValue() ){
        	CitizenServiceResponseModel<DifferentialTaxFeeModel> response = 
        			citizenService.getDiffertialTaxFeeDetails(vehicleDetailEntity.getVehicleRcId().getTrNumber(), token);
			if(response.getHttpStatus() == HttpStatus.OK){
				taxFeeDetailModel.setDifferentialTaxFeeModel(response.getResponseBody());
				log.info("Differential Tax Fee :::::::::: ");
			}	
        }
        return taxFeeDetailModel;
	}


}
