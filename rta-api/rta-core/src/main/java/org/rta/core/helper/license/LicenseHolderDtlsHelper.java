package org.rta.core.helper.license;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.dao.docs.LicenceAttachmentDetailsDAO;
import org.rta.core.dao.master.DistrictDAO;
import org.rta.core.dao.master.MandalDAO;
import org.rta.core.dao.master.QualificationDAO;
import org.rta.core.dao.master.StateDAO;
import org.rta.core.dao.office.RtaOfficeDAO;
import org.rta.core.entity.docs.LicenceAttachmentDetailsEntity;
import org.rta.core.entity.licence.LicenceIdentitiesDetailsEntity;
import org.rta.core.entity.licence.LicenseHolderDtlsEntity;
import org.rta.core.entity.office.NocAddressEntity;
import org.rta.core.enums.DocTypes;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.license.LicenceIdentitiesDetailsModel;
import org.rta.core.model.license.LicenseHolderDtlsModel;
import org.rta.core.model.master.DistrictModel;
import org.rta.core.model.master.MandalModel;
import org.rta.core.model.master.QualificationModel;
import org.rta.core.model.master.StateModel;
import org.rta.core.model.office.RTAOfficeModel;
import org.rta.core.model.user.AddressModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LicenseHolderDtlsHelper implements BaseConverter<LicenseHolderDtlsEntity, LicenseHolderDtlsModel> {

	@Autowired
	private RtaOfficeDAO rtaOfficeDAO;
	
	@Autowired
	private DistrictDAO districtDAO;
	
	@Autowired
	private MandalDAO mandalDAO;
	
	@Autowired
	private StateDAO stateDAO;
	
	@Autowired
	private QualificationDAO qualificationDAO;
	
	@Autowired
	private	LicenceAttachmentDetailsDAO licenceAttachmentDetailsDAO;

	@Override
	public LicenseHolderDtlsModel convertToModel(LicenseHolderDtlsEntity source) {

		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		LicenseHolderDtlsModel model = new LicenseHolderDtlsModel();
		model.setLicenceHolderId(source.getLicenceHolderId());
		model.setAadhaarNo(source.getAadhaarNo());
		model.setIsSameAsAadhaar(source.isSameAsAadhaar());
		model.setBloodDonor(source.getBloodDonor());
		model.setBloodGrp(source.getBloodGrp());
		model.setCfstApplicantId(source.getCfstApplicantId());
		model.setDateOfBirth(source.getDateOfBirth());
		model.setDisplayName(source.getDisplayName());
		model.setElectoralNumber(source.getElectoralNumber());
		model.setEmail(source.getEmail());
		model.setFirstName(source.getFirstName());
		model.setFirstaidcertified(source.getFirstaidcertified());
		model.setForeignMilitary(source.getForeignMilitary());
		model.setGender(source.getGender());
		model.setGuardianName(source.getGuardianName());
		model.setHandicapDtls(source.getHandicapDtls());
		model.setIsActive(source.getIsActive());
		model.setIsAdharVerify(source.getIsAdharVerify());
		model.setIsHandicapped(source.getIsHandicapped());
		model.setLastName(source.getLastName());
		model.setMobileNo(source.getMobileNo());
		model.setNationality(source.getNationality());
		model.setOrganDonor(source.getOrganDonor());
		model.setOtherstateCd(source.getOtherstateCd());
		model.setPanNo(source.getPanNo());
		model.setPermAddrMandal(source.getPermAddrMandal());
		model.setPermAddrDistrict(source.getPermAddrDistrict());
		model.setPermAddrState(source.getPermAddrState());
		model.setPermAddrCountry(source.getPermAddrCountry());
		model.setDistrictDetails(new DistrictModel());
		model.getDistrictDetails().setCode(source.getPresAddrDistrictId().getCode());
		model.getDistrictDetails().setName(source.getPresAddrDistrictId().getName());
		model.setPermAddrDoorNo(source.getPermAddrDoorNo());
		model.setMandalDetails(new MandalModel());
		model.getMandalDetails().setCode(source.getPresAddrMandalId().getCode());
		model.getMandalDetails().setName(source.getPresAddrMandalId().getName());
		model.setPermAddrPinCode(source.getPermAddrPinCode());
		model.setStateDetails(new StateModel());
		model.getStateDetails().setCode(source.getPresAddrStateId().getCode());
		model.getStateDetails().setName(source.getPresAddrStateId().getName());
		model.setPermAddrStreet(source.getPermAddrStreet());
		model.setPermAddrTown(source.getPermAddrTown());
		model.setPresAddrCountryId(source.getPresAddrCountryId());
		model.setPresAddrDoorNo(source.getPresAddrDoorNo());
		model.setPresAddrPinCode(source.getPresAddrPinCode());
		model.setPresAddrStreet(source.getPresAddrStreet());
		model.setPresAddrTown(source.getPresAddrTown());
		model.setRtaOfficeDetails(new RTAOfficeModel());
		model.getRtaOfficeDetails().setName(source.getRtaOfficeId().getName());
		model.getRtaOfficeDetails().setCode(source.getRtaOfficeId().getCode());
		model.setTicketDetails(source.getTicketDetails());
		model.setTwotire(source.getTwotire());
		model.setQualificationDetails(new QualificationModel());
		if(!ObjectsUtil.isNull(source.getQualificationId())){
			model.getQualificationDetails().setName(source.getQualificationId().getName());
			model.getQualificationDetails().setCode(source.getQualificationId().getCode());
		}
		if(!ObjectsUtil.isNull(source.getLicenceIdentityId())){
			LicenceIdentitiesDetailsEntity identitiesDetailsEntity = source.getLicenceIdentityId();
			LicenceIdentitiesDetailsModel liIdentitiesDetails = new LicenceIdentitiesDetailsModel();
			liIdentitiesDetails.setPassportNumber(identitiesDetailsEntity.getPassportNumber());
			liIdentitiesDetails.setPassportValidFrom(identitiesDetailsEntity.getPassportValidFrom());
			liIdentitiesDetails.setPassportValidTo(identitiesDetailsEntity.getPassportValidTo());
			liIdentitiesDetails.setVisaValidFrom(identitiesDetailsEntity.getVisaValidFrom());
			liIdentitiesDetails.setVisaValidTo(identitiesDetailsEntity.getVisaValidTo());
			liIdentitiesDetails.setDlNumber(identitiesDetailsEntity.getDlNumber());
			liIdentitiesDetails.setDlValidFrom(identitiesDetailsEntity.getDlValidFrom());
			liIdentitiesDetails.setDlValidTo(identitiesDetailsEntity.getDlValidTo());
			if(!ObjectsUtil.isNull(identitiesDetailsEntity.getRtaOfficeAddressId())){
				NocAddressEntity nocAddressEntity = identitiesDetailsEntity.getRtaOfficeAddressId();
				
				AddressModel addressModel = new AddressModel();
				addressModel.setCity(nocAddressEntity.getNocAddress());
				addressModel.setDistrictCode(nocAddressEntity.getDistrictId().getCode());
				addressModel.setDistrictName(nocAddressEntity.getDistrictId().getName());
				addressModel.setStateCode(nocAddressEntity.getDistrictId().getStateEntity().getCode());
				addressModel.setStateName(nocAddressEntity.getDistrictId().getStateEntity().getName());
				liIdentitiesDetails.setRtaOfficeAddressDetails(addressModel);
				
				RTAOfficeModel rtaOfficeModel = new RTAOfficeModel();
				rtaOfficeModel.setCode(nocAddressEntity.getRtaOfficeId().getCode());
				rtaOfficeModel.setName(nocAddressEntity.getRtaOfficeId().getName());
				liIdentitiesDetails.setRtaOfficeDetails(rtaOfficeModel);
				liIdentitiesDetails.setRtaOfficeAddressCode(nocAddressEntity.getNocAddressCode());
			}
			model.setLicenceIdentitiesDetails(liIdentitiesDetails);
		}
		List<LicenceAttachmentDetailsEntity> attachments=licenceAttachmentDetailsDAO.getAllAttachmentDetails(source.getLicenceHolderId());
		if(!ObjectsUtil.isNull(attachments)){
			for(LicenceAttachmentDetailsEntity attachment:attachments){
				if(attachment.getDocTypes().getDocTypeId() == DocTypes.DOC_OWNER_PHOTO.getValue()){
					model.setOwnerPhotograph(attachment.getSource());
				}
				if(attachment.getDocTypes().getDocTypeId() == DocTypes.DOC_USER_SIGNATURE.getValue()){
					model.setOwnerSignature(attachment.getSource());
				}
			}
		}
		return model;
	}

	@Override
	public LicenseHolderDtlsEntity convertToEntity(LicenseHolderDtlsModel source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		LicenseHolderDtlsEntity entity = new LicenseHolderDtlsEntity();
		entity.setAadhaarNo(source.getAadhaarNo());
		entity.setBloodDonor(source.getBloodDonor());
		entity.setBloodGrp(source.getBloodGrp());
		entity.setCfstApplicantId(source.getCfstApplicantId());
		entity.setDateOfBirth(source.getDateOfBirth());
		entity.setDisplayName(source.getDisplayName());
		entity.setElectoralNumber(source.getElectoralNumber());
		entity.setEmail(source.getEmail());
		entity.setFirstName(source.getFirstName());
		entity.setFirstaidcertified(source.getFirstaidcertified());
		entity.setForeignMilitary(source.getForeignMilitary());
		entity.setGender(source.getGender());
		entity.setGuardianName(source.getGuardianName());
		entity.setHandicapDtls(source.getHandicapDtls());
		entity.setIsActive(source.getIsActive());
		entity.setIsAdharVerify(source.getIsAdharVerify());
		entity.setIsHandicapped(source.getIsHandicapped());
		entity.setLastName(source.getLastName());
		entity.setMobileNo(source.getMobileNo());
		entity.setNationality(source.getNationality());
		entity.setOrganDonor(source.getOrganDonor());
		entity.setOtherstateCd(source.getOtherstateCd());
		entity.setPanNo(source.getPanNo());
		entity.setPermAddrDoorNo(source.getPermAddrDoorNo());
		entity.setPermAddrStreet(source.getPermAddrStreet());
		entity.setPermAddrTown(source.getPermAddrTown());
		entity.setPermAddrMandal(source.getPermAddrMandal());
		entity.setPermAddrDistrict(source.getPermAddrDistrict());
		entity.setPermAddrState(source.getPermAddrState());
		entity.setPermAddrCountry(source.getPermAddrCountry());
		entity.setPermAddrPinCode(source.getPermAddrPinCode());
		entity.setPresAddrDistrictId(districtDAO.getDistrictByCode(source.getDistrictDetails().getCode()));
		entity.setPresAddrMandalId(mandalDAO.getByMandalCode(source.getMandalDetails().getCode()));
		entity.setPresAddrStateId(stateDAO.getStateByCode(source.getStateDetails().getCode()));
		entity.setPresAddrCountryId(source.getPresAddrCountryId());
		entity.setPresAddrDoorNo(source.getPresAddrDoorNo());
		entity.setPresAddrPinCode(source.getPresAddrPinCode());
		entity.setPresAddrStreet(source.getPresAddrStreet());
		entity.setPresAddrTown(source.getPresAddrTown());
		entity.setQualificationId(qualificationDAO.getByCode(source.getQualificationDetails().getCode()));
		entity.setRtaOfficeId(rtaOfficeDAO.getRtaOfficeDetailsByCode(source.getRtaOfficeDetails().getCode()));
		entity.setTicketDetails(source.getTicketDetails());
		entity.setTwotire(source.getTwotire());
		entity.setSameAsAadhaar(source.getIsSameAsAadhaar());
		return entity;
	}

	@Override
	public Collection<LicenseHolderDtlsModel> convertToModelList(Collection<LicenseHolderDtlsEntity> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<LicenseHolderDtlsEntity> convertToEntityList(Collection<LicenseHolderDtlsModel> source) {
		if (ObjectsUtil.isNull(source)) {
			return null;
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}

}
