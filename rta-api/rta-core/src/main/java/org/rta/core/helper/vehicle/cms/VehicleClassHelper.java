package org.rta.core.helper.vehicle.cms;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.model.vehicle.cms.VehicleClassModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class VehicleClassHelper  implements BaseConverter<VehicleClassEntity,VehicleClassModel> {

	@Override
	public VehicleClassModel convertToModel(VehicleClassEntity source) {
		VehicleClassModel model=new VehicleClassModel();
		model.setVehicleClassCode(source.getVehicleClassCode());
		model.setVehicleCategory(source.getVehicleCateg());
		return model;
	}

	@Override
	public VehicleClassEntity convertToEntity(VehicleClassModel model) {
		VehicleClassEntity vehicleClassEntity=new VehicleClassEntity();
		vehicleClassEntity.setVehicleClassCode(model.getVehicleClassCode());
		vehicleClassEntity.setVehicleCateg(model.getVehicleCategory());
		return vehicleClassEntity;
	}

	@Override
	public Collection<VehicleClassModel> convertToModelList(Collection<VehicleClassEntity> source) {
		return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
	}

	@Override
	public Collection<VehicleClassEntity> convertToEntityList(Collection<VehicleClassModel> source) {
		return source.stream().map(s -> convertToEntity(s)).collect(Collectors.toList());
	}

	

	/*@Override
	public VehicleClassEntity convert(VehicleClassModel model) {
		VehicleClassEntity vehicleClassEntity=new VehicleClassEntity();
		vehicleClassEntity.setCode(model.getVehicleClassCode());
		vehicleClassEntity.setVehicleCategory(model.getVehicleCategory());
		return vehicleClassEntity;
	}*/
	
	
	

}
