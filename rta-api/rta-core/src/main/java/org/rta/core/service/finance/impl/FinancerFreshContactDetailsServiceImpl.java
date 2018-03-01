package org.rta.core.service.finance.impl;

import org.rta.core.dao.finance.FinancerFreshContactDetailsDao;
import org.rta.core.entity.application.rejection.RejectionHistoryEntity;
import org.rta.core.entity.finance.FinancerFreshContactDetailsEntity;
import org.rta.core.entity.finance.FinancerMasterEntity;
import org.rta.core.exception.VehicleRcNotFoundException;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.finance.FinancerFreshContactDetailsModel;
import org.rta.core.service.finance.FinancerFreshContactDetailsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinancerFreshContactDetailsServiceImpl implements FinancerFreshContactDetailsService  {

	@Autowired
	private FinancerFreshContactDetailsDao financerFreshContactDetailsDao;

	@Override
	@Transactional
	public FinancerFreshContactDetailsModel getFinancerFreshContactDetails(Long vehicleRcId)
			throws VehicleRcNotFoundException {
		FinancerFreshContactDetailsEntity financerFreshContactDetails = financerFreshContactDetailsDao
				.getByVehicleRcId(vehicleRcId);

		return convertToModel(financerFreshContactDetails);
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveFinancerFreshContactDetails(
			FinancerFreshContactDetailsModel financerFreshContactDetails) throws VehicleRcNotFoundException {
		SaveUpdateResponse response = new SaveUpdateResponse(SaveUpdateResponse.SUCCESS);
		if (financerFreshContactDetails == null) {
			throw new IllegalArgumentException("Data not received");
		}

		if (ObjectsUtil.isNotNull(financerFreshContactDetails)) {

			FinancerFreshContactDetailsEntity financerFreshContactDetailsEntity = financerFreshContactDetailsDao.getByVehicleRcId(financerFreshContactDetails.getVehicleRcId());
			if (ObjectsUtil.isNull(financerFreshContactDetailsEntity)) {
				financerFreshContactDetailsEntity = new FinancerFreshContactDetailsEntity();
				financerFreshContactDetailsEntity.setCreatedBy(financerFreshContactDetails.getCreatedBy());
				financerFreshContactDetailsEntity.setCreatedOn(DateUtil.toCurrentUTCTimeStamp());
				financerFreshContactDetailsEntity.setVehicleRcId(financerFreshContactDetails.getVehicleRcId());
			}
			financerFreshContactDetailsEntity.setEmail(financerFreshContactDetails.getEmail());
			financerFreshContactDetailsEntity.setMobileNumber(financerFreshContactDetails.getMobileNumber());
			financerFreshContactDetailsEntity.setModifiedBy(financerFreshContactDetails.getCreatedBy());
			financerFreshContactDetailsEntity.setModifiedOn(DateUtil.toCurrentUTCTimeStamp());

			financerFreshContactDetailsDao.saveOrUpdate(financerFreshContactDetailsEntity);
		}

		return response;
	}
	
	public FinancerFreshContactDetailsModel convertToModel(FinancerFreshContactDetailsEntity source){
		if(ObjectsUtil.isNull(source)){
			return null;
		}
		FinancerFreshContactDetailsModel financerFreshContactDetails = new FinancerFreshContactDetailsModel();
		
		financerFreshContactDetails.setVehicleRcId(source.getVehicleRcId());
		financerFreshContactDetails.setMobileNumber(source.getMobileNumber());
		financerFreshContactDetails.setEmail(source.getEmail());
		financerFreshContactDetails.setIsUpdated(source.getIsUpdated());
		financerFreshContactDetails.setCreatedBy(source.getCreatedBy());
		financerFreshContactDetails.setCreatedOn(source.getCreatedOn());
		financerFreshContactDetails.setModifiedBy(source.getModifiedBy());
		financerFreshContactDetails.setModifiedOn(source.getModifiedOn());
		financerFreshContactDetails.setFinancierId(source.getFinancierId());
		
		return financerFreshContactDetails;
	}
}
