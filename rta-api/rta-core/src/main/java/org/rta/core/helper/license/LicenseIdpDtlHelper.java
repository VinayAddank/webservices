package org.rta.core.helper.license;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.licence.LicenseIdpDtlEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.license.LicenseIdpDtlModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LicenseIdpDtlHelper implements BaseConverter<LicenseIdpDtlEntity, LicenseIdpDtlModel> {

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;
	
	@Override
	public LicenseIdpDtlModel convertToModel(LicenseIdpDtlEntity source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		LicenseIdpDtlModel model = new LicenseIdpDtlModel();
		model.setApplicationNo(source.getApplicationNo());
		model.setApprovedBy(source.getApplicationNo());
		model.setIdpLicenseNo(source.getIdpLicenseNo());
		model.setIdpVehicleClassCode(source.getIdpVehicleClassCode());
		model.setIssueDate(source.getIssueDate());
		model.setPassportNo(source.getPassportNo());
		model.setPassportValidTo(source.getPassportValidTo());
		model.setPhotoAttachmentId(source.getPhotoAttachmentId());
		model.setRtaOfficeDetails(new RTAOfficeModel());
		model.getRtaOfficeDetails().setCode(source.getRtaOfficeId().getCode());
		model.getRtaOfficeDetails().setName(source.getRtaOfficeId().getName());
		model.setStatus(source.getStatus());
		model.setStatusRemarks(source.getStatusRemarks());
		model.setTicketDetails(source.getTicketDetails());
		model.setValidFrom(source.getValidFrom());
		model.setValidTo(source.getValidTo());
		return model;
	}

	@Override
	public LicenseIdpDtlEntity convertToEntity(LicenseIdpDtlModel source) {
		LicenseIdpDtlEntity entity = new LicenseIdpDtlEntity();
		entity.setApplicationNo(source.getApplicationNo());
		entity.setApprovedBy(source.getApprovedBy());
		entity.setIdpLicenseNo(source.getIdpLicenseNo());
		entity.setIdpVehicleClassCode(source.getIdpVehicleClassCode());
		entity.setIssueDate(source.getIssueDate());
		entity.setPassportNo(source.getPassportNo());
		entity.setPassportValidTo(source.getPassportValidTo());
//		entity.setPhotoAttachmentId(source.getPhotoAttachmentId());
		entity.setRtaOfficeId(rtaOfficeDAO.getRtaOfficeDetailsByCode(source.getRtaOfficeDetails().getCode()));
		entity.setStatus(source.getStatus());
		entity.setStatusRemarks(source.getStatusRemarks());
//		entity.setTicketDetails(source.getTicketDetails());
		entity.setValidFrom(source.getValidFrom());
		entity.setValidTo(source.getValidTo());
		return entity;
	}

	@Override
	public Collection<LicenseIdpDtlModel> convertToModelList(Collection<LicenseIdpDtlEntity> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<LicenseIdpDtlEntity> convertToEntityList(Collection<LicenseIdpDtlModel> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}

}
