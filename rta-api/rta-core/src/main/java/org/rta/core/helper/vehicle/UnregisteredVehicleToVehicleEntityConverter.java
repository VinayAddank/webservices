package org.rta.core.helper.vehicle;

import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.springframework.core.convert.converter.Converter;

public class UnregisteredVehicleToVehicleEntityConverter implements Converter<UnregisteredVehicleModel, VehicleRCEntity> {

    @Override
    public VehicleRCEntity convert(UnregisteredVehicleModel source) {
        VehicleRCEntity target = new VehicleRCEntity();
        return null;
    }

}
