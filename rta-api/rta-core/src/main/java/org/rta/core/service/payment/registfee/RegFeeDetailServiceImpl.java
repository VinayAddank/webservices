package org.rta.core.service.payment.registfee;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.payment.registfee.FitnessFeeDAO;
import org.rta.core.dao.payment.registfee.RegFeeDetailDAO;
import org.rta.core.entity.payment.registfee.FitnessFeeDetailsEntity;
import org.rta.core.entity.payment.registfee.RegFeeDetailsEntity;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.enums.PRType;
import org.rta.core.enums.RegistrationCategoryType;
import org.rta.core.helper.payment.registfee.FitnessFeeHelper;
import org.rta.core.helper.payment.registfee.RegFeeDetailHelper;
import org.rta.core.model.finance.FinanceModel;
import org.rta.core.model.payment.registfee.FeeRuleModel;
import org.rta.core.model.payment.registfee.FitnessFeeModel;
import org.rta.core.model.payment.registfee.RegFeeDetailModel;
import org.rta.core.service.finance.FinanceDtlsService;
import org.rta.core.service.payment.tax.TaxDetailServiceImpl;
import org.rta.core.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegFeeDetailServiceImpl implements RegFeeDetailService {
	
	private static final Logger log = Logger.getLogger(TaxDetailServiceImpl.class);
	
	@Autowired
	private RegFeeDetailDAO regFeeDetailDAO;
	
	@Autowired
	private RegFeeDetailHelper regFeeDetailHelper;

	@Autowired
	private FinanceDtlsService financeDtlsServices;
	
	@Autowired
	private FitnessFeeDAO fitnessFeeDAO;
	
	@Autowired
    private FitnessFeeHelper fitnessFeeHelper;
	
	
    
	@Override
    @Transactional
    public RegFeeDetailModel saveFeeDetails(long vehicleRcId, String userName, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicldDetailsEntity) {
        log.debug(":::saveFeeDetails:::::: " + vehicleRcId);
		RegFeeDetailsEntity regFeeDetailsEntity = null;
        RegFeeDetailModel regFeeDetailModel = null;
        FitnessFeeModel fitnessFeeModel = null ;
        regFeeDetailsEntity = regFeeDetailDAO.getByVehicleRcId(vehicleRcId);
        if (regFeeDetailsEntity != null) {
            regFeeDetailModel = new RegFeeDetailModel();
            regFeeDetailsEntity = calculateRegistFee(userName,vehicleRcId, regFeeDetailsEntity, feeRuleModel , vehicldDetailsEntity);
            regFeeDetailsEntity.setModifiedBy(userName);
            regFeeDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
        } else {
            regFeeDetailModel = new RegFeeDetailModel();
            regFeeDetailsEntity = new RegFeeDetailsEntity();
            regFeeDetailsEntity = calculateRegistFee(userName,vehicleRcId, regFeeDetailsEntity, feeRuleModel , vehicldDetailsEntity);
            regFeeDetailsEntity.setVehicleRc(vehicldDetailsEntity.getVehicleRcId());
            regFeeDetailsEntity.setCreatedBy(userName);
            regFeeDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
        }
        regFeeDetailDAO.saveOrUpdate(regFeeDetailsEntity);
        fitnessFeeModel = saveFitness(userName,vehicleRcId, regFeeDetailsEntity, feeRuleModel , vehicldDetailsEntity);
        regFeeDetailModel = regFeeDetailHelper.convertToModel(regFeeDetailsEntity);
        if(fitnessFeeModel != null)
        	regFeeDetailModel.setFitnessFeeModel(fitnessFeeModel);
        return regFeeDetailModel;
	}
	
	public FitnessFeeModel saveFitness(String userName,long vehicleRcId, RegFeeDetailsEntity regFeeDetailEntity,
            FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicldDetailsEntity){
		FitnessFeeDetailsEntity fitnessFeeEntity = null;
		FitnessFeeModel fitnessFeeModel = null;
		switch (RegistrationCategoryType.getRegistrationCategoryType(feeRuleModel.getRegCategory())) {
        case TRANSPORT:
        	fitnessFeeEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRcId);
        	if (fitnessFeeEntity == null) {
                fitnessFeeEntity = new FitnessFeeDetailsEntity();
                fitnessFeeEntity.setCreatedBy(userName);
                fitnessFeeEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
                fitnessFeeEntity.setFitnessFee(feeRuleModel.getFitnessFee());
                fitnessFeeEntity.setFitnessService(feeRuleModel.getFitnessService());
                fitnessFeeEntity.setTotalFitnessFee(feeRuleModel.getTotalFitnessFee());
                fitnessFeeEntity.setVehicleRc(vehicldDetailsEntity.getVehicleRcId());
            } else {
                fitnessFeeEntity.setModifiedBy(userName);
                fitnessFeeEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
                fitnessFeeEntity.setFitnessFee(feeRuleModel.getFitnessFee());
                fitnessFeeEntity.setFitnessService(feeRuleModel.getFitnessService());
                fitnessFeeEntity.setTotalFitnessFee(feeRuleModel.getTotalFitnessFee());
            }
        	fitnessFeeDAO.saveOrUpdate(fitnessFeeEntity);
        	fitnessFeeModel = fitnessFeeHelper.convertToModel(fitnessFeeEntity);
        	break;
        }    
		return fitnessFeeModel;
	}

	private RegFeeDetailsEntity calculateRegistFee(String userName,long vehicleRcId, RegFeeDetailsEntity regFeeDetailEntity,
            FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicldDetailsEntity) {
			regFeeDetailEntity.setImportedFee(0);
	        regFeeDetailEntity.setPostalCharge(feeRuleModel.getPostalFee());
	        regFeeDetailEntity.setPrFee(feeRuleModel.getPrFee());
	        regFeeDetailEntity.setPrServiceCharge(feeRuleModel.getPrService());
	        regFeeDetailEntity.setSmartCardFee(feeRuleModel.getCardFee());
	        regFeeDetailEntity.setSpecialNumberFee(feeRuleModel.getSpecialNumberFee());
	        regFeeDetailEntity.setTrFee(feeRuleModel.getTrFee());
	        regFeeDetailEntity.setTrServiceCharge(feeRuleModel.getTrService());
	        FinanceModel financeDetails = financeDtlsServices
					.getFinanceDtlsByVehicleRcId(vehicldDetailsEntity.getVehicleRcId().getVehicleRcId(),null);
			if (financeDetails != null && financeDetails.getIsFinance()){
			regFeeDetailEntity.setHpaFee(feeRuleModel.getHpaFee());
			}else{
				regFeeDetailEntity.setHpaFee(0);
				regFeeDetailEntity.setTotalFee(feeRuleModel.getTotalPrFee() - feeRuleModel.getHpaFee());
			}
			
			return regFeeDetailEntity;
	}

	@Override
	@Transactional
	public FeeRuleModel getFeeRuleModel(long vehicleRcId, String userName, FeeRuleModel feeRuleModel , VehicleDetailsEntity vehicldDetailsEntity) {
		feeRuleModel = getFeeRuleDetails(vehicldDetailsEntity, feeRuleModel);
		return feeRuleModel;
	}

	public FeeRuleModel getFeeRuleDetails(VehicleDetailsEntity vehicleDetailEntity, FeeRuleModel feeRuleModel) {
		feeRuleModel.setRegCategory(vehicleDetailEntity.getVehicleRcId().getRegCategory().getRegistrationCategoryId());
		feeRuleModel.setVehicleClassCategory(vehicleDetailEntity.getVehicleSubClass());
		feeRuleModel.setVehicleClass(vehicleDetailEntity.getVehicleClass());
		feeRuleModel.setGvw(vehicleDetailEntity.getRlw());
		feeRuleModel.setOwnerType(vehicleDetailEntity.getVehicleRcId().getOwnershipType());
		feeRuleModel.setSeatingCapacity(vehicleDetailEntity.getSeatingCapacity());
		if(vehicleDetailEntity.getVehicleRcId().getPrType() != null)
			feeRuleModel.setSpecialNoFlag(vehicleDetailEntity.getVehicleRcId().getPrType());
		else
			feeRuleModel.setSpecialNoFlag(PRType.ORDINARY.getId());
		FinanceModel financeDetails = financeDtlsServices
				.getFinanceDtlsByVehicleRcId(vehicleDetailEntity.getVehicleRcId().getVehicleRcId(),null);
		if (financeDetails != null && financeDetails.getIsFinance())
			feeRuleModel.setIsFinance(true);
		return feeRuleModel;
	}

	
	@Override
    @Transactional
    public RegFeeDetailModel viewFeeDetails(long vehicleRcId, String userName, VehicleDetailsEntity vehicldDetailsEntity) {
        log.debug("::viewFeeDetails:::::: " + vehicleRcId);
		RegFeeDetailsEntity regFeeDetailsEntity = null;
        RegFeeDetailModel regFeeDetailModel = null;
        FitnessFeeModel fitnessFeeModel = null ;
        regFeeDetailsEntity = regFeeDetailDAO.getByVehicleRcId(vehicleRcId);
        fitnessFeeModel = viewFitness(vehicleRcId);
        regFeeDetailModel = regFeeDetailHelper.convertToModel(regFeeDetailsEntity);
        if(fitnessFeeModel != null)
        	regFeeDetailModel.setFitnessFeeModel(fitnessFeeModel);
        return regFeeDetailModel;
	}
	
	public FitnessFeeModel viewFitness(long vehicleRcId){
		FitnessFeeDetailsEntity fitnessFeeEntity = null;
		FitnessFeeModel fitnessFeeModel = null;
		fitnessFeeEntity = fitnessFeeDAO.getByVehicleRcId(vehicleRcId);
		if(fitnessFeeEntity != null)
        fitnessFeeModel = fitnessFeeHelper.convertToModel(fitnessFeeEntity);
        return fitnessFeeModel;
	}

}
