package org.rta.core.service.master.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.rta.core.dao.master.impl.RegistrationCategoryDAOImpl;
import org.rta.core.dao.vehicle.VehicleDAO;
import org.rta.core.entity.master.RegistrationCategoryEntity;
import org.rta.core.entity.vehicle.VehicleRCEntity;
import org.rta.core.helper.master.RegistrationCategoryHelper;
import org.rta.core.model.master.RegistrationCategoryModel;
import org.rta.core.service.master.RegistrationCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationCategoryServiceImpl implements RegistrationCategoryService {

    @Autowired
    private RegistrationCategoryDAOImpl registrationCategoryDAO;

    @Autowired
    private RegistrationCategoryHelper registrationCategoryHelper;

    @Autowired
    private VehicleDAO vehicleDao;
    
    @Override
    @Transactional
    public List<RegistrationCategoryModel> getAll() {
        return (List<RegistrationCategoryModel>) registrationCategoryHelper
                .convertToModelList(registrationCategoryDAO.getAll());
    }

    @Override
    public RegistrationCategoryModel get(Long registrationCategoryId) {
        return registrationCategoryHelper.convertToModel(registrationCategoryDAO.getEntity(RegistrationCategoryEntity.class, registrationCategoryId));
    }
    
    @Override
    @Transactional
	public RegistrationCategoryModel getRegistrationCategoryDetails(Long vehicleRcId) {

		VehicleRCEntity vehicleRCEntity = vehicleDao.get(vehicleRcId);
		return registrationCategoryHelper.convertToModel(vehicleRCEntity.getRegCategory());
	}

	@Override
	public Boolean getIsVehicleReassignmentApplicable(String prNumber) {
		// TODO after data migration we will apply the logic that is vehicle reassignment applicable.
		// TODO for now it will return true.
		return true;
	}
}
