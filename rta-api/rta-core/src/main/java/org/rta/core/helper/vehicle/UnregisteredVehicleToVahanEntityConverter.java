package org.rta.core.helper.vehicle;

import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.springframework.core.convert.converter.Converter;

public class UnregisteredVehicleToVahanEntityConverter implements Converter<UnregisteredVehicleModel, VahanEntity> {

    @Override
    public VahanEntity convert(UnregisteredVehicleModel source) {
        VahanEntity target = new VahanEntity();
        target.setBodyTypeDesc(source.getBodyTypeDesc());
        target.setChassisNumber(source.getChassisNumber());
        target.setColor(source.getColor());
        target.setCubicCap(source.getCubicCapacity());
        target.setEngineNumber(source.getEngineNumber());
        target.setEnginePower(source.getEnginePower());
        target.setFrontAxleDesc(source.getFrontAxleDesc());
        target.setFrontAxleWeight(source.getFrontAxleWeight());
        target.setFuelUsed(source.getFuelUsed());
        target.setHeight(source.getHeight());
        target.setId(null);
        target.setLength(source.getLength());
        target.setMakersClass(source.getMakersClass());
        target.setMakersDesc(source.getMakersName());
        target.setMfgDate(source.getManufacturingMonthYear());
        target.setNoCyl(source.getNoCyl());
        target.setO1AxleDesc(source.getO1AxleDesc());
        target.setO1AxleWeight(source.getO1AxleWeight());
        target.setO2AxleDesc(source.getO2AxleDesc());
        target.setO2AxleWeight(source.getO2AxleWeight());
        target.setO3AxleDesc(source.getO3AxleDesc());
        target.setO3AxleWeight(source.getO3AxleWeight());
        target.setO4AxleDesc(source.getO4AxleDesc());
        target.setO4AxleWeight(source.getO4AxleWeight());
        target.setO5AxleDesc(source.getO5AxleDesc());
        target.setO5AxleWeight(source.getO5AxleWeight());
        target.setPollutionNormsDesc(source.getPollutionNormsDesc());
        target.setRearAxleDesc(source.getRearAxleDesc());
        target.setRearAxleWeight(source.getRearAxleWeight());
        target.setRlw(source.getRlw());
        target.setSeatingCapacity(source.getSeatingCapacity());
        target.setStandCapacity(source.getStandCapacity());
        target.setTandemAxelDescp(source.getTandemAxelDescp());
        target.setTandemAxelWeight(source.getTandemAxelWeight());
        target.setUlw(source.getUlw());
        target.setVehicleClass(source.getVehicleClass());
        target.setWheelbase(source.getWheelbase());
        target.setWidth(source.getWidth());
        return target; 
    }

}
