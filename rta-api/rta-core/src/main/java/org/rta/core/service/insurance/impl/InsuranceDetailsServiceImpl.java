package org.rta.core.service.insurance.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.insurance.InsuranceDetailsDAO;
import org.rta.core.dao.master.InsuranceTypeDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.entity.insurance.InsuranceDetailsEntity;
import org.rta.core.entity.master.InsuranceTypeEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.Status;
import org.rta.core.exception.InsurancePolicyValidationException;
import org.rta.core.exceptioncode.InsurancePolicyValidationExceptionCode;
import org.rta.core.helper.insurance.InsuranceDetailsHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.insurance.IibResponseModel;
import org.rta.core.model.insurance.InsuranceDetailsModel;
import org.rta.core.model.user.DealerModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.insurance.InsuranceDetailsService;
import org.rta.core.service.user.DealerService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InsuranceDetailsServiceImpl implements InsuranceDetailsService {
	
	private static final Logger log = Logger.getLogger(InsuranceDetailsServiceImpl.class);
	
    @Autowired
    InsuranceDetailsDAO insuranceDetailsDAO;
    @Autowired
    InsuranceDetailsHelper insuranceDetailsHelper;
    @Autowired
    InsuranceTypeDAO insuranceTypeDAO;
    @Autowired
    VehicleDetailsDAO vehicleDetailsDAO;
    @Autowired
    VehicleDAO vehicleDAO;
    @Value("${rta.step.insurance}")
    Integer currentStep;
    @Autowired
    DealerService dealerService;
    @Autowired
    RestTemplate restTemplete;
    @Value("${rta.iib.url}")
    String rtaIibUrl;
    @Override
    @Transactional
    public List<InsuranceDetailsModel> getAll() {
        return (List<InsuranceDetailsModel>) insuranceDetailsHelper.convertToModelList(insuranceDetailsDAO.getAll());
    }

    @Override
    @Transactional
    public SaveUpdateResponse saveUpdate(InsuranceDetailsModel insuranceDetails, UserModel user)
            throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
        Long vehicleRcId = Long.parseLong(insuranceDetails.getVehicleRcId());
        
        VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
        if (vehicleDetailsEntity == null) {
            log.error("Vehicle Details not found for given VehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Vehicle Details not found for given VehicleRcId !");
        }
        insuranceDetails.setStatus(Status.FRESH.getValue());
        
        InsuranceDetailsEntity updateEntity = insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
        
        VehicleRCEntity vehicRcEntity = vehicleDetailsEntity.getVehicleRcId();
        //duplicate policy number should not be allowed
        InsuranceDetailsModel insuranceMdl = getInsuranceDtlsByPolicyNo(insuranceDetails.getPolicyNo());
        if(!ObjectsUtil.isNull(insuranceMdl)){
            if(ObjectsUtil.isNull(updateEntity) || ObjectsUtil.isNull(updateEntity.getInsuranceDtlId()) || insuranceMdl.getInsuranceDtlId().compareTo(updateEntity.getInsuranceDtlId()) != 0){
                
                DealerModel dealer = dealerService.findDealerByUserId(vehicRcEntity.getUserId().getUserId());
                String duplicateMsg = "Provided policy number is already exist with Dealer : " + dealer.getShowRoomName();
                SaveUpdateResponse saveUpdateModel =
                        new SaveUpdateResponse(SaveUpdateResponse.FAILURE, duplicateMsg, String.valueOf(insuranceMdl.getVehicleRcId()), currentStep);
                saveUpdateModel.setCode(HttpStatus.IM_USED.value());
                return saveUpdateModel;
            }
        }
        InsuranceDetailsEntity entity = insuranceDetailsHelper.convertToEntity(insuranceDetails, updateEntity);
        
        // set VehicleDetailsEntity and in InsuranceDetailsEntity
        entity.setVehicleDetailsEntity(vehicleDetailsEntity);
        if (vehicRcEntity == null) {
            log.error("Invalid vehicleRcId : " + vehicleRcId);
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        }
        
        Long timeStamp = DateUtil.toCurrentUTCTimeStamp();

        // set InsuranceTypeEntity in InsuranceDetailsEntity
        InsuranceTypeEntity insuranceTypeEntity = insuranceTypeDAO.getByCode(insuranceDetails.getInsuranceTypeCode());
        if (insuranceTypeEntity == null) {
        	log.error("Invalid InsuranceTypeCode : " + insuranceDetails.getInsuranceTypeCode());
            throw new IllegalArgumentException("Invalid InsuranceTypeCode !");
        }
        entity.setInsuranceTypeEntity(insuranceTypeEntity);

        String msg = "";
        if (ObjectsUtil.isNotNull(entity.getInsuranceDtlId())) {
            // update insurance details
            msg = "Updated Successfully.";
            entity.setModifiedBy(user.getUserName());
            entity.setModifiedOn(timeStamp);
        } else {
            // save insurance details
            msg = "Saved Successfully.";
            entity.setCreatedBy(user.getUserName());
            entity.setCreatedOn(timeStamp);
            vehicRcEntity.setCurrentStep(currentStep);
            vehicRcEntity.setModifiedBy(user.getUserName());
            vehicRcEntity.setModifiedOn(timeStamp);
        }
        entity.setVehicleRcEntity(vehicRcEntity);

        insuranceDetailsDAO.saveOrUpdate(entity);
        SaveUpdateResponse saveUpdateModel =
                new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, String.valueOf(vehicleRcId), currentStep);
        saveUpdateModel.setCode(HttpStatus.OK.value());
        return saveUpdateModel;
    }

    @Override
    @Transactional
    public InsuranceDetailsModel getInsuranceDtlsByVehicleRcId(Long vehicleRcId) {
        InsuranceDetailsEntity insuranceDetailsEntity =
                insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
        if (insuranceDetailsEntity == null) {
            throw new IllegalArgumentException("Invalid vehicleRcId !");
        }
        InsuranceDetailsModel insDtlMdl = insuranceDetailsHelper.convertToModel(insuranceDetailsEntity);
        return insDtlMdl;
    }
    
    @Override
    @Transactional
    public InsuranceDetailsModel getInsuranceDtlsByPolicyNo(String policyNo) {
        InsuranceDetailsEntity insuranceDetailsEntity = insuranceDetailsDAO.getInsuranceDtlsByPolicyNo(policyNo);
        InsuranceDetailsModel insDtlMdl = insuranceDetailsHelper.convertToModel(insuranceDetailsEntity);
        if(!ObjectsUtil.isNull(insuranceDetailsEntity)){
            insuranceDetailsDAO.getSession().evict(insuranceDetailsEntity);
        }
        return insDtlMdl;
    }
    
    @Override
	@Transactional
	public boolean isValidInsurancePolicy(String vehicleRcId) {
		try{
			IibResponseModel iibResponseModel=new IibResponseModel();
		//InsuranceDetailsEntity insuranceDetailsEntity=insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
		/*if(insuranceDetailsEntity==null){
			throw new InsurancePolicyValidationException(InsurancePolicyValidationExceptionCode.INSURANCE_POLICY_NOT_FOUND, "");
		}*/
			HttpHeaders httpHeaders=new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			  httpHeaders.set("Authorization", "7efa25ed-80f3-491c-84af-6bd9a3a4ba10");
			HttpEntity<String> httpEntity=new HttpEntity<String>(httpHeaders);
			//http://<URL>:<port></iib/policydetails/regno?regNo=AP23RT1239

			String url=rtaIibUrl+"?regNo="+vehicleRcId;
			ResponseEntity<IibResponseModel> response= restTemplete.exchange(url, HttpMethod.GET, httpEntity,IibResponseModel.class);
			if(response.getStatusCode()==HttpStatus.OK){
				iibResponseModel=response.getBody();
			}else{
				throw new InsurancePolicyValidationException(InsurancePolicyValidationExceptionCode.INSURANCE_POLICY_NOT_FOUND, "");

			}if(!ObjectsUtil.isNull(iibResponseModel)){
				return true;
			}
		}catch (InsurancePolicyValidationException e) {
			throw new InsurancePolicyValidationException(InsurancePolicyValidationExceptionCode.INVALID_INSURANCE_POLICY, "");

		}
		return false;
	}

    @Transactional(dontRollbackOn = Exception.class)
    @Override
    public InsuranceDetailsModel getIibInsuranceDetailsByVehicleRcId(Long vehicleRcId) {
        IibResponseModel iibResponseModel = new IibResponseModel();
        VehicleRCEntity vehicleRCEntity = vehicleDAO.get(vehicleRcId);
        String regNo = vehicleRCEntity.getPrNumber();
        try {
            if (vehicleRcId == null) {
                throw new InsurancePolicyValidationException(
                        InsurancePolicyValidationExceptionCode.INSURANCE_POLICY_NOT_FOUND, "");
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            httpHeaders.set("Authorization", "7efa25ed-80f3-491c-84af-6bd9a3a4ba10");
            HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
            String url = rtaIibUrl + "?regNo=" + regNo;
            ResponseEntity<IibResponseModel> response =
                    restTemplete.exchange(url, HttpMethod.GET, httpEntity, IibResponseModel.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                iibResponseModel = response.getBody();
                // Long _vehicleRcId = Long.parseLong(vehicleRcId);

                VehicleDetailsEntity vehicleDetailsEntity = vehicleDetailsDAO.getByVehicleRcId(vehicleRcId);
                if (vehicleDetailsEntity == null) {
                    log.error("Vehicle Details not found for given VehicleRcId : " + vehicleRcId);
                    throw new IllegalArgumentException("Vehicle Details not found for given VehicleRcId !");
                }
                if (!ObjectsUtil.isNull(iibResponseModel.getResult())) {
                    iibResponseModel.getResult().setPolicyStatus(String.valueOf(Status.IIB_VERIFIED.getValue()));
                    InsuranceDetailsEntity updateEntity =
                            insuranceDetailsDAO.getInsuranceDtlsEntityByVehicleRcId(vehicleRcId);
                    VehicleRCEntity vehicRcEntity = vehicleDetailsEntity.getVehicleRcId();
                    // duplicate policy number should not be allowed
                    InsuranceDetailsModel insuranceMdl =
                            getInsuranceDtlsByPolicyNo(iibResponseModel.getResult().getPolicyNo());
                    if (!ObjectsUtil.isNull(insuranceMdl)) {
                        if (ObjectsUtil.isNull(updateEntity) || ObjectsUtil.isNull(updateEntity.getInsuranceDtlId())
                                || insuranceMdl.getInsuranceDtlId().compareTo(updateEntity.getInsuranceDtlId()) != 0) {

                            DealerModel dealer =
                                    dealerService.findDealerByUserId(vehicRcEntity.getUserId().getUserId());
                            String duplicateMsg =
                                    "Provided policy number is already exist with Dealer : " + dealer.getShowRoomName();
                            SaveUpdateResponse saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.FAILURE,
                                    duplicateMsg, String.valueOf(insuranceMdl.getVehicleRcId()), currentStep);
                            saveUpdateModel.setCode(HttpStatus.IM_USED.value());
                            return insuranceDetailsHelper.convertIibResModelToInsuranceDetailsModel(iibResponseModel);
                        }
                    }
                    InsuranceDetailsEntity entity =
                            insuranceDetailsHelper.convertToEntity(iibResponseModel, updateEntity);
                    // set VehicleDetailsEntity and in InsuranceDetailsEntity
                    entity.setVehicleDetailsEntity(vehicleDetailsEntity);
                    if (vehicRcEntity == null) {
                        log.error("Invalid vehicleRcId : " + vehicleRcId);
                        throw new IllegalArgumentException("Invalid vehicleRcId !");
                    }
                    Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
                    // set InsuranceTypeEntity in InsuranceDetailsEntity
                    InsuranceTypeEntity insuranceTypeEntity = insuranceTypeDAO.getByCode("OTHER");
                    if (insuranceTypeEntity == null) {
                        log.error("Invalid InsuranceTypeCode : " + "OTHER");
                        throw new IllegalArgumentException("Invalid InsuranceTypeCode !");
                    }
                    entity.setInsuranceTypeEntity(insuranceTypeEntity);
                    String msg = "";
                    if (ObjectsUtil.isNotNull(entity.getInsuranceDtlId())) {
                        // update insurance details
                        msg = "Updated Successfully.";
                        entity.setModifiedBy(regNo);
                        entity.setModifiedOn(timeStamp);
                        // insuranceDetailsDAO.update(entity);
                    } else {
                        // save insurance details
                        msg = "Saved Successfully.";
                        entity.setCreatedBy(regNo);
                        entity.setCreatedOn(timeStamp);
                        vehicRcEntity.setCurrentStep(currentStep);
                        vehicRcEntity.setModifiedBy(regNo);
                        vehicRcEntity.setModifiedOn(timeStamp);
                        entity.setVehicleRcEntity(vehicRcEntity);
                    }
                    insuranceDetailsDAO.saveOrUpdate(entity);
                    SaveUpdateResponse saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg,
                            String.valueOf(vehicleRcId), currentStep);
                    saveUpdateModel.setCode(HttpStatus.OK.value());
                }
            } else {
                log.info("Error while getting insurance details from iib");
                throw new InsurancePolicyValidationException(
                        InsurancePolicyValidationExceptionCode.INSURANCE_POLICY_NOT_FOUND, "");
            }
        } catch (InsurancePolicyValidationException e) {
            log.info("Error while getting insurance details from iib");
            throw new InsurancePolicyValidationException(
                    InsurancePolicyValidationExceptionCode.INSURANCE_POLICY_NOT_FOUND, "");
        }
        return insuranceDetailsHelper.convertIibResModelToInsuranceDetailsModel(iibResponseModel);
    }
}
