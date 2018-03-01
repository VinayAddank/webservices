/**
 * 
 */
package org.rta.registration.service.cms.impl;

import org.apache.log4j.Logger;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.export.SinkAllData;
import org.rta.core.model.vehicle.cms.CMSSyncVehicleModel;
import org.rta.core.service.export.ExportDataService;
import org.rta.core.service.vehicle.cms.CMSSyncVehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.registration.controller.ApplicationController;
import org.rta.registration.service.cms.CmsLiveSyncingService;
import org.rta.secondvehicle.api.VehicleDetailsSyncService;
import org.rta.secondvehicle.api.model.CmsSyncingRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author sohan.maurya
 *
 */

@Service
public class CmsLiveSyncingServiceImpl implements CmsLiveSyncingService {
	private static final Logger logger = Logger.getLogger(ApplicationController.class);
	
	@Value("${base.url}")
	private String baseURL;

	private static final String PROD_BASE_STRING = "productionapp";
	
	@Autowired
	private ExportDataService exportDataService;
	
	@Autowired
	private VehicleDetailsSyncService vehicleDetailsSyncService;

	@Autowired
	private CMSSyncVehicleService cmsSyncVehicleService;
	
	@Override
	public String cmsLiveSyncingService(Long vehicleRcId, String trOrPrNumber, String serviceCode) {
		
		logger.info("get Syncing CMS Data ::::::::::::: " + vehicleRcId);
		SinkAllData sinkAllData = exportDataService.getVehicleDetailsInfo(vehicleRcId, trOrPrNumber);
		CMSSyncVehicleModel model = null;
		String environment = "DEV";
		String result = SaveUpdateResponse.SUCCESS;
		try {
			if (baseURL.contains(PROD_BASE_STRING)) {
				environment = "PROD";
			}
			String response = vehicleDetailsSyncService.syncVehicleDetailsInfo(environment, getCmsSyncingRequest(sinkAllData ));
			
			try {
				model = new CMSSyncVehicleModel();
				model.setVehicleNumber(sinkAllData.getVehicleRegistrationDetails().getVehicleNumber());
				model.setVehicleRcId(vehicleRcId);
				model.setResponseCode(response);
				model.setMessage("SUCCESS");
				model.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				model.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
				model.setServiceCode(serviceCode);
				cmsSyncVehicleService.save(model);
			} catch (Exception e) {
				logger.error("Exception in cmsSyncVehicleService save :::: " + e.getMessage());
			}

		} catch (Exception ex) {
			logger.error("get Syncing CMS Data :::::::::Exception:::: " + ex.getMessage());
			result = SaveUpdateResponse.FAILURE;
			model = new CMSSyncVehicleModel();
			model.setMessage("ERROR");
			model.setVehicleNumber(sinkAllData.getVehicleRegistrationDetails().getVehicleNumber());
			model.setVehicleRcId(vehicleRcId);
			model.setResponseCode("E");
			try {
				model.setMessage(ex.getMessage().substring(0, 200));
			} catch (Exception e) {
			}
			model.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
			model.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());
			model.setServiceCode(serviceCode);
			cmsSyncVehicleService.save(model);
		}
		return result;
	}
	
	private CmsSyncingRequestModel getCmsSyncingRequest(SinkAllData sinkAllData ){
		
		CmsSyncingRequestModel model = new CmsSyncingRequestModel();
		model.setVehicleNumber(sinkAllData.getVehicleRegistrationDetails().getVehicleNumber()); 
		model.setVehicleRegisteredDate(sinkAllData.getVehicleRegistrationDetails().getVehicleRegisteredDate());
		model.setVehicleRegistrationValidUpto(sinkAllData.getVehicleRegistrationDetails().getVehicleRegistrationValidUpto());
		model.setVehicleIssuePlace(sinkAllData.getVehicleRegistrationDetails().getVehicleIssuePlace());
		model.setVehicleStatus(sinkAllData.getVehicleRegistrationDetails().getVehicleStatus());
		model.setRemarks(sinkAllData.getVehicleRegistrationDetails().getRemarks());
		model.setOwnershipType(sinkAllData.getOwnerDetails().getOwnershipType());
		model.setOwnerFirstName(sinkAllData.getOwnerDetails().getOwnerFirstName()); 
		model.setOwnerMiddileName(sinkAllData.getOwnerDetails().getOwnerMiddileName()); 
		model.setOwnerLastName(sinkAllData.getOwnerDetails().getOwnerLastName());
		model.setOwnerAge(String.valueOf(sinkAllData.getOwnerDetails().getOwnerAge()));
		model.setOwnerFatherName(sinkAllData.getOwnerDetails().getOwnerFatherName()); 
		model.setOwnerPanNo(sinkAllData.getOwnerDetails().getOwnerPanNo());
		model.setOwnerVoterId(sinkAllData.getOwnerDetails().getOwnerVoterId()); 
		model.setOwnerMobileNo(sinkAllData.getOwnerDetails().getOwnerMobileNo());
		model.setOwnerAddress1(sinkAllData.getOwnerDetails().getOwnerAddress1());
		model.setOwnerAddress2(sinkAllData.getOwnerDetails().getOwnerAddress2()); 
		model.setOwnerAddress3(sinkAllData.getOwnerDetails().getOwnerAddress3()); 
		model.setOwnerCity(sinkAllData.getOwnerDetails().getOwnerCity());
		model.setOwnerState(sinkAllData.getOwnerDetails().getOwnerState()); 
		model.setOwnerPin(sinkAllData.getOwnerDetails().getOwnerPin());
		model.setVehicleOldNo(sinkAllData.getVehicleRegistrationDetails().getVehicleOldNo()); 
		model.setPreviousRegisteredOfficeName(sinkAllData.getVehicleRegistrationDetails().getPreviousRegisteredOfficeName()); 
		model.setPreviousRegisteredOfficeState(sinkAllData.getVehicleRegistrationDetails().getPreviousRegisteredOfficeState());
		model.setGovernmentVehicle(sinkAllData.getVehicleRegistrationDetails().getGovernmentVehicle()); 
		model.setReservedSpecialNo(sinkAllData.getVehicleRegistrationDetails().getReservedSpecialNo());
		model.setVehicleIsRTC(sinkAllData.getVehicleRegistrationDetails().getVehicleIsRTC()); 
		model.setInsuranceNo(sinkAllData.getInsuranceDetails().getInsuranceNo());
		model.setInsuranceCompanyName(sinkAllData.getInsuranceDetails().getInsuranceCompanyName()); 
		model.setInsuranceValidFrom(sinkAllData.getInsuranceDetails().getInsuranceValidFrom()); 
		model.setInsuranceValidTo(sinkAllData.getInsuranceDetails().getInsuranceValidTo()); 
		model.setMakerName(sinkAllData.getVehicleDetails().getMakerName());
		model.setMakerClass(sinkAllData.getVehicleDetails().getMakerClass());
		model.setDealerName(sinkAllData.getDealerDetails().getDealerName()); 
		model.setDealerAddress1(sinkAllData.getDealerDetails().getDealerAddress1());
		model.setDealerAddress2(sinkAllData.getDealerDetails().getDealerAddress2());
		model.setDealerAddress3(sinkAllData.getDealerDetails().getDealerAddress3());
		model.setDealerCity(sinkAllData.getDealerDetails().getDealerCity()); 
		model.setDealerState(sinkAllData.getDealerDetails().getDealerState()); 
		model.setDealerAddressPinCode(sinkAllData.getDealerDetails().getDealerAddressPinCode()); 
		model.setVehicleClass(sinkAllData.getVehicleDetails().getVehicleClass()); 
		model.setChassisNo(sinkAllData.getVehicleDetails().getChassisNo());
		model.setEngineNo(sinkAllData.getVehicleDetails().getEngineNo());
		model.setVehicleType(sinkAllData.getVehicleDetails().getVehicleType()); 
		model.setBodyType(sinkAllData.getVehicleDetails().getBodyType()); 
		model.setWheelBase(sinkAllData.getVehicleDetails().getWheelBase()); 
		model.setFuel(sinkAllData.getVehicleDetails().getFuel());
		model.setVehicleCC(sinkAllData.getVehicleDetails().getVehicleCC());
		model.setCylenders(sinkAllData.getVehicleDetails().getCylenders());
		model.setSeatingCapacity(sinkAllData.getVehicleDetails().getSeatingCapacity());
		model.setDriverSeatingCapacity(sinkAllData.getVehicleDetails().getDriverSeatingCapacity()); 
		model.setStandingCapacity(sinkAllData.getVehicleDetails().getStandingCapacity());
		model.setColor(sinkAllData.getVehicleDetails().getColor()); 
		model.setVehicleIsNewOld(sinkAllData.getVehicleDetails().getVehicleIsNewOld()); 
		model.setManufactureMonthYear(sinkAllData.getVehicleDetails().getManufactureMonthYear()); 
		model.setHorsePower(sinkAllData.getVehicleDetails().getHorsePower()); 
		model.setUnleadenWeight(sinkAllData.getVehicleDetails().getUnleadenWeight());
		model.setGrowssWeightCertificate(sinkAllData.getVehicleDetails().getGrowssWeightCertificate()); 
		model.setGrowssWeightRegistrationTime(sinkAllData.getVehicleDetails().getGrowssWeightRegistrationTime()); 
		model.setFronAxelTyreSizes(sinkAllData.getVehicleDetails().getFronAxelTyreSizes());
		model.setRearAxelTyreSizes(sinkAllData.getVehicleDetails().getRearAxelTyreSizes());
		model.setOtherAxelTyreSizes(sinkAllData.getVehicleDetails().getOtherAxelTyreSizes());
		model.setTandomAxelTyreSizes(sinkAllData.getVehicleDetails().getTandomAxelTyreSizes()); 
		model.setFronWeight(sinkAllData.getVehicleDetails().getFronWeight());
		model.setRearWeight(sinkAllData.getVehicleDetails().getRearWeight());
		model.setOtherAxelWeight(sinkAllData.getVehicleDetails().getOtherAxelWeight());
		model.setTandomAxelWeight(sinkAllData.getVehicleDetails().getTandomAxelWeight()); 
		model.setAxelType(sinkAllData.getVehicleDetails().getAxelType()); 
		model.setLength(sinkAllData.getVehicleDetails().getLength());
		model.setWidth(sinkAllData.getVehicleDetails().getWidth());
		model.setHeight(sinkAllData.getVehicleDetails().getHeight()); 
		model.setHangingCapacity(sinkAllData.getVehicleDetails().getHangingCapacity());
		model.setTaxExemption(sinkAllData.getTaxDetails().getTaxExemption());
		model.setTaxPaidOfficeCode(sinkAllData.getTaxDetails().getTaxPaidOfficeCode());
		model.setTaxPaidStateCode(sinkAllData.getTaxDetails().getTaxPaidStateCode()); 
		model.setTaxPaymentPeriod(sinkAllData.getTaxDetails().getTaxPaymentPeriod());
		model.setTaxDemandAmount(sinkAllData.getTaxDetails().getTaxDemandAmount()); 
		model.setTaxPenaltyAmount(sinkAllData.getTaxDetails().getTaxPenaltyAmount());
		model.setTaxCollectedAmount(sinkAllData.getTaxDetails().getTaxCollectedAmount());
		model.setTaxDemandDate(sinkAllData.getTaxDetails().getTaxDemandDate()); 
		model.setTaxQuarterStartDate(sinkAllData.getTaxDetails().getTaxQuarterStartDate()); 
		model.setTaxValidUpto(sinkAllData.getTaxDetails().getTaxValidUpto());
		model.setHypothecationType(sinkAllData.getFinancerDetails().getHypothecationType());
		model.setFinancerName(sinkAllData.getFinancerDetails().getFinancerName());
		model.setFinanceAggrementDate(sinkAllData.getFinancerDetails().getFinanceAggrementDate());
		model.setFinancerAddress1(sinkAllData.getFinancerDetails().getFinancerAddress1());
		model.setFinancerAddress2(sinkAllData.getFinancerDetails().getFinancerAddress2());
		model.setFinancerAddress3(sinkAllData.getFinancerDetails().getFinancerAddress3());
		model.setFinancerCity(sinkAllData.getFinancerDetails().getFinancerCity()); 
		model.setFinancerState(sinkAllData.getFinancerDetails().getFinancerState());
		model.setFinancerPin(sinkAllData.getFinancerDetails().getFinancerPin()); 
		model.setFinancerFaxNo(sinkAllData.getFinancerDetails().getFinancerFaxNo()); 
		model.setFinancerPhoneNO(sinkAllData.getFinancerDetails().getFinancerPhoneNO()); 
		model.setFinancerEmailId(sinkAllData.getFinancerDetails().getFinancerEmailId());
		model.setNocOfficeCode(sinkAllData.getVehicleRegistrationDetails().getNocOfficeCode()); 
		model.setApplicationNo(sinkAllData.getApplication());
		model.setFcNumber(sinkAllData.getFitnessDetails().getFcNumber());
		model.setFcIssuedBy(sinkAllData.getFitnessDetails().getFcIssuedBy());
		model.setFcIssuedDate(sinkAllData.getFitnessDetails().getFcIssuedDate());
		model.setFcValidFromDate(sinkAllData.getFitnessDetails().getFcValidFromDate());
		model.setFcValidToDate(sinkAllData.getFitnessDetails().getFcValidToDate());
		model.setFcApprovedBy(sinkAllData.getFitnessDetails().getFcApprovedBy());
		model.setFcChallanNo(sinkAllData.getFitnessDetails().getFcChallanNo());
		model.setPermitNo(sinkAllData.getPermitDetails().getPermitNo());
		model.setPermitClass(sinkAllData.getPermitDetails().getPermitClass());
		model.setPermitType(sinkAllData.getPermitDetails().getPermitType());
		model.setPermitIssueDate(sinkAllData.getPermitDetails().getPermitIssueDate());
		model.setPermitValidFromDate(sinkAllData.getPermitDetails().getPermitValidFromDate());
		model.setPermitValidToDate(sinkAllData.getPermitDetails().getPermitValidToDate());
		model.setAuthorizationNo(sinkAllData.getPermitDetails().getAuthorizationNo());
		model.setAuthFromDate(sinkAllData.getPermitDetails().getAuthFromDate());
		model.setAuthToDate(sinkAllData.getPermitDetails().getAuthToDate());
		model.setRouteDescription(sinkAllData.getPermitDetails().getRouteDescription());
		model.setGoodsDescription(sinkAllData.getPermitDetails().getGoodsDescription());
		model.setPermitLadenWeight(sinkAllData.getPermitDetails().getPermitLadenWeight());
		model.setPermitRouteType(sinkAllData.getPermitDetails().getPermitRouteType());
		model.setOneDistrictPermit(sinkAllData.getPermitDetails().getOneDistrictPermit());
		model.setTwoDistrictPermit(sinkAllData.getPermitDetails().getTwoDistrictPermit());
		//model.setTransactionDate(sinkAllData.getPermitDetails().getTransactionDate());
		model.setTransactionDate(DateUtil.extractDateAsString(DateUtil.toCurrentUTCTimeStamp()));// I have to change, because OTSI want
		return model;
	}
}
