package org.rta.core.helper.license;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.constant.SomeConstants;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.licence.LearnersPermitDtlEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.license.LicenceVehicleClass;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.license.LearnersPermitDtlModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.license.DriversLicenceDetailsService;
import org.rta.core.utils.DateUtil;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LearnersPermitDtlHelper implements BaseConverter<LearnersPermitDtlEntity, LearnersPermitDtlModel>{

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DriversLicenceDetailsService driversLicenceDetailsService;
	
	@Override
	public LearnersPermitDtlModel convertToModel(LearnersPermitDtlEntity source) {
		
		if(ObjectsUtil.isNull(source)){
			return null;	
		}
		
		LearnersPermitDtlModel model = new LearnersPermitDtlModel();
		LicenceVehicleClass vehicleClassType = LicenceVehicleClass.getLicenceVehicleClassByCode(source.getId().getLlrVehicleClassCode().trim());
		if(!ObjectsUtil.isNull(vehicleClassType)){
			model.setTransportType(vehicleClassType.getTransportType());
			model.setCovName(vehicleClassType.getLabel());
		}
		model.setLlrVehicleClassCode(source.getId().getLlrVehicleClassCode());
		model.setLicenceHolderId(source.getId().getLicenceHolderId());
		model.setApplicationId(source.getApplicationId());
		model.setApplicationOrigination(source.getApplicationOrigination());
		model.setDateOfFirstIssue(source.getDateOfFirstIssue());
		model.setLlrIssuedt(source.getLlrIssuedt());
		model.setLlrNo(source.getLlrNo());
		model.setLlrNoType(source.getLlrNoType());
		model.setParentConsentAadhaarNo(source.getParentConsentAadhaarNo());
		model.setPhotoAttachmentId(source.getPhotoAttachmentId());
		model.setReferenceId(source.getReferenceId());
		model.setRetestFlag(source.getRetestFlag());
		model.setRtaOfficeDetails(new RTAOfficeModel());
		model.getRtaOfficeDetails().setName(source.getRtaOfficeId().getName());
		model.getRtaOfficeDetails().setCode(source.getRtaOfficeId().getCode());
		model.setSignAttachmentId(source.getSignAttachmentId());
		model.setStatusCode(source.getStatusCode());
		model.setStatusDate(source.getStatusDate());
		model.setStatusRemarks(source.getStatusRemarks());
		model.setStatusUpdatedBy(source.getStatusUpdatedBy());
		model.setTestDate(source.getTestDate());
		model.setTestExempted(source.getTestExempted());
		model.setTestExemptedReason(source.getTestExemptedReason());
		model.setTestId(source.getTestId());
		model.setTestResult(source.getTestResult());
		model.setTicketDetails(source.getTicketDetails());
		model.setValidFrom(source.getValidFrom());
		model.setValidTo(source.getValidTo());
		model.setMedicalFitnessType(source.getMedicalFitnessType());
		model.setMedicalPractionerCode(source.getMedicalPractionerCode());
		if(!ObjectsUtil.isNull(source.getAoUserId())){
			UserModel aoUserModel = new  UserModel();
			UserEntity userEntity = source.getAoUserId();
			aoUserModel.setUserSignature(driversLicenceDetailsService.getEmployeeSignature(userEntity.getUserId()));
			aoUserModel.setFirstName(userEntity.getFirstName());
			aoUserModel.setLastName(userEntity.getLastName());
			model.setAoUserDetails(aoUserModel);
		}
		if(!ObjectsUtil.isNull(source.getMviUserId())){
			UserModel mviUserModel = new  UserModel();
			UserEntity userEntity = source.getAoUserId();
			mviUserModel.setUserSignature(driversLicenceDetailsService.getEmployeeSignature(userEntity.getUserId()));
			mviUserModel.setFirstName(userEntity.getFirstName());
			mviUserModel.setLastName(userEntity.getLastName());
			model.setMviUserDetails(mviUserModel);
		}
		return model;
	}

	@Override
	public LearnersPermitDtlEntity convertToEntity(LearnersPermitDtlModel source) {
		if(ObjectsUtil.isNull(source)){
			return null;	
		}
		
		LearnersPermitDtlEntity entity = new LearnersPermitDtlEntity();
		entity.setApplicationId(source.getApplicationId());
		entity.setApplicationOrigination(source.getApplicationOrigination());
		entity.setDateOfFirstIssue(source.getDateOfFirstIssue());
		entity.setLlrIssuedt(source.getLlrIssuedt());
		entity.setLlrNo(source.getLlrNo());
		entity.setLlrNoType(source.getLlrNoType());
		entity.setParentConsentAadhaarNo(source.getParentConsentAadhaarNo());
		entity.setPhotoAttachmentId(source.getPhotoAttachmentId());
		entity.setReferenceId(source.getReferenceId());
		entity.setRetestFlag(source.getRetestFlag());
		entity.setRtaOfficeId(rtaOfficeDAO.getRtaOfficeDetailsByCode(source.getRtaOfficeDetails().getCode()));
		entity.setSignAttachmentId(source.getSignAttachmentId());
		entity.setStatusCode(source.getStatusCode());
		entity.setStatusDate(source.getStatusDate());
		entity.setStatusRemarks(source.getStatusRemarks());
		entity.setStatusUpdatedBy(source.getStatusUpdatedBy());
		entity.setTestDate(source.getTestDate());
		entity.setTestExempted(source.getTestExempted());
		entity.setTestExemptedReason(source.getTestExemptedReason());
		entity.setTestId(source.getTestId());
		entity.setTestResult(source.getTestResult());
		entity.setTicketDetails(source.getTicketDetails());
		entity.setValidFrom(source.getValidFrom());
		entity.setDateOfFirstIssue(source.getValidTo());
		entity.setMedicalFitnessType(source.getMedicalFitnessType());
		entity.setMedicalPractionerCode(source.getMedicalPractionerCode());
		entity.setValidTo(source.getValidTo());
//		if(!ObjectsUtil.isNull(source.getAoUserDetails())){
//			if(!ObjectsUtil.isNull(source.getAoUserDetails().getUserId())){
//				entity.setAoUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
//			}
//		}
		if(!ObjectsUtil.isNull(source.getAoUserId())){
			entity.setAoUserId(userDAO.findByUserId(source.getAoUserId()));
		}
		if(!ObjectsUtil.isNull(source.getMviUserDetails())){
			if(!ObjectsUtil.isNull(source.getMviUserDetails().getUserId())){
				entity.setMviUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
			}
		}
		return entity;
	}

	@Override
	public List<LearnersPermitDtlModel> convertToModelList(Collection<LearnersPermitDtlEntity> source) {
		if(ObjectsUtil.isNull(source)){
			return null;	
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<LearnersPermitDtlEntity> convertToEntityList(Collection<LearnersPermitDtlModel> source) {
		if(ObjectsUtil.isNull(source)){
			return null;	
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}
}
