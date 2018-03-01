package org.rta.core.service.sync.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.customer.CustIndividualDetailsHistoryDAO;
import org.rta.core.dao.customer.CustomerCorporateDAO;
import org.rta.core.dao.customer.CustomerDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.user.AddressDAO;
import org.rta.core.dao.user.AddressHistoryDAO;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.dao.vehicle.VehicleRCChangeHistoryDAO;
import org.rta.core.entity.applicant.CustCorporateDetailsEntity;
import org.rta.core.entity.applicant.CustIndividualDetailsEntity;
import org.rta.core.entity.master.MandalEntity;
import org.rta.core.entity.user.AddressEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.enums.ServiceType;
import org.rta.core.exception.DataMismatchException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.sync.RCAadharSeedModel;
import org.rta.core.model.sync.SyncDataModel;
import org.rta.core.service.certificate.FcDetailsService;
import org.rta.core.service.certificate.PermitDetailsService;
import org.rta.core.service.sync.SyncService;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SyncServiceImpl implements SyncService{

	private static final Logger log = Logger.getLogger(SyncServiceImpl.class);

	@Autowired
	private VehicleDAO vehicleDAO;
	
	@Autowired
	private FcDetailsService fcDetailsService;
	
	@Autowired
	private PermitDetailsService permitDetailsService;
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private CustIndividualDetailsHistoryDAO custIndividualDetailsHistoryDAO;
	
	@Autowired
	private VehicleRCChangeHistoryDAO vehicleRCChangeHistoryDAO;
	
	@Autowired
	private AddressDAO addressDAO;
	
	@Autowired
	private AddressHistoryDAO addressHistoryDAO;
	
	@Autowired
	private MandalDAO mandalDAO;
	
	@Autowired
	private CustomerCorporateDAO customerCorporateDAO;
	
	@Autowired
	private VehicleService vehicleService;
	
	@Override
	@Transactional
	public SaveUpdateResponse syncData(SyncDataModel syncDataModel) {
		log.info(":REGISTRATION ::syncData::start:: " + syncDataModel.getServiceType());
		VehicleRCEntity vehicleRCEntity =  null;
		SaveUpdateResponse saveUpdateResponse = null;
		vehicleRCEntity = vehicleDAO.getVehicleRCByPRNumber(syncDataModel.getPrNumber());
		if(vehicleRCEntity == null)
			throw new IllegalArgumentException("Invalid Application!" + syncDataModel.getPrNumber());
		switch(ServiceType.getServiceType(syncDataModel.getServiceType())){
		case FC_FRESH:
		case FC_OTHER_STATION:
		case FC_RENEWAL:
		case FC_REVOCATION_CFX:
		case FC_RE_INSPECTION_SB:
			saveUpdateResponse = syncFitness(syncDataModel , vehicleRCEntity);
			break;
		case PERMIT_FRESH:
		case PERMIT_RENEWAL:
		case PERMIT_RENEWAL_AUTH_CARD:
		case PERMIT_SURRENDER:
		case PERMIT_VARIATIONS:
		case PERMIT_REPLACEMENT_VEHICLE:	
			saveUpdateResponse = syncPermit(syncDataModel , vehicleRCEntity);
			break;
		}
		
		vehicleService.updateRcApproverUserId(syncDataModel.getActionModelList(), vehicleRCEntity.getVehicleRcId());
		
		log.info(":REGISTRATION ::syncData::end:: " + saveUpdateResponse.getStatus());
		return saveUpdateResponse;
	}
	
	private SaveUpdateResponse syncFitness(SyncDataModel syncDataModel , VehicleRCEntity vehicleRCEntity){
		log.info("::syncFitnessNew:::start:::");
		return fcDetailsService.syncData(syncDataModel , vehicleRCEntity);
	}
	
	private SaveUpdateResponse syncPermit(SyncDataModel syncDataModel , VehicleRCEntity vehicleRCEntity){
		log.info("::syncPermit:::start:::");
		return permitDetailsService.syncData(syncDataModel , vehicleRCEntity);
	}

	@Override
	@Transactional
	public SaveUpdateResponse aadhaarSeeding(RCAadharSeedModel rcAadharSeedModel, String userName) throws DataMismatchException {
		VehicleRCEntity rcEntity = vehicleDAO.getVehicleRCByPRNumber(rcAadharSeedModel.getPrNumber());
		if (ObjectsUtil.isNull(rcEntity)) {
			throw new DataMismatchException();
		}
		try {
			String result = vehicleRCChangeHistoryDAO.saveData(rcEntity.getVehicleRcId(), userName,
					DateUtil.toCurrentUTCTimeStamp(), ServiceType.AADHAR_SEED_RC);
			log.info("One entity in history table staus " + result);
		} catch (Exception e) {
		}
		rcEntity.setAadharNo(rcAadharSeedModel.getAadharNumber());
		rcEntity.setAadharVerified(Boolean.TRUE);
		vehicleDAO.update(rcEntity);
		Long userId = null;
		CustIndividualDetailsEntity cide = customerDAO.getByVehicleRcId(rcEntity.getVehicleRcId());
		if(ObjectsUtil.isNotNull(cide)){
		    log.info("Updating customer indivisual details for aadhar seeding service...");
		    userId = cide.getCustIndDtlId();
		    try {
	            String result = custIndividualDetailsHistoryDAO.saveData(cide.getCustIndDtlId(), userName,
	                    DateUtil.toCurrentUTCTimeStamp(), ServiceType.AADHAR_SEED_RC);
	            log.info("One entity in history table staus " + result);
	        } catch (Exception e) {
	        }
	        cide.setAadharNo(rcAadharSeedModel.getAadharNumber());
	        if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getLastName())){
	            cide.setSurName(rcAadharSeedModel.getLastName());
	        }
	        if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getMobileNumber())){
	            cide.setMobileNo(rcAadharSeedModel.getMobileNumber());
	        }
	        if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getEmailId())){
	            cide.setEmailId(rcAadharSeedModel.getEmailId());
	        }
	        customerDAO.update(cide);
		} else {
		    CustCorporateDetailsEntity custCorp = customerCorporateDAO.getByVehicleRcId(rcEntity.getVehicleRcId());
		    if(ObjectsUtil.isNotNull(custCorp)){
		        log.info("Updating customer corporate details for aadhar seeding service...");
		        userId = custCorp.getCustCorpDtlsId();
		        if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getMobileNumber())){
		            custCorp.setMobile(rcAadharSeedModel.getMobileNumber());
		        }
		        if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getEmailId())){
		            custCorp.setEmail(rcAadharSeedModel.getEmailId());
		        }
		        customerCorporateDAO.update(custCorp);
		    }
		}
		
		
		if(ObjectsUtil.isNotNull(userId) && (!ObjectsUtil.isNull(rcAadharSeedModel.getMandalCode()) || !StringsUtil.isNullOrEmpty(rcAadharSeedModel.getPinCode()))){
			AddressEntity addressEntity = addressDAO.findByUserIdAndType(userId, "T");
			try {
				String result = addressHistoryDAO.saveData(addressEntity.getAddressId(), userName,
						DateUtil.toCurrentUTCTimeStamp(), ServiceType.AADHAR_SEED_RC);
				log.info("One entity in history table staus " + result);
			} catch (Exception e) {
			}
			if(!ObjectsUtil.isNull(rcAadharSeedModel.getMandalCode())){
				MandalEntity mandalEntity = mandalDAO.getByMandalCode(rcAadharSeedModel.getMandalCode());
				addressEntity.setMandal(mandalEntity);
			}
			if(!StringsUtil.isNullOrEmpty(rcAadharSeedModel.getPinCode())){
				addressEntity.setPinCode(rcAadharSeedModel.getPinCode());	
			}
			addressDAO.update(addressEntity);
		}
		
		vehicleService.updateRcApproverUserId(rcAadharSeedModel.getActionModelList(), rcEntity.getVehicleRcId());
		
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
	}
	
}
