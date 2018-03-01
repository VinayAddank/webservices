package org.rta.core.service.master.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.certificate.PermitVehicleClassMappingDAO;
import org.rta.core.dao.master.PermitTypeDAO;
import org.rta.core.entity.certificate.PermitVehicleClassMappingEntity;
import org.rta.core.entity.master.PermitTypeEntity;
import org.rta.core.model.master.PermitTypeModel;
import org.rta.core.service.master.PermitTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermitTypeServiceImpl implements PermitTypeService {
	
    @Autowired
    private PermitVehicleClassMappingDAO permitVehicleClassMappingDAO;

//    @Autowired
//    private VehicleDAO vehicleDAO;
//
//    @Autowired
//    private VehicleClassDescDAO vehicleClassDescDAO;
//    
//    @Autowired
//    private VehicleAlterationDAO vehicleAlterationDAO;
//    
    @Autowired
    private PermitTypeDAO permitTypeDAO;

    @Override
    @Transactional
    public List<PermitTypeModel> getPermitType() {
        List<PermitTypeEntity> entities = permitTypeDAO.getAll(true);
        List<PermitTypeModel> permitTypeModels = new ArrayList<PermitTypeModel>();
        PermitTypeModel model = null;
        for (PermitTypeEntity entity : entities) {
            model = new PermitTypeModel();
            model.setCode(entity.getCode());
            model.setName(entity.getName());
            permitTypeModels.add(model);
        }
        return permitTypeModels;
    }
    
    @Override
    @Transactional
    public List<PermitTypeModel> getPermitType(String vehicleClassDescCode) {
        List<PermitVehicleClassMappingEntity> entities =
                permitVehicleClassMappingDAO.getPermitTypeList(vehicleClassDescCode);
        List<PermitTypeModel> permitTypeModels = new ArrayList<PermitTypeModel>();
        PermitTypeModel model = null;
        for (PermitVehicleClassMappingEntity entity : entities) {
            model = new PermitTypeModel();
            model.setCode(entity.getPermitType().getCode());
            model.setName(entity.getPermitType().getName());
            permitTypeModels.add(model);
        }
        return permitTypeModels;
    }



//    @Override
//    @Transactional
//    public PermitTypeVehicleClassModel getPermitTypeVehicleClass(String trPrNumber) {
//        String vehicleClassCode = vehicleAlterationDAO.getVehicleAlterationDetails(vehicleDAO.getByPROrTrNumber(trPrNumber).getVehicleRcId()).getVehicleSubClass();        
//        List<PermitVehicleClassMappingEntity> entities = permitVehicleClassMappingDAO.getPermitTypeList(vehicleClassCode);        
//        VehicleClassDescriptionEntity vcEntity = vehicleClassDescDAO.getByVehiclClassDesCode(vehicleClassCode);   
//        List<PermitTypeModel> permitTypeModels = new ArrayList<PermitTypeModel>();
//        PermitTypeModel model = null;
//        for (PermitVehicleClassMappingEntity entity : entities) {
//            model = new PermitTypeModel();
//            model.setCode(entity.getPermitType().getCode());
//            model.setName(entity.getPermitType().getName());
//            permitTypeModels.add(model);
//        }
//        PermitTypeVehicleClassModel pvModel = new PermitTypeVehicleClassModel();
//        pvModel.setPermitType(permitTypeModels);
//        pvModel.setVehicleClassDetails(new VehicleClassDescModel());
//        pvModel.getVehicleClassDetails().setVehicleCode(vcEntity.getCode());
//        pvModel.getVehicleClassDetails().setVehicleDesc(vcEntity.getDescription());
//        return pvModel;
//    }
}
