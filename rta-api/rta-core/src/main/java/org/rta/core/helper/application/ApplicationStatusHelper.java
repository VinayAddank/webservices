package org.rta.core.helper.application;

import java.util.Collection;
import java.util.stream.Collectors;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.helper.BaseConverter;
import org.rta.core.model.application.ApplicationStatusModel;
import org.rta.core.service.vehicle.VehicleDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *	@Author sohan.maurya created on Aug 2, 2016.
 */

@Component
public class ApplicationStatusHelper implements BaseConverter<VehicleRCEntity, ApplicationStatusModel> {

    @Autowired
    private VehicleDetailsService vehicleDetailsService;
    @Override
    public ApplicationStatusModel convertToModel(VehicleRCEntity source) {

        ApplicationStatusModel model = new ApplicationStatusModel();

        model.setApplicationNo(source.getVehicleRcId());
        model.setStepNo(" Step " + source.getCurrentStep());
        model.setChassisNumber(source.getChassisNumber());
        model.setMakerClass(vehicleDetailsService.getMakersClass(source.getVehicleRcId()));
        return model;
    }

    @Override
    public VehicleRCEntity convertToEntity(ApplicationStatusModel source) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<ApplicationStatusModel> convertToModelList(Collection<VehicleRCEntity> source) {

        return source.stream().map(s -> convertToModel(s)).collect(Collectors.toList());
    }

    @Override
    public Collection<VehicleRCEntity> convertToEntityList(Collection<ApplicationStatusModel> source) {
        // TODO Auto-generated method stub
        return null;
    }

}
