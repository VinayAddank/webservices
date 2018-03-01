package org.rta.core.helper.vehicle;

import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.core.convert.converter.Converter;

public class UnregisteredVehicleModelToVehicleDetailsEntity
        implements Converter<UnregisteredVehicleModel, VehicleDetailsEntity> {

    @Override
    public VehicleDetailsEntity convert(UnregisteredVehicleModel source) {
        VehicleDetailsEntity vde = new VehicleDetailsEntity();
        vde.setChassisNo(source.getChassisNumber());
        vde.setColor(source.getColor());
        vde.setEngineNo(source.getEngineNumber());
        vde.setFuelUsed(source.getFuelUsed());
        vde.setMakerClass(source.getMakersClass());
        vde.setMakerName(source.getMakersName());
        vde.setMfgDate(source.getManufacturingMonthYear());
        vde.setRlw(source.getRlw());
        vde.setSeatingCapacity(source.getSeatingCapacity());
        vde.setUlw(source.getUlw());
        vde.setVehicleClass(source.getVehicleClass());
        vde.setVehicleDtlId(source.getVehicleDetailsId());
        vde.setBodyTypeUpdated(source.getBodyTypeUpdated());
        vde.setLengthUpdated(source.getLengthUpdated());
        vde.setHeightUpdated(source.getHeightUpdated());
        vde.setWidthUpdated(source.getWidthUpdated());
        vde.setCompletionDate(source.getCompletionDate());
        if (!ObjectsUtil.isNull(source.getAlterationCategory()))
            vde.setAlterationCategory(source.getAlterationCategory().getValue());
        return vde;
    }



}
