package org.rta.core.service.vehicle.cms.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.rta.core.dao.payment.cms.VehicleQuarterlyDAO;
import org.rta.core.dao.vehicle.VehicleDetailsDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDAO;
import org.rta.core.dao.vehicle.cms.VehicleClassDescDAO;
import org.rta.core.entity.vehicle.VehicleDetailsEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassDescriptionEntity;
import org.rta.core.entity.vehicle.cms.VehicleClassEntity;
import org.rta.core.enums.master.TaxType;
import org.rta.core.helper.vehicle.cms.VehicleClassDescHelper;
import org.rta.core.helper.vehicle.cms.VehicleClassHelper;
import org.rta.core.model.master.TaxTypeModel;
import org.rta.core.model.vehicle.cms.VehicleClassDescModel;
import org.rta.core.model.vehicle.cms.VehicleClassModel;
import org.rta.core.service.vehicle.cms.VehicleMappingService;
import org.rta.core.utils.ObjectsUtil;
import org.rta.core.utils.StringsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleMappingServiceImpl implements VehicleMappingService {

	private static final Logger log = Logger.getLogger(VehicleMappingServiceImpl.class);
    
	@Autowired
	VehicleClassDAO vehicleClassDAO;

	@Autowired
	VehicleClassDescDAO vehicleClassDescDAO;

	@Autowired
	VehicleClassHelper vehicleClasHelper;

	@Autowired
	VehicleClassDescHelper vehicleDescHelper;
	
	@Autowired
	VehicleQuarterlyDAO vehicleQuarterlyDAO;
	
	@Autowired
	private VehicleDetailsDAO vehicleDetailsDAO;
	
	public VehicleMappingServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Transactional
	public List<VehicleClassModel> getAllVehicleClass() {
		List<VehicleClassEntity> vehicleEntities = vehicleClassDAO.getAll();
		if (vehicleEntities == null || vehicleEntities.size() == 0)
			throw new IllegalArgumentException("No Vehicle Class Entry found");
		List<VehicleClassModel> vehicleClassModels = new ArrayList<>();
		vehicleClassModels = (List<VehicleClassModel>) vehicleClasHelper.convertToModelList(vehicleEntities);
		return vehicleClassModels;
	}

	@Transactional
    public List<VehicleClassDescModel> getAllVehicleClassDescription(String regCategoryCode, String vehicleClassCode) {
		if (StringsUtil.isNullOrEmpty(vehicleClassCode))
			throw new IllegalArgumentException("Invalid vehicle Class Code");
		List<VehicleClassDescriptionEntity> descEntities = vehicleClassDescDAO
                .getAllClassDes4VehicleCateg(regCategoryCode, vehicleClassCode, null, false);

		return (List<VehicleClassDescModel>) vehicleDescHelper.convertToModelList(descEntities);

	}
	
	
	@Transactional
    public TaxTypeModel getTaxType4VehicleSubClass(String vehicleSubClassCode, Integer seatingCapacity) {
		if (StringsUtil.isNullOrEmpty(vehicleSubClassCode))
			throw new IllegalArgumentException("Invalid vehicle Sub Class Code");
        TaxTypeModel taxTypeModel = new TaxTypeModel();
//        List<String> descEntities =
//                vehicleClassDescDAO.getAllClassDesCode4VehicleCateg(regCategoryCode, vehicleClassCode);
        
        if ("OBPN".equalsIgnoreCase(vehicleSubClassCode) && null != seatingCapacity) {
            if (seatingCapacity > 10) {
                taxTypeModel.setName(TaxType.FIRST_QUARTERLY.getLabel());
                taxTypeModel.setTaxTypeCode("FQ");
                taxTypeModel.setId(1l);
                return taxTypeModel;
            } else {
                taxTypeModel.setName(TaxType.LIFE_TAX.getLabel());
                taxTypeModel.setTaxTypeCode("LT");
                taxTypeModel.setId(2l);
                return taxTypeModel;
            }
        }

        if ("MTLT".equalsIgnoreCase(vehicleSubClassCode) || "LTCT".equalsIgnoreCase(vehicleSubClassCode)) {
            taxTypeModel.setName(TaxType.LIFE_TAX.getLabel());
            taxTypeModel.setTaxTypeCode("LT");
            taxTypeModel.setId(2l);
            return taxTypeModel;
        }
        
		List<String> vehicleQuarters = vehicleQuarterlyDAO.getAllCodes();
        if (vehicleQuarters!=null && !vehicleQuarters.isEmpty() && vehicleQuarters.contains(vehicleSubClassCode)) {
            taxTypeModel.setName(TaxType.FIRST_QUARTERLY.getLabel());
            taxTypeModel.setTaxTypeCode("FQ");
            taxTypeModel.setId(1l);
            return taxTypeModel;
        } else {
            taxTypeModel.setName(TaxType.LIFE_TAX.getLabel());
            taxTypeModel.setTaxTypeCode("LT");
            taxTypeModel.setId(2l);
            return taxTypeModel;
        }
	}

	@Transactional
	@Override
    public List<VehicleClassDescModel> getVehicleClassDesc(String regCategoryCode, String classCode, Integer alterationCategory, boolean isNewVehicle) {
		
		List<VehicleClassDescriptionEntity> descEntities = vehicleClassDescDAO
                .getAllClassDes4VehicleCateg(regCategoryCode, classCode, alterationCategory, isNewVehicle);
        return (List<VehicleClassDescModel>) vehicleDescHelper.convertToModelList(descEntities);

    }
	
	@Transactional
    @Override
    public VehicleClassDescModel getVehicleClassDesc(String prNumber) {
	    VehicleClassDescModel model = new VehicleClassDescModel();
	    VehicleDetailsEntity vehDetails = vehicleDetailsDAO.getVehicleDtlsByPrNumber(prNumber);
	    if(ObjectsUtil.isNull(vehDetails)){
	        log.error("Vehicle details not found for pr number : " + prNumber);
	        throw new IllegalArgumentException("Vehicle details not found !!!");
	    }
	    VehicleClassDescriptionEntity classEntity = vehicleClassDescDAO.getByVehiclClassDesCode(vehDetails.getVehicleSubClass());
	    if(ObjectsUtil.isNull(classEntity)){
            log.error("vehicle Class Description not found for subclass : " + vehDetails.getVehicleSubClass());
            return model;
        }
	    model.setVehicleCode(classEntity.getCode());
	    model.setVehicleDesc(classEntity.getDescription());
        return model;
    }
}
