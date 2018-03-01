package org.rta.core.service.finance.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.finance.FinanceDetailsDAO;
import org.rta.core.dao.finance.FinanceTokenDAO;
import org.rta.core.dao.finance.FinancerDAO;
import org.rta.core.dao.finance.VehicleFinanceDtlsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.finance.FinanceDetailsEntity;
import org.rta.core.entity.finance.FinanceTokenEntity;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.rta.core.entity.finance.VehicleFinanceDtlstEntity;
import org.rta.core.entity.master.DistrictEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.master.StateEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.enums.Status;
import org.rta.core.enums.UserType;
import org.rta.core.enums.master.FinanceType;
import org.rta.core.helper.finance.FinanceHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.finance.FinancerModel;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.master.MandalService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

@Service("financeDtlsService")
public class FinanceDtlsServiceImpl implements FinanceDtlsService {
	
	private static final Logger log = Logger.getLogger(FinanceDtlsServiceImpl.class);

    @Autowired
    FinancerDAO financerDAO;
    @Autowired
    FinanceDetailsDAO financeDtlsDAO;
    @Autowired
    FinanceHelper financeHelp;
    @Autowired
    VehicleDAO vehicleDAO;

    @Autowired
    MandalService mandalService;

    @Autowired
    MandalDAO mandalDAO;

    @Autowired
    DistrictDAO districtDAO;

    @Autowired
    StateDAO stateDAO;
    
    @Autowired
    VehicleFinanceDtlsDAO vehicleFinanceDtlsDAO;

    @Autowired
    FinanceTokenDAO financeTokenDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    AddressDAO addressDAO;


    @Value("${rta.step.finance}")
    Integer currentStep;

    @Transactional
    public List<FinancerModel> getFinancers() {
        List<FinancerMasterEntity> financers = financerDAO.getAll();
        if (financers == null || financers.isEmpty())
            return null;
        return (List<FinancerModel>) financeHelp.convertFinancerEntityToFinancerModelList(financers);

    }

    @Transactional
    public SaveUpdateResponse saveUpdateFinanceDtls(FinanceModel model)
			throws DataIntegrityViolationException, InvalidDataAccessApiUsageException {
		if (model == null)
			throw new IllegalArgumentException("Data not received");
		Long vehicleRcId = null;
		SaveUpdateResponse saveUpdateModel = null;
		String msg = "";
		
		try {
			vehicleRcId = Long.valueOf(model.getVehicleRcId());
		} catch (NumberFormatException nfe) {
			throw new IllegalArgumentException("Invalid VehicleRCId, must be a valid Numeric");
		}
		VehicleRCEntity vehicle = vehicleDAO.get(vehicleRcId);
		if (vehicle == null){
			throw new IllegalArgumentException("vehicle doesnt exist");
		}
		Integer existingStep = vehicle.getCurrentStep();
        if(ObjectsUtil.isNull(existingStep)){
            existingStep = 0;
        }
		if(model.getFinancerMode()!=null && model.getFinancerMode()==FinanceType.ONLINE.getId()) {
			if (vehicle.getCurrentStep() < currentStep){
				vehicle.setCurrentStep(currentStep);
			}
			vehicleDAO.update(vehicle);
			saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg,String.valueOf(model.getVehicleRcId()), currentStep);
			return saveUpdateModel;
		}
		FinanceDetailsEntity financeDtls = financeDtlsDAO.getFinanceDetailsOfVehicleRcd(vehicleRcId);
		if (model.getIsFinance()==null || !model.getIsFinance()) {
			if (financeDtls != null) {
				financeDtlsDAO.delete(financeDtls);
			}
			else if(existingStep < currentStep){
			    vehicle.setCurrentStep(currentStep);
			}
			msg = "Successfully Saved";
			saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg,
					String.valueOf(model.getVehicleRcId()), currentStep);
			return saveUpdateModel;
		}
		
		if(model.getMandal()==null || StringsUtil.isNullOrEmpty(model.getDistrict()) || StringsUtil.isNullOrEmpty(model.getState()) ||
				StringsUtil.isNullOrEmpty(model.getCity()) ||model.getDateOfAgreement()==null || model.getFinancerMode()==null)
			throw new IllegalArgumentException("Mandatory Data incomplete");
		
		Long timeStamp = DateUtil.toCurrentUTCTimeStamp();
		if (financeDtls == null) {
			financeDtls = new FinanceDetailsEntity();
			financeDtls.setCreatedBy(model.getUserNm());
			financeDtls.setCreatedOn(timeStamp);
			if(existingStep < currentStep){
			    vehicle.setCurrentStep(currentStep);
			}
			msg = "Saved Successfully";
		} else {

			financeDtls.setModifiedBy(model.getUserNm()); // TODO
			financeDtls.setModifiedOn(timeStamp);
			msg = "Updated Successfully";
		}
		StateEntity stateEntity = stateDAO.getStateByCode(model.getState());
		if(stateEntity==null )
			throw new IllegalArgumentException("Illegal State value received !!");
		MandalEntity mandalEntity = mandalDAO.getMandalByCode(model.getMandal());
		if(mandalEntity==null){
			mandalEntity=new MandalEntity();
			mandalEntity.setMandalId(0l);
		}
		DistrictEntity districtEntity = districtDAO.getDistrictByCode(model.getDistrict());
		
		if(districtEntity==null){
			districtEntity=new DistrictEntity();
			districtEntity.setDistrictId(0l);
		}
		
		financeDtls.setMandalEntity(mandalEntity);
		financeDtls.setDistrictEntity(districtEntity);
		financeDtls.setStateEntity(stateEntity);

		// financeDtls.getVehicleRcId().setCurrentStep(4);
		FinancerMasterEntity financer = financeHelp.convertToFinancerEntity(model);
		financeHelp.addFinanceModelToFinancerDetails(financeDtls, model);
		financeDtls.setVehicleRcId(vehicle);
		if (model.getFinancerMode() == FinanceType.ONLINE.getId()) {
			financerDAO.save(financer);
			financeDtls.setFinancerMId(financer.getId());
		}
		financeDtlsDAO.saveOrUpdate(financeDtls);
		saveUpdateModel = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg,
				String.valueOf(model.getVehicleRcId()), currentStep);
		return saveUpdateModel;

    }

	@Transactional
	public FinanceModel getFinanceDtlsByVehicleRcId(Long vehicleRcId,UserType userType) {
		log.info(":::getFinanceDtlsByVehicleRcId::::start:::::: vehicleRcId " + vehicleRcId + " userType " + userType);
		FinanceModel financeModel = new FinanceModel();
		VehicleFinanceDtlstEntity vehFinanceDtls=null;
		
		FinanceDetailsEntity financDtlsEnty = financeDtlsDAO.getFinanceDetailsOfVehicleRcd(vehicleRcId);
		financeModel=financeHelp.convertToModel(financDtlsEnty);
		
		if (financDtlsEnty == null) {
		    vehFinanceDtls = vehicleFinanceDtlsDAO.getRcrdForVehicleRc(vehicleRcId);
			FinanceTokenEntity financeToken = financeTokenDAO.getTokenId(vehicleRcId, ServiceType.FINANCE_NEW_REGISTARTION_SERVICE.getValue());
			if (vehFinanceDtls != null) {
				financeModel.setIsFinance(true);
				financeModel.setFinancerMode(FinanceType.ONLINE.getId());
				financeModel.setDateOfAgreement(vehFinanceDtls.getDateOfAgreement());
				financeModel.setAmount(vehFinanceDtls.getFinanceAmount());
				financeModel.setTenure(vehFinanceDtls.getTenure());
				financeModel.setEmi(vehFinanceDtls.getFinanceEmi());
				financeModel.setIntrestRate(vehFinanceDtls.getIntrestRate());
				financeModel.setPaymentMode(vehFinanceDtls.getPaymentMode());
				financeModel.setChequeNo(vehFinanceDtls.getChequeNo());
				financeModel.setEndDt(vehFinanceDtls.getEndDt());
				financeModel.setFinanceTerminated(vehFinanceDtls.getFinanceTerminated());
				financeModel.setRtaApproved(Status.getStatus(vehFinanceDtls.getRtoApproved()));
				financeModel.setFinancerId(vehFinanceDtls.getFinancerId());
				if(!ObjectsUtil.isNull(financeToken)){
					financeModel.setAppStatus(Status.getLabel(financeToken.getAppStatus()));
				}
				UserEntity user=userDAO.findByUserId(vehFinanceDtls.getFinancerId());
				if(user ==null)
					throw new IllegalArgumentException("Financer is invalid !!!");
				AddressEntity address=addressDAO.findByUserIdAndType(user.getUserId(), "T");
				if(address==null)
					throw new IllegalArgumentException("Financier's Address is invalid !!!");
				MandalEntity mandal= address.getMandal();
				DistrictEntity district=  mandal==null    ?  null:mandal.getDistrictEntity();
				StateEntity state      =  district==null  ?  null:district.getStateEntity();
				
				financeModel.setStreet(address.getStreet());
				financeModel.setDoorNo(address.getDoorNo());
				financeModel.setCity(address.getCity());
				financeModel.setMandal(address.getMandal() == null ? 0 : address.getMandal().getCode());
				financeModel.setDistrict(district == null ? "" : district.getCode());
			
				financeModel.setState(state == null ? "" : state.getCode());
				financeModel.setDistrcitName(district == null ? "" : district.getName());
				financeModel.setMandalName(mandal == null ? "" : mandal.getName());
				financeModel.setStateName(state == null ? "" : state.getName());
				financeModel.setName(user.getInstitutionName());
				financeModel.setApproverName(user.getFirstName() + (user.getLastName() != null ? user.getLastName() : ""));
				financeModel.setFinancerOfficialEmailId(user.getEmail());
				financeModel.setFinancerContactNumber(user.getMobile());
			} else {
                if (financeToken != null) {
                    financeModel.setFinanceApprovalStatus(financeToken.getAppStatus());
                    if(!ObjectsUtil.isNull(financeToken)){
                    	financeModel.setAppStatus(Status.getLabel(financeToken.getAppStatus()));
                    }
                    financeModel.setFinancerMode(FinanceType.ONLINE.getId());
                    financeModel.setIsFinance(true);
                }
			}
			
		}else{
			financeModel.setIsFinance(true);
		}
		log.info(":::getFinanceDtlsByVehicleRcId:::end:::::");
		return financeModel;
	}
	

}
