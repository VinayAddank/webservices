package org.rta.registration.service.vahan.impl;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.rta.core.exception.ConflictException;
import org.rta.core.exception.FoundException;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.service.vehicle.VahanServiceCore;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.registration.service.vahan.UnregisteredVahanService;
import org.rta.secondvehicle.api.ChassisEngineCheckService;
import org.rta.vahan.api.ResponseType;
import org.rta.vahan.api.VahanResponseModel;
import org.rta.vahan.api.unregistered.UnregisteredVahanClient;
import org.rta.vahan.api.unregistered.model.UnregisteredChassisInfo;
import org.rta.vahan.exception.IllegalEngineNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
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
public class UnregisteredVahanServiceImpl implements UnregisteredVahanService {

    private static final Logger logger = Logger.getLogger(UnregisteredVahanServiceImpl.class);

    @Autowired
    private UnregisteredVahanClient vahanClient;

    @Autowired
    private ConversionService conversionService;
    
    @Autowired
    private VahanServiceCore vahanService;
    
    @Autowired
    private ChassisEngineCheckService chassisEngineCheckService;

    @Autowired
    VehicleService vehicleService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String BHARAT_STAGE_III = "BHARAT STAGE III";
    
    @Override
    public UnregisteredVehicleModel getChassisInfo(String chassisNumber, String fullEngineNumber , boolean migration)
            throws InvalidEngineNumberException, ConflictException, FoundException {
        
        VehicleDetailsRequestModel vrModel = vehicleService.getVehicleDetailsByChassisNo(chassisNumber);
        if(!ObjectsUtil.isNull(vrModel)){
            throw new ConflictException(vrModel.getVehicleRcId());
        }
        
         if(chassisEngineCheckService.checkChassisEngineNo(fullEngineNumber, chassisNumber)) {
          throw new FoundException(); }
         
         
        /*VahanResponseModel<UnregisteredChassisInfo> vrm;
        try {
            vrm = vahanClient.getChassisInfo(chassisNumber, fullEngineNumber);
            if (ObjectsUtil.isNull(vrm) || vrm.getResponseType() == ResponseType.ERROR) {
                return null;
            }
            if (!fullEngineNumber.equalsIgnoreCase(vrm.getResponseModel().getEngineNumber())) {
                logger.error("engine number doesn't match!");
                throw new IllegalEngineNumberException("engine number is not correct");
            }
        } catch (IllegalEngineNumberException e) {
            logger.error(e.getMessage());
            throw new InvalidEngineNumberException("engine number invalid");
        }
        Long generatedId;
        UnregisteredVehicleModel uvm = conversionService.convert(vrm.getResponseModel(), UnregisteredVehicleModel.class);*/
        // Save vehicle info with full engine number
//        uvm.setEngineNumber(fullEngineNumber);
        UnregisteredVehicleModel uvm = new UnregisteredVehicleModel();
        if(migration){
        	logger.info(":::Is migration::::::start:: " + migration);
        	try {
				ResponseEntity<String> responseEntity = setMigration( chassisNumber,  fullEngineNumber);
				if (responseEntity.getStatusCode() == HttpStatus.OK) {
				    ObjectMapper mapper = new ObjectMapper();
				    UnregisteredVehicleModel unregisteredVehicleModel = new UnregisteredVehicleModel();
				    try {
				    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				    	unregisteredVehicleModel = mapper.readValue(responseEntity.getBody(), UnregisteredVehicleModel.class);
				    } catch (IOException e) {
				        e.printStackTrace();
				        throw new IllegalArgumentException(" ismigration Object json parser Internal Server Error:");
				    }
				    logger.info("::::::ismigration:::::end:::::: " + responseEntity.getBody());
				    Long generatedId;
				    generatedId = vahanService.save(unregisteredVehicleModel);
				    unregisteredVehicleModel.setVahanId(generatedId);
				    return unregisteredVehicleModel;
				} else {
					logger.info(":::::::ismigration:::::Internal Server Error:::::: " +responseEntity.getStatusCode() +" " + responseEntity.getBody());
				    throw new IllegalArgumentException(" ismigration Internal Server Error:");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }else{
        	VahanResponseModel<UnregisteredChassisInfo> vrm;
            try {
                vrm = vahanClient.getChassisInfo(chassisNumber, fullEngineNumber);
                if (ObjectsUtil.isNull(vrm) || vrm.getResponseType() == ResponseType.ERROR) {
                    return null;
                }
                if (!fullEngineNumber.equalsIgnoreCase(vrm.getResponseModel().getEngineNumber())) {
                    logger.error("engine number doesn't match!");
                    throw new IllegalEngineNumberException("engine number is not correct");
                }
            } catch (IllegalEngineNumberException e) {
                logger.error(e.getMessage());
                throw new InvalidEngineNumberException("engine number invalid");
            }
            Long generatedId;
            uvm = conversionService.convert(vrm.getResponseModel(), UnregisteredVehicleModel.class);
        	generatedId = vahanService.save(uvm);
            uvm.setVahanId(generatedId);
        }
        return uvm;
    }

    
    public ResponseEntity<String> setMigration(String chassisNumber, String fullEngineNumber){
    	logger.info("::::::::::callMigration:::::start::::::");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<UnregisteredVehicleModel> entity = new HttpEntity<UnregisteredVehicleModel>(headers);
        try {
            return restTemplate.exchange("http://preprodapp.trafficmanager.net:8080/registration/vehicle/migration/"+chassisNumber+"/"+fullEngineNumber, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(" callMigration Internal Server Error: Calling migration API's");
        }
    }


	@Override
	public UnregisteredVehicleModel saveMIgrate(UnregisteredVehicleModel unregisteredVehicleModel) {
		logger.info("::saveMIgrate::on migration server::::start:::");
		Long generatedId;
		generatedId = vahanService.save(unregisteredVehicleModel);
		unregisteredVehicleModel.setVahanId(generatedId);
		logger.info("::saveMIgrate::on migration server::::end::: " + generatedId);
		return unregisteredVehicleModel;
	}
    
   

}
