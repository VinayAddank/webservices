package org.rta.registration.service.vahan.impl;

import java.util.Objects;

import org.apache.log4j.Logger;
import org.rta.core.exception.InvalidEngineNumberException;
import org.rta.core.model.vehicle.EngineModel;
import org.rta.core.model.vehicle.RegisteredVehicleModel;
import org.rta.core.service.vehicle.VahanServiceCore;
import org.rta.core.service.vehicle.VehicleService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.rta.registration.service.vahan.VahanService;
import org.rta.vahan.api.ResponseType;
import org.rta.vahan.api.VahanResponseModel;
import org.rta.vahan.api.registered.VahanClient;
import org.rta.vahan.api.registered.model.RegisteredVehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VahanServiceImpl implements VahanService {

    private static final Logger logger = Logger.getLogger(VahanServiceImpl.class);
    private static final String VAHAN_CLIENT_ID = "AP001";

    private final VehicleService vehicleService;
    private final VahanClient vahanClient;
    private final VahanServiceCore vahanService;

    private final ConversionService conversionService;

    @Autowired
    public VahanServiceImpl(final VehicleService vehicleService, final ConversionService conversionService,
            final VahanClient vahanClient, final VahanServiceCore vahanService) {
        this.vehicleService = vehicleService;
        this.conversionService = conversionService;
        this.vahanClient = vahanClient;
        this.vahanService = vahanService;
    }

    @Override
    @Transactional
    public RegisteredVehicleModel getVehicleInfo(String chassisNumber) {
        RegisteredVehicleModel vm = getVahanDetails(chassisNumber);
        if (!ObjectsUtil.isNull(vm)) {
            return RegisteredVehicleModel.createBasicVehicleModel(vm);
        }
        return null;
    }

    private RegisteredVehicleModel getVahanDetails(String chassisNumber) {
        long currentTimeMilliSec = System.currentTimeMillis(); 
        VahanResponseModel<RegisteredVehicleDetails> vrm = vahanClient.getChasisDetails(VAHAN_CLIENT_ID, chassisNumber);
        logger.debug("time taken : " + (System.currentTimeMillis() - currentTimeMilliSec));
        if (Objects.isNull(vrm)) {
            return null;
        }
        RegisteredVehicleModel vm = null;
        if (vrm.getResponseType() == ResponseType.SUCCESS) {
            vm = conversionService.convert(vrm.getResponseModel(), RegisteredVehicleModel.class);
            return vm;
        }
        return null;
    }

    @Override
    public RegisteredVehicleModel getVehicleDetails(String chassisNumber, String engineNumber)
            throws InvalidEngineNumberException {
        RegisteredVehicleModel vehicleModel = getVahanDetails(chassisNumber);
        if (ObjectsUtil.isNull(vehicleModel)) {
            return null;
        }
        EngineModel engine = null;
        if (!ObjectsUtil.isNull(engine) && !ObjectsUtil.isNull(engine.getEngineNumber())) {
            // validate engine number
            if (StringsUtil.isEqualIgnoreCase(engineNumber, engine.getEngineNumber())) {
                // save vahan info
                Long generatedId = (Long) vahanService.save(vehicleModel);
                return ObjectsUtil.isNull(generatedId) ? null : RegisteredVehicleModel.createBasicVehicleModel(vehicleModel);
            }
            throw new InvalidEngineNumberException();
        }
        return null;
    }
    
    /*public VehicleModel getVehicleDetails(String chassisNumber, String engineNumber)
            throws InvalidEngineNumberException {
        VehicleModel vehicleModel = getVahanDetails(chassisNumber);
        if (ObjectsUtil.isNull(vehicleModel)) {
            return null;
        }
        EngineModel engine = vehicleModel.getEngine();
        if (!ObjectsUtil.isNull(engine) && !ObjectsUtil.isNull(engine.getEngineNumber())) {
            // validate engine number
            if (StringsUtil.isEqualIgnoreCase(engineNumber, engine.getEngineNumber())) {
                // save vahan info
                Long generatedId = (Long) vehicleService.save(vehicleModel);
                return ObjectsUtil.isNull(generatedId) ? null : VehicleModel.createBasicVehicleModel(vehicleModel);
            }
            throw new InvalidEngineNumberException();
        }
        return null;
    }*/

}
