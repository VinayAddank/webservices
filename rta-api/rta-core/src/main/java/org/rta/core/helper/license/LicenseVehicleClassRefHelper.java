package org.rta.core.helper.license;

import java.util.Collection;
import java.util.stream.Collectors;
import org.rta.core.entity.licence.LicenseVehicleClassRefEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.license.LicenseVehicleClassRefModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.stereotype.Component;

@Component
public class LicenseVehicleClassRefHelper implements BaseConverter<LicenseVehicleClassRefEntity, LicenseVehicleClassRefModel>{
	
	@Override
	public LicenseVehicleClassRefModel convertToModel(LicenseVehicleClassRefEntity source) {
		
		if(ObjectsUtil.isNull(source)){
			return null;	
		}
		LicenseVehicleClassRefModel model = new LicenseVehicleClassRefModel();
		model.setVehicleClassCode(source.getVehicleClassCode());
		model.setLicenceClassType(source.getLicenceClassType());
		model.setAgeGroupCode(source.getAgeGroupCode());
		model.setIdpClass(source.getIdpClass());
		model.setRequiresDoctorCert(source.getRequiresDoctorCert());
		model.setBadgeAvailable(source.getBadgeAvailable());
		model.setHazardous(source.getHazardous());
		model.setValidityPeriod(source.getValidityPeriod());
		model.setMaxAge(source.getMaxAge());
	
		return model;
	}

	@Override
	public LicenseVehicleClassRefEntity convertToEntity(LicenseVehicleClassRefModel source) {
		if(ObjectsUtil.isNull(source)){
		return null;
	}
		LicenseVehicleClassRefEntity entity = new LicenseVehicleClassRefEntity();
		entity.setVehicleClassCode(source.getVehicleClassCode());
		entity.setLicenceClassType(source.getLicenceClassType());
		entity.setAgeGroupCode(source.getAgeGroupCode());
		entity.setIdpClass(source.getIdpClass());
		entity.setRequiresDoctorCert(source.getRequiresDoctorCert());
		entity.setBadgeAvailable(source.getBadgeAvailable());
		entity.setHazardous(source.getHazardous());
		entity.setValidityPeriod(source.getValidityPeriod());
		entity.setMaxAge(source.getMaxAge());
		
		return entity;
	}
		
	@Override
	public Collection<LicenseVehicleClassRefModel> convertToModelList(Collection<LicenseVehicleClassRefEntity> source) {
		if(ObjectsUtil.isNull(source)){
			return null;
		}
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<LicenseVehicleClassRefEntity> convertToEntityList(
			Collection<LicenseVehicleClassRefModel> source) {
		if(ObjectsUtil.isNull(source)){
			return null;
		}
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}
}
