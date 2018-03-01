package org.rta.core.service.vehicle.impl;

import org.rta.core.dao.vehicle.VahanDAO;
import org.rta.core.entity.vehicle.VahanEntity;
import org.rta.core.model.vehicle.RegisteredVehicleModel;
import org.rta.core.model.vehicle.UnregisteredVehicleModel;
import org.rta.core.service.vehicle.VahanServiceCore;
import org.rta.core.utils.ObjectsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VahanServiceCoreImpl implements VahanServiceCore {

    private final VahanDAO vahanDao;

    private final ConversionService conversionService;

    @Autowired
    public VahanServiceCoreImpl(final VahanDAO vahanDao, final ConversionService conversionService) {
        this.vahanDao = vahanDao;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public Long save(UnregisteredVehicleModel uvm) {
        VahanEntity vahanEntity = vahanDao.getByChassisNumber(uvm.getChassisNumber());
        Long id;
        if (ObjectsUtil.isNull(vahanEntity)) {
            id = (Long) vahanDao.save(conversionService.convert(uvm, VahanEntity.class));
        } else {
            vahanEntity.setBodyTypeDesc(uvm.getBodyTypeDesc());
            vahanEntity.setColor(uvm.getColor());
//            vahanEntity.setCreatedBy(uvm.getCreatedBy());
//            vahanEntity.setCreatedOn(uvm.getCreatedOn());
            vahanEntity.setCubicCap(uvm.getCubicCapacity());
            vahanEntity.setEngineNumber(uvm.getEngineNumber());
            vahanEntity.setEnginePower(uvm.getEnginePower());
            vahanEntity.setFrontAxleDesc(uvm.getFrontAxleDesc());
            vahanEntity.setFrontAxleWeight(uvm.getFrontAxleWeight());
            vahanEntity.setFuelUsed(uvm.getFuelUsed());
            vahanEntity.setHeight(uvm.getHeight());
            vahanEntity.setLength(uvm.getLength());
            vahanEntity.setMakersClass(uvm.getMakersClass());
            vahanEntity.setMakersDesc(uvm.getMakersName());
            vahanEntity.setMfgDate(uvm.getManufacturingMonthYear());
            vahanEntity.setNoCyl(uvm.getNoCyl());
            vahanEntity.setO1AxleDesc(uvm.getO1AxleDesc());
            vahanEntity.setO1AxleWeight(uvm.getO1AxleWeight());
            vahanEntity.setO2AxleDesc(uvm.getO2AxleDesc());
            vahanEntity.setO2AxleWeight(uvm.getO2AxleWeight());
            vahanEntity.setO3AxleDesc(uvm.getO3AxleDesc());
            vahanEntity.setO4AxleDesc(uvm.getO4AxleDesc());
            vahanEntity.setO3AxleWeight(uvm.getO3AxleWeight());
            vahanEntity.setO4AxleWeight(uvm.getO4AxleWeight());
            vahanEntity.setO5AxleDesc(uvm.getO5AxleDesc());
            vahanEntity.setO5AxleWeight(uvm.getO5AxleWeight());
            vahanEntity.setPollutionNormsDesc(uvm.getPollutionNormsDesc());
            vahanEntity.setRearAxleDesc(uvm.getRearAxleDesc());
            vahanEntity.setRearAxleWeight(uvm.getRearAxleWeight());
            vahanEntity.setRlw(uvm.getRlw());
            vahanEntity.setUlw(uvm.getUlw());
            vahanEntity.setSeatingCapacity(uvm.getSeatingCapacity());
            vahanEntity.setStandCapacity(uvm.getStandCapacity());
            vahanEntity.setTandemAxelDescp(uvm.getTandemAxelDescp());
            vahanEntity.setTandemAxelWeight(uvm.getTandemAxelWeight());
            vahanEntity.setVehicleClass(uvm.getVehicleClass());
            vahanEntity.setWheelbase(uvm.getWheelbase());
            vahanEntity.setWidth(uvm.getWidth());
            id = vahanEntity.getId();
        }
        return ObjectsUtil.isNull(id) ? null : id;
    }

    @Override
    public Long save(RegisteredVehicleModel vehicleModel) {
        // TODO need to implement if required
        return null;
    }

    @Override
    @Transactional
    public UnregisteredVehicleModel getByChassisNumber(String chassisNumber) {
        return conversionService.convert(vahanDao.getByChassisNumber(chassisNumber), UnregisteredVehicleModel.class);
    }

}
