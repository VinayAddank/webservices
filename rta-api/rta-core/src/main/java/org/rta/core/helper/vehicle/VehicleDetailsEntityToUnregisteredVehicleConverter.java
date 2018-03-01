package org.rta.core.helper.vehicle;

import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.enums.AlterationCategory;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.core.convert.converter.Converter;

public class VehicleDetailsEntityToUnregisteredVehicleConverter implements Converter<VehicleDetailsEntity, UnregisteredVehicleModel> {

    @Override
    public UnregisteredVehicleModel convert(VehicleDetailsEntity source) {
        UnregisteredVehicleModel target = new UnregisteredVehicleModel();
        target.setChassisNumber(source.getChassisNo());
        target.setColor(source.getColor());
        target.setEngineNumber(source.getEngineNo());
        target.setFuelUsed(source.getFuelUsed());
        target.setMakersClass(source.getMakerClass());
        target.setManufacturingMonthYear(source.getMfgDate());
        target.setRlw(source.getRlw());
        target.setUlw(source.getUlw());
        target.setTaxType(source.getTaxTypeId().getCode());
        target.setTaxName(source.getTaxTypeId().getName());
        target.setVehicleClass(source.getVehicleClass());
        target.setMakersName(source.getMakerName());
        target.setVehicleDetailsId(source.getVehicleDtlId());
        target.setCreatedBy(source.getCreatedBy());
        target.setCreatedOn(source.getCreatedOn());
        target.setModifiedBy(source.getModifiedBy());
        target.setModifiedOn(source.getModifiedOn());
        target.setSeatingCapacity(source.getSeatingCapacity());
        target.setBodyTypeUpdated(source.getBodyTypeUpdated());
        target.setLengthUpdated(source.getLengthUpdated());
        target.setHeightUpdated(source.getHeightUpdated());
        target.setWidthUpdated(source.getWidthUpdated());
        target.setCompletionDate(source.getCompletionDate());
        if (!ObjectsUtil.isNull(source.getAlterationCategory()))
            target.setAlterationCategory(AlterationCategory.getAlterationCategory(source.getAlterationCategory()));
        if (!ObjectsUtil.isNull(source.getVehicleCategory()))
            target.setVehicleCategory(source.getVehicleCategory());

        return target;
    }
    
}
