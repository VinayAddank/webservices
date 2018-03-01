package org.rta.core.service.vehicle;

import java.util.List;
import java.util.Map;

import org.rta.core.enums.PRType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.exception.InvalidVehicleSubClassException;
import org.rta.core.exception.NotFoundException;
import org.rta.core.exception.TaxTypeNotFoundException;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.TheftIntimationRevocationModel;
import org.rta.core.model.UserActionModel;
import org.rta.core.model.vehicle.CommonServiceModel;
import org.rta.core.model.vehicle.DuplicateRegistrationModel;
import org.rta.core.model.vehicle.VehicleDetailsRequestModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;

public interface VehicleService {

/*    public abstract Long save(UnregisteredVehicleModel vehicleModel);

    *//**
     * save vehicle model
     * 
     * @param vehicleModel
     * @return Long, generated id
     * @throws InvalidVehicleSubClassException 
     *//*
    public abstract Long save(Integer registrationId, UnregisteredVehicleModel vehicleModel);*/

    public abstract void update(Long userId, VehicleDetailsRequestModel vehicleModel, String token, Integer currentStep,
            UserType userType)
            throws VehicleRcNotFoundException, TaxTypeNotFoundException, InvalidVehicleSubClassException;

    public Map<String, Object> getApplicationStatus(Long userId, UserType userType, String appType, Integer status,
            String regCat, Long from, Long to, String query, Integer perPageRecords, Integer pageNumber);

    public abstract Long getApplicationCount(Long userId, String type, Status status, String regCatogry);

    public VehicleDetailsRequestModel getVehicleDetails(Long vehicleRcId, UserType userType) throws VehicleRcNotFoundException;
    
    public VehicleDetailsRequestModel getVehicleClass(String prNumber);

    public SaveUpdateResponse updateStepSeven(Long vehicleRcId);

    public Map<String, String> getIsPanCardMandatory(Long vehicleRcId, String vehicleClass, Double amount);

    public SaveUpdateResponse updatePRNumberType(String userName, Long vehicleRcId, PRType numberType);
    
    public PRType getPRType(Long vehicleRcId) throws VehicleRcNotFoundException;

    public VehicleDetailsRequestModel getVehicleDetailsByChassisNo(String chassisNumber);
    
    public SaveUpdateResponse update(DuplicateRegistrationModel model, String userName) throws DataMismatchException;

	public SaveUpdateResponse update(CommonServiceModel model, String userName) throws DataMismatchException, NotFoundException;

	public SaveUpdateResponse syncTI(TheftIntimationRevocationModel theftModel, String prNumber, String userName) throws DataMismatchException;

	public Boolean getIsPrGenerated(Long vehicleRcId);
	
	public Boolean getIsBharatStageThreeVehicle(String chassisNumber);
	
	public List<VehicleClassDescModel> getAlterationCovList(String prNumber,String regCatCode);
	
	public Boolean  isMatchedSavedAadhar(String aadharNumber, Long vehicleRcId);
	
	public List<String> getBodyTypeList();

	public void updateRcApproverUserId(List<UserActionModel> userActionList, Long vehicleRcId);
}

