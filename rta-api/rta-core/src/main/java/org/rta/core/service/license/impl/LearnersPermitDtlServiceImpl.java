package org.rta.core.service.license.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.license.LearnersPermitDtlDAO;
import org.rta.core.dao.license.LicenseHolderDtlsDAO;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;
import org.rta.core.entity.licence.LearnersPermitDtlPK;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.exception.NotFoundException;
import org.rta.core.helper.license.LearnersPermitDtlHelper;
import org.rta.core.model.SaveUpdateResponse;
import org.rta.core.model.license.LearnersPermitDtlModel;
import org.rta.core.service.license.LearnersPermitDtlService;
import org.rta.core.service.license.LicenseHolderDtlsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnersPermitDtlServiceImpl implements LearnersPermitDtlService{
	
	private static final Logger logger = Logger.getLogger(LearnersPermitDtlServiceImpl.class);
	
	@Autowired
	private LearnersPermitDtlDAO learnersPermitDtlDAO;
	
	@Autowired
	private LearnersPermitDtlHelper learnersPermitDtlHelper;

	@Autowired
	private LicenseHolderDtlsDAO licenseHolderDtlsDAO;
	
	@Autowired
	private LicenseHolderDtlsService licenseHolderDtlsService;
	
	@Override
	@Transactional
	public List<LearnersPermitDtlModel> getLearnersPermitDtl(Long pk) throws NotFoundException {
	
		List<LearnersPermitDtlModel> model = learnersPermitDtlHelper.convertToModelList(learnersPermitDtlDAO.getLearnersPermitDtl(pk));
		if(ObjectsUtil.isNull(model)){
			throw new NotFoundException();
		}	
		return model;
	}

	@Override
	@Transactional
	public SaveUpdateResponse saveLearnersPermitDetail(List<LearnersPermitDtlModel> models, String aadharNumber, String userName) {
		
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		String msg = "";
		logger.info("Getting  in request LicenseHolderDtlsEntity  " + entity.getLicenceHolderId());
	
		List<LearnersPermitDtlEntity> permitData = learnersPermitDtlDAO.getLearnersPermitDtl(entity.getLicenceHolderId());
		Integer count = permitData.size();
		for (LearnersPermitDtlModel permit : models) {
			count++;
			LearnersPermitDtlEntity permitEntity = learnersPermitDtlHelper.convertToEntity(permit);
			permitEntity.setCreatedBy(userName);
			permitEntity.setCreatedOn(new Date());
			permitEntity.setModifiedBy(userName);
			permitEntity.setModifiedOn(new Date());
			permitEntity.setRtaOfficeId(entity.getRtaOfficeId());
			permitEntity.setReferenceId(licenseHolderDtlsService.getReferenceId(entity.getLicenceHolderId(), entity.getRtaOfficeId().getCode(), permit.getLlrNoType()));
			permitEntity.setId(new LearnersPermitDtlPK(entity.getLicenceHolderId(), count,	permit.getLlrVehicleClassCode()));
			msg = " Successfully save data";
			learnersPermitDtlDAO.saveOrUpdate(permitEntity);
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}
	
	@Override
	@Transactional
	public SaveUpdateResponse updateLearnersPermitDetail(List<LearnersPermitDtlModel> models, String aadharNumber, String userName) {
		
		LicenseHolderDtlsEntity entity = licenseHolderDtlsDAO.getLicenseHolderDtls(aadharNumber);
		String msg = "";
		logger.info("Getting  in request updateLearnersPermitDetail  " + entity.getLicenceHolderId());
	
		List<LearnersPermitDtlEntity> permitData = learnersPermitDtlDAO.getLearnersPermitDtl(entity.getLicenceHolderId());
		for (LearnersPermitDtlEntity permitEntity : permitData) {
			permitEntity.setModifiedBy(userName);
			permitEntity.setModifiedOn(new Date());
			permitEntity.setApplicationId(models.get(0).getApplicationId());
			permitEntity.setLlrNo(models.get(0).getLlrNo());
			permitEntity.setLlrNoType(models.get(0).getLlrNoType());
			permitEntity.setTestDate(models.get(0).getTestDate());
			permitEntity.setTestExempted(models.get(0).getTestExempted());
			permitEntity.setTestExemptedReason(models.get(0).getTestExemptedReason());
			permitEntity.setTestResult(SomeConstants.PASS);
			permitEntity.setValidFrom(new Date());
			permitEntity.setValidTo(DateUtil.addMonths(new Date(), SomeConstants.SIX));
			permitEntity.setReferenceId(licenseHolderDtlsService.getReferenceId(entity.getLicenceHolderId(), entity.getRtaOfficeId().getCode(), models.get(0).getLlrNoType()));
			msg = " Successfully update data";
			learnersPermitDtlDAO.saveOrUpdate(permitEntity);
		}
		return new SaveUpdateResponse(SaveUpdateResponse.SUCCESS, msg, null);
	}
	
}
