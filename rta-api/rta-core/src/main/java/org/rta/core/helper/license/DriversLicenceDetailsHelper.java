package org.rta.core.helper.license;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.dao.user.UserDAO;
import org.rta.core.entity.licence.DriversLicenceDetailsEntity;
import org.rta.core.entity.user.UserEntity;
import org.rta.core.enums.license.LicenceVehicleClass;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.license.DriversLicenceDetailsModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.user.UserModel;
import org.rta.core.service.license.DriversLicenceDetailsService;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriversLicenceDetailsHelper implements BaseConverter<DriversLicenceDetailsEntity, DriversLicenceDetailsModel> {

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private DriversLicenceDetailsService driversLicenceDetailsService;
	
	@Override
	public DriversLicenceDetailsModel convertToModel(DriversLicenceDetailsEntity source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		DriversLicenceDetailsModel model = new DriversLicenceDetailsModel();
		model.setAppId(source.getAppId());
		model.setApplicationId(source.getApplicationId());
		model.setBadgeIssuedDate(source.getBadgeIssuedDate());
		model.setBadgeRtaOfficeCode(source.getBadgeRtaOfficeCode());
		model.setDateOfFirstIssue(source.getDateOfFirstIssue());
		model.setDlIssuedDate(source.getDlIssuedDate());
		model.setDlNo(source.getDlNo());
		model.setDlSequenceId(source.getId().getDlSequenceId());
		model.setDlType(source.getDlType());
		model.setDlVehicleClassCode(source.getDlVehicleClassCode());
		LicenceVehicleClass vehicleClassType = LicenceVehicleClass.getLicenceVehicleClassByCode(source.getDlVehicleClassCode().trim());
		if(!ObjectsUtil.isNull(vehicleClassType)){
			model.setTransportType(vehicleClassType.getTransportType());
			model.setCovName(vehicleClassType.getLabel());
		}
		model.setDrivingSchoolLicenseNo(source.getDrivingSchoolLicenseNo());
		model.setIsTrained(source.getIsTrained());
		model.setLicenceHolderId(source.getId().getLicenceHolderId());
		model.setLlrNo(source.getLlrNo());
		model.setModuleCd(source.getModuleCd());
		model.setObservation(source.getObservation());
		model.setPhotoAttachmentId(source.getPhotoAttachmentId());
		model.setPlannedValidFrom(source.getPlannedValidFrom());
		model.setPlannedValidTo(source.getPlannedValidTo());
		model.setReferenceId(source.getReferenceId());
		model.setRenewalFlag(source.getRenewalFlag());
		model.setRetestFlag(source.getRetestFlag());
		model.setRetestReason(source.getRetestReason());
		model.setSignAttachmentId(source.getSignAttachmentId());
		model.setStatusCode(source.getStatusCode());
		model.setStatusDate(source.getStatusDate());
		model.setStatusRemarks(source.getStatusRemarks());
		model.setStatusValidFrom(source.getStatusValidFrom());
		model.setStatusUpdatedBy(source.getStatusUpdatedBy());
		model.setStatusValidTo(source.getStatusValidTo());
		model.setTestDate(source.getTestDate());
		model.setTestExempted(source.getTestExempted());
		model.setTestExemptedReason(source.getTestExemptedReason());
		model.setTestId(source.getTestId());
		model.setTestResult(source.getTestResult());
		model.setTicketDetails(source.getTicketDetails());
		model.setValidFlg(source.getValidFlg());
		model.setValidFrom(source.getValidFrom());
		model.setValidTo(source.getValidTo());
		model.setRtaOfficeDetails(new RTAOfficeModel());
		model.setDlBadgeNo(source.getDlBadgeNo());
		model.getRtaOfficeDetails().setCode(source.getRtaOfficeId().getCode());
		model.getRtaOfficeDetails().setName(source.getRtaOfficeId().getName());
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
	public DriversLicenceDetailsEntity convertToEntity(DriversLicenceDetailsModel source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		DriversLicenceDetailsEntity entity = new DriversLicenceDetailsEntity();
		entity.setAppId(source.getAppId());
		entity.setApplicationId(source.getApplicationId());
		entity.setBadgeIssuedDate(source.getBadgeIssuedDate());
		entity.setBadgeRtaOfficeCode(source.getBadgeRtaOfficeCode());
		entity.setDateOfFirstIssue(source.getDateOfFirstIssue());
		entity.setDlIssuedDate(source.getDlIssuedDate());
		entity.setDlNo(source.getDlNo());
		entity.setDlType(source.getDlType());
		entity.setDlVehicleClassCode(source.getDlVehicleClassCode());
		entity.setDrivingSchoolLicenseNo(source.getDrivingSchoolLicenseNo());
		entity.setIsTrained(source.getIsTrained());
		entity.setLlrNo(source.getLlrNo());
		entity.setModuleCd(source.getModuleCd());
		entity.setObservation(source.getObservation());
		entity.setPhotoAttachmentId(source.getPhotoAttachmentId());
		entity.setPlannedValidFrom(source.getPlannedValidFrom());
		entity.setPlannedValidTo(source.getPlannedValidTo());
		entity.setReferenceId(source.getReferenceId());
		entity.setRenewalFlag(source.getRenewalFlag());
		entity.setRetestFlag(source.getRetestFlag());
		entity.setRetestReason(source.getRetestReason());
		entity.setRtaOfficeId(rtaOfficeDAO.getRtaOfficeDetailsByCode(source.getRtaOfficeDetails().getCode()));
		entity.setSignAttachmentId(source.getSignAttachmentId());
		entity.setStatusCode(source.getStatusCode());
		entity.setStatusDate(source.getStatusDate());
		entity.setStatusRemarks(source.getStatusRemarks());
		entity.setStatusValidFrom(source.getStatusValidFrom());
		entity.setStatusUpdatedBy(source.getStatusUpdatedBy());
		entity.setStatusValidTo(source.getStatusValidTo());
		entity.setTestDate(source.getTestDate());
		entity.setTestExempted(source.getTestExempted());
		entity.setTestExemptedReason(source.getTestExemptedReason());
		entity.setTestId(source.getTestId());
		entity.setTestResult(source.getTestResult());
		entity.setTicketDetails(source.getTicketDetails());
		entity.setValidFlg(source.getValidFlg());
		entity.setValidFrom(source.getValidFrom());
		entity.setValidTo(source.getValidTo());
		if(!ObjectsUtil.isNull(source.getAoUserDetails())){
			if(!ObjectsUtil.isNull(source.getAoUserDetails().getUserId())){
				entity.setAoUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
			}
		}
		if(!ObjectsUtil.isNull(source.getMviUserDetails())){
			if(!ObjectsUtil.isNull(source.getMviUserDetails().getUserId())){
				entity.setMviUserId(userDAO.findByUserId(source.getAoUserDetails().getUserId()));
			}
		}
		
		return entity;
	}

	@Override
	public List<DriversLicenceDetailsModel> convertToModelList(Collection<DriversLicenceDetailsEntity> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<DriversLicenceDetailsEntity> convertToEntityList(Collection<DriversLicenceDetailsModel> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}
}
