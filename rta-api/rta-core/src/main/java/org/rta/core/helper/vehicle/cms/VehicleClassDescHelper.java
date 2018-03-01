package org.rta.core.helper.vehicle.cms;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.springframework.stereotype.Component;


@Component
public class VehicleClassDescHelper  implements BaseConverter<VehicleClassDescriptionEntity,VehicleClassDescModel> {

	@Override
	public VehicleClassDescModel convertToModel(VehicleClassDescriptionEntity source) {
		VehicleClassDescModel model=new VehicleClassDescModel();
		model.setVehicleCode(source.getCode());
		model.setVehicleDesc(source.getDescription());
		model.setAlterationCategory(source.getAlterationCategory());
		return model;
	}

	@Override
	public VehicleClassDescriptionEntity convertToEntity(VehicleClassDescModel source) {
		VehicleClassDescriptionEntity entity=new VehicleClassDescriptionEntity();
		entity.setCode(source.getVehicleCode());
		entity.setDescription(source.getVehicleDesc());
		return entity;
	}

	@Override
	public Collection<VehicleClassDescModel> convertToModelList(Collection<VehicleClassDescriptionEntity> source) {
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<VehicleClassDescriptionEntity> convertToEntityList(Collection<VehicleClassDescModel> source) {
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}

}
